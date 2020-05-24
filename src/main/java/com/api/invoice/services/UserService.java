package com.api.invoice.services;

import com.api.invoice.dto.request.AuthoritiesChangerDTO;
import com.api.invoice.dto.request.RegisterDTO;
import com.api.invoice.dto.request.UserInfoChangerDTO;
import com.api.invoice.dto.response.UserDTO;
import com.api.invoice.dto.response.UserInfoAdminDTO;
import com.api.invoice.dto.response.UserInfoDTO;

import java.util.List;

public interface UserService {
    public UserDTO registerUser(RegisterDTO registerDTO,String token);
    public UserInfoDTO getUserInfo(String token);
    public UserInfoDTO updateUserInfo(String token, UserInfoChangerDTO userInfoChangerDTO);
    public List<UserInfoAdminDTO> getUsersTenant(String token);
    public UserInfoAdminDTO updateUserAuthorities(String token, AuthoritiesChangerDTO authoritiesChangerDTO);
    public UserInfoAdminDTO disableUser(String token, String username);
    public boolean disableUsersTenant(String token);
}
