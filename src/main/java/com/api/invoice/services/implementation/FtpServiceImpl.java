package com.api.invoice.services.implementation;

import com.api.invoice.exceptions.ApiRequestException;
import com.api.invoice.models.FTP;
import com.api.invoice.repositories.FTPRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FtpServiceImpl implements FtpService {
    @Autowired
    private FTPRepo ftpRepo;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public FTP registerFTP(FTP ftp, String token) {
        ftp.setTenantId(tokenUtils.getTenantFromToken(token));
        ftp.setPassword(ftp.getPassword());
        return ftpRepo.save(ftp);
    }

    @Override
    public FTP updateFTP(FTP ftp, String id, String token) {
        FTP ftpclient = ftpRepo.getByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(ftpclient == null){
            throw new ApiRequestException("ftp is not valid!");
        }
        return ftpRepo.save(ftp);
    }

    @Override
    public void deleteFTP(String id, String token) {
        FTP ftpclient = ftpRepo.getByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(ftpclient == null){
            throw new ApiRequestException("ftp is not valid!");
        }
        ftpRepo.delete(ftpclient);
    }

    @Override
    public List<FTP> getFTP(String token) {
        return ftpRepo.getAllByTenantId(tokenUtils.getTenantFromToken(token));
    }
}
