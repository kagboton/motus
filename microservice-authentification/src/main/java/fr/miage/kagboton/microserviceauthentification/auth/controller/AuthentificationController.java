package fr.miage.kagboton.microserviceauthentification.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentification")
public class AuthentificationController {

    @PostMapping("/login")
    public ResponseEntity connection(
            @RequestParam("login") String login, @RequestParam("pwd") String pwd
    ){
        if (login.equals(pwd)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
