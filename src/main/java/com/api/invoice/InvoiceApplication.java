package com.api.invoice;

import com.api.invoice.models.Category;
import com.api.invoice.services.CategoryService;
import com.api.invoice.services.implementation.CategoryServiceImpl;
import com.api.invoice.services.implementation.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.Resource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class InvoiceApplication implements CommandLineRunner {
    @Resource
    FileStorageServiceImpl storageService;

    @Autowired
    CategoryServiceImpl categoryService;
    public static void main(String[] args) {
        SpringApplication.run(InvoiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        storageService.deleteAll();
        storageService.init();
        categoryService.add(new Category("default",false));
        categoryService.add(new Category("incoming",false));
        categoryService.add(new Category("outgoing", false));
    }
}

