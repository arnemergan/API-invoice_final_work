package com.api.invoice.controllers;
import com.api.invoice.models.User;
import com.api.invoice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/{tenant_id}")
    @CrossOrigin
    public List<User> getUsers(@PathVariable String tenant_id){
        //return userService.login(email,password);
        return null;
    }

    @GetMapping(path = "/{tenant_id}/{user_id}")
    @CrossOrigin
    public User getSingleUser(@PathVariable String tenant_id,@PathVariable String user_id){
        //return userService.login(email,password);
        return null;
    }

    @PostMapping(path = "/{tenant_id}/register")
    @CrossOrigin
    public User register(@PathVariable String tenant_id,@RequestBody User user){
        return userService.saveUser(user);
    }

    @DeleteMapping(path = "/{tenant_id}/delete/{user_id}")
    @CrossOrigin
    public void Delete(@PathVariable String tenant_id,@PathVariable String user_id){
    }

    @PutMapping(path = "/{tenant_id}/update/{user_id}")
    @CrossOrigin
    public void Update(@PathVariable String tenant_id,@PathVariable String user_id,@RequestBody User user){
    }

}
