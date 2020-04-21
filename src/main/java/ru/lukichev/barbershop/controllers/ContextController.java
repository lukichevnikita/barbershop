package ru.lukichev.barbershop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lukichev.barbershop.dtos.Context;
import ru.lukichev.barbershop.entities.User;
import ru.lukichev.barbershop.services.UserService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ContextController {

    private final UserService userService;

    @GetMapping("getContext")
    public ResponseEntity<Context> context(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.buildContext(user), HttpStatus.OK);
    }
}
