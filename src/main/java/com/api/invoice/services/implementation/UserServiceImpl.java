package com.api.invoice.services.implementation;
import com.api.invoice.dto.request.AuthoritiesChangerDTO;
import com.api.invoice.dto.request.LoginDTO;
import com.api.invoice.dto.request.RegisterDTO;
import com.api.invoice.dto.request.UserInfoChangerDTO;
import com.api.invoice.dto.response.RegisteredUserDTO;
import com.api.invoice.dto.response.UserDTO;
import com.api.invoice.dto.response.UserInfoAdminDTO;
import com.api.invoice.dto.response.UserInfoDTO;
import com.api.invoice.exceptions.ApiRequestException;
import com.api.invoice.exceptions.ResourceNotFoundException;
import com.api.invoice.exceptions.UserNameFoundException;
import com.api.invoice.models.AuthorityEnum;
import com.api.invoice.models.User;
import com.api.invoice.repositories.TenantRepo;
import com.api.invoice.repositories.UserRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private TenantRepo tenantRepo;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenUtils tokenUtils;
    public RegisteredUserDTO registerUser(RegisterDTO registerDTO, String token) {
        String tenantId = tokenUtils.getTenantFromToken(token);
        String tok = UUID.randomUUID().toString();
        if(tenantId.equals("")){
            throw new ApiRequestException("Not valid tenant id!");
        }
        if(userRepository.findUserByUsername(registerDTO.getUsername()) != null){
            throw new UserNameFoundException("Username already exists!");
        }
        if(userRepository.countAllByTenantId(tenantId) >= tenantRepo.getTenantById(tenantId).getMaxEmployees()){
            throw new ApiRequestException("Max employees reached!");
        }
        User user = new User();
        List<String> auth = new ArrayList<>();
        auth.add("ROLE_VIEW");
        user.setAuthorities(tokenUtils.getAuthorities(auth));
        user.setEmail(registerDTO.getEmail());
        user.setLastName(registerDTO.getLastName());
        user.setFirstName(registerDTO.getFirstName());
        user.setUsername(registerDTO.getUsername());
        user.setLastPasswordResetDate(new Date());
        user.setEnabled(false);
        user.setFirsTimePasswordToken(tok);
        user.setFirsTimePasswordReset(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(registerDTO.getPassword()));
        user.setTenantId(tenantId);
        try{
            emailService.sendEmail(registerDTO.getEmail(),tok,registerDTO.getUsername());
        }catch (Exception e){
            throw new ApiRequestException("Email sending went wrong");
        }
        userRepository.save(user);
        return new RegisteredUserDTO(user.getUsername(),user.getFirsTimePasswordToken());
    }

    @Override
    public UserInfoDTO getUserInfo(String token) {
        User user = userRepository.findUserByUsername(tokenUtils.getUsernameFromToken(token));
        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setFirstName(user.getFirstName());
        userInfoDTO.setLastName(user.getLastName());
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setAuthorities(user.getAuthorities());
        return userInfoDTO;
    }

    @Override
    public UserInfoDTO updateUserInfo(String token, UserInfoChangerDTO userInfoChangerDTO) {
        User user = userRepository.findUserByUsername(tokenUtils.getUsernameFromToken(token));
        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        }
        user.setEmail(userInfoChangerDTO.getEmail());
        user.setLastName(userInfoChangerDTO.getLastName());
        user.setFirstName(userInfoChangerDTO.getFirstName());
        user.setUsername(userInfoChangerDTO.getUsername());
        userRepository.save(user);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setFirstName(user.getFirstName());
        userInfoDTO.setLastName(user.getLastName());
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setAuthorities(user.getAuthorities());
        return null;
    }

    @Override
    public List<UserInfoAdminDTO> getUsersTenant(String token) {
        List<User> users = userRepository.findAllByTenantId(tokenUtils.getTenantFromToken(token));
        List<UserInfoAdminDTO> usersAdmin = new ArrayList<>();
        for (User user:users) {
            usersAdmin.add(mapUser(user));
        }
        return usersAdmin;
    }

    @Override
    public UserInfoAdminDTO updateUserAuthorities(String token, AuthoritiesChangerDTO authoritiesChangerDTO) {
        User user = userRepository.findUserByUsername(authoritiesChangerDTO.getUsername());
        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        }
        for (String auth: authoritiesChangerDTO.getAuthorities()) {
            if(!auth.equals(AuthorityEnum.ADMIN.getAuth()) || !auth.equals(AuthorityEnum.VIEW.getAuth()) || !auth.equals(AuthorityEnum.EDIT.getAuth())){
                throw new ResourceNotFoundException("Something went wrong");
            }
        }
        user.setAuthorities(tokenUtils.getAuthorities(authoritiesChangerDTO.getAuthorities()));
        userRepository.save(user);
        return mapUser(user);
    }

    @Override
    public UserInfoAdminDTO disableUser(String token, String username) {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        }
        user.setEnabled(false);
        userRepository.save(user);
        return mapUser(user);
    }

    @Override
    public boolean disableUsersTenant(String token) {
        String tenantId = tokenUtils.getTenantFromToken(token);
        if(tenantId == null){
            throw new ResourceNotFoundException("Tenant id not found");
        }
        List<User> users = userRepository.findAllByTenantId(tokenUtils.getTenantFromToken(token));
        for(User user: users){
            user.setEnabled(false);
            userRepository.save(user);
        }
        return true;
    }

    @Override
    public AuthorityEnum[] authorities() {
        return AuthorityEnum.values();
    }

    private UserInfoAdminDTO mapUser(User user){
        UserInfoAdminDTO userInfoAdminDTO = new UserInfoAdminDTO();
        userInfoAdminDTO.setAuthorities(user.getAuthorities());
        userInfoAdminDTO.setEmail(user.getEmail());
        userInfoAdminDTO.setUsername(user.getUsername());
        userInfoAdminDTO.setFirstName(user.getFirstName());
        userInfoAdminDTO.setLastName(user.getLastName());
        userInfoAdminDTO.setEnabled(user.isEnabled());
        return  userInfoAdminDTO;
    }
}
