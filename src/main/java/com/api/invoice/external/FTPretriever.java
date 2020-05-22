package com.api.invoice.external;

import com.api.invoice.models.FTP;
import com.api.invoice.repositories.FTPRepo;
import com.api.invoice.repositories.InvoicesRepo;
import com.api.invoice.repositories.UserRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.implementation.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FTPretriever {
 /*   @Autowired
    private FTPRepo ftpRepo;
    @Autowired
    private InvoiceServiceImpl invoiceService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenUtils tokenUtils;

    List<FTP> ftps = new ArrayList<>();

    public FTPretriever() {
        this.ftps = ftpRepo.findAll();
    }

    @Bean
    @InboundChannelAdapter(channel = "ftpChannel", poller = @Poller(fixedDelay = "5000"))
    public MessageSource<File> ftpMessageSource() {
        FtpInboundFileSynchronizingMessageSource source = new FtpInboundFileSynchronizingMessageSource(ftpInboundFileSynchronizer());
        source.setLocalDirectory(new File("ftp-inbound"));
        source.setAutoCreateLocalDirectory(true);
        source.setLocalFilter(new AcceptOnceFileListFilter<File>());
        //invoiceService.uploadInvoice(file,"eng","ef");
        return source;
    }

    @Bean
    public FtpInboundFileSynchronizer ftpInboundFileSynchronizer() {
        FtpInboundFileSynchronizer fileSynchronizer = new FtpInboundFileSynchronizer(ftpSessionFactory());
        fileSynchronizer.setDeleteRemoteFiles(false);
        fileSynchronizer.setRemoteDirectory("foo");
        fileSynchronizer.setFilter(new FtpSimplePatternFileListFilter("*"));
        return fileSynchronizer;
    }

    @Bean
    public DefaultFtpSessionFactory ftpSessionFactory(){
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost(ftp.getHost());
        sf.setPort(ftp.getPort());
        sf.setUsername(ftp.getUsername());
        sf.setPassword(ftp.getPassword());
        return sf;
    }

    @Bean
    @ServiceActivator(inputChannel = "ftpChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getPayload());
            }
        };
    }*/
}
