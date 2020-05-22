package com.api.invoice.controllers;

import com.api.invoice.models.FTP;
import com.api.invoice.services.implementation.FtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("ftp")
public class FTPController {

    @Autowired
    private FtpServiceImpl ftpService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FTP> register(@RequestBody @Valid FTP ftp, HttpServletRequest request) {
        return new ResponseEntity<>(ftpService.registerFTP(ftp,request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FTP> update(@PathVariable String id,@RequestBody @Valid FTP ftp, HttpServletRequest request) {
        return new ResponseEntity<>(ftpService.updateFTP(ftp,id,request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity delete(@PathVariable String id, HttpServletRequest request) {
        ftpService.deleteFTP(id,request.getHeader("Authorization").split(" ")[1]);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<FTP>> register(HttpServletRequest request) {
        return new ResponseEntity<>(ftpService.getFTP(request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }
}
