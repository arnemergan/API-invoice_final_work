package com.api.invoice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Document
public class FTP extends BaseSaasEntity{
    @Column(name = "username", unique = true, nullable = false)
    @NotNull
    private String username;

    @Column(name = "password", unique = true, nullable = false)
    @NotNull
    private String password;

    @Column(name = "port", nullable = false)
    @NotNull
    private int port;

    @Column(name = "host", nullable = false)
    @NotNull
    private String host;

    @Column(name = "folder", nullable = false)
    @NotNull
    private String folder;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
