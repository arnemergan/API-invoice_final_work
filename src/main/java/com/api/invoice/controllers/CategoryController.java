package com.api.invoice.controllers;

import com.api.invoice.models.Category;
import com.api.invoice.services.implementation.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @PostMapping("/")
    public ResponseEntity add(HttpServletRequest request, @RequestBody @Valid Category category) {
        return new ResponseEntity<>(categoryService.add(request.getHeader("Authorization").split(" ")[1],category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(HttpServletRequest request,@PathVariable String id) {
        categoryService.delete(request.getHeader("Authorization").split(" ")[1],id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAll(HttpServletRequest request) {
        return new ResponseEntity<List<Category>>(categoryService.getAll(request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }
}
