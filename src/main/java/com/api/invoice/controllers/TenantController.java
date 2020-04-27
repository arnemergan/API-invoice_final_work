package com.api.invoice.controllers;
import com.api.invoice.models.Tenant;
import com.api.invoice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "tenant")
public class TenantController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/")
    @CrossOrigin
    public Tenant Register(@RequestBody Tenant tenant){
        //return userService.login(email,password);
        return null;
    }

    @GetMapping(path = "/{id}")
    @CrossOrigin
    public Tenant Get(@PathVariable String id){
        //return userService.login(email,password);
        return null;
    }

    @PutMapping(path = "/update/{id}")
    @CrossOrigin
    public Tenant Update(@PathVariable String id, @RequestBody Tenant tenant){
        return null;
    }

    @DeleteMapping(path = "/delete/{id}")
    @CrossOrigin
    public void Delete(@PathVariable String id){
    }
}
