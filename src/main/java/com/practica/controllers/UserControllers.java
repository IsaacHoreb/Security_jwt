package com.practica.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@PreAuthorize(value = "denyAll()") //Para configurar por anotaciones los acceso
@RestController
@RequestMapping(path = "/auth/")
public class UserControllers {

    //@PreAuthorize(value = "permitAll()")
    @GetMapping("/hello")
    public String Hello() {
        return "Hello World";
    }

    @GetMapping("/hello-secured")
    public String HelloSecured() {
        return "Hello World With Secured";
    }

    @GetMapping("/get")
    public String helloGet() {
        return "Hello World - GET";
    }

    @PostMapping("/post")
    public String helloPost() {
        return "Hello World - POST";
    }

    @PutMapping("/put")
    public String helloPut() {
        return "Hello World - PUT";
    }

    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hello World - DELETE";
    }

    @PatchMapping("/patch")
    public String helloPatch() {
        return "Hello World - PATCH";
    }

}
