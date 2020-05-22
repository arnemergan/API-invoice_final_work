package com.api.invoice.services;

import com.api.invoice.models.FTP;

import java.util.List;

public interface FtpService {
    public FTP registerFTP(FTP ftp,String token);
    public FTP updateFTP(FTP ftp,String id, String token);
    public void deleteFTP(String id, String token);
    public List<FTP> getFTP(String token);
}
