package ru.lukichev.barbershop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.lukichev.barbershop.dtos.MasterDto;
import ru.lukichev.barbershop.dtos.RegistrationMasterDataDto;
import ru.lukichev.barbershop.entities.User;
import ru.lukichev.barbershop.services.UserService;

import java.sql.ResultSet;
import java.util.List;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/changeName")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void changeName(@AuthenticationPrincipal User user, @RequestBody String name) {
        userService.changeName(user, name);
    }

    @PutMapping("/changeUsername")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void changeUsername(@AuthenticationPrincipal User user, @RequestBody String username) {
        userService.changeUsername(user, username);
    }

    @PutMapping("/changePassword")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void changePassword(@AuthenticationPrincipal User user, @RequestBody String password) {
        userService.changePassword(user, password);
    }

    @PutMapping("/changePhoneNumber")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void changePhoneNumber(@AuthenticationPrincipal User user, @RequestBody String phoneNumber) {
        userService.changePhoneNumber(user, phoneNumber);
    }

    @PostMapping("/createMaster")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void createMaster(@RequestBody RegistrationMasterDataDto data) {
        userService.createMaster(data);
    }

    @GetMapping("/getMasters")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public ResponseEntity<List<MasterDto>> getMasters() {
        return new ResponseEntity<>(userService.getMasters(), HttpStatus.OK);
    }

    @PutMapping("/changePhoto")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void changePhoto(@AuthenticationPrincipal User user, @RequestBody String changePhoto) {
        userService.changePhoto(user, changePhoto);
    }

    @PutMapping("/changeFunctions")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void changeFunctions(@AuthenticationPrincipal User user, @RequestBody String changeFunctions) {
        userService.changeFunctions(user, changeFunctions);
    }

    @GetMapping("/getFunctions")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public ResponseEntity<String> getMasters(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getFunctions(user), HttpStatus.OK);
    }

}
