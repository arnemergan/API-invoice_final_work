package com.api.invoice.services.implementation;

import com.api.invoice.dto.request.LoginDTO;
import com.api.invoice.dto.response.UserDTO;
import com.api.invoice.dto.response.UserTokenDTO;
import com.api.invoice.exceptions.ApiRequestException;
import com.api.invoice.exceptions.ResourceNotFoundException;
import com.api.invoice.repositories.UserRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.models.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.api.invoice.models.User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        return user;
    }

    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if (authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            LOGGER.debug("No authentication manager set. can't change Password!");
            return;
        }

        LOGGER.debug("Changing password for user '" + username + "'");

        com.api.invoice.models.User user = (com.api.invoice.models.User) loadUserByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserDTO changePasswordFirst(String newPassword, String username, String token) {
        com.api.invoice.models.User user = userRepository.findUserByUsername(username);
        Calendar date = new GregorianCalendar();
        date.add(Calendar.DATE,-1);
        if(!user.isEnabled() && user.getFirsTimePasswordReset().getTime() > date.getTime().getTime() && user.getFirsTimePasswordToken().equals(token)){
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setEnabled(true);
            userRepository.save(user);
            return new UserDTO(user);
        }
        throw new ApiRequestException("Something went wrong");
    }

    public UserDTO login(LoginDTO authenticationRequest) throws ApiRequestException {
        User user = (User) loadUserByUsername(authenticationRequest.getUsername());
        Calendar date = new GregorianCalendar();
        date.add(Calendar.DATE,-1);
        if(!user.isEnabled() && user.getFirsTimePasswordReset().getTime() > date.getTime().getTime()){
            throw new ApiRequestException("Password must be changed");
        }
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword(),user.getAuthorities()));
        } catch (BadCredentialsException e) {
            throw new ApiRequestException("Credentials are not valid!");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenUtils.generateToken(user.getUsername(),user.getTenantId());
        int expiresIn = tokenUtils.getExpiredIn();
        String refresh = tokenUtils.generateRefreshToken(user.getUsername(),user.getTenantId());
        UserDTO userDto = new UserDTO(user);
        userDto.setToken(new UserTokenDTO(jwt, expiresIn,refresh));
        return userDto;
    }

    public UserTokenDTO refreshAuthenticationToken(String refreshToken, String token){
        if(token == null || refreshToken == null || token.isEmpty() || refreshToken.isEmpty()){
            throw new ResourceNotFoundException("Token can not be refreshed.");
        }
        if (tokenUtils.canTokenBeRefreshed(refreshToken)) {
            UserTokenDTO userTokenDTO = new UserTokenDTO();
            userTokenDTO.setAccessToken(tokenUtils.refreshToken(refreshToken));
            userTokenDTO.setExpiresIn((long) tokenUtils.getExpiredIn());
            return userTokenDTO;
        } else {
            throw new ApiRequestException("Token can not be refreshed.");
        }
    }
}
