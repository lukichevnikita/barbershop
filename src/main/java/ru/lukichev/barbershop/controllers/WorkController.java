package ru.lukichev.barbershop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.lukichev.barbershop.dtos.RecordDto;
import ru.lukichev.barbershop.dtos.ServiceDto;
import ru.lukichev.barbershop.dtos.WorkWeekDto;
import ru.lukichev.barbershop.entities.User;
import ru.lukichev.barbershop.services.WorkService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work/")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("createService")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void createService(@RequestBody ServiceDto service) {
        workService.createService(service);
    }

    @GetMapping("getServices")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public ResponseEntity<List<ServiceDto>> getServices() {
        return new ResponseEntity<>(workService.getServices(), HttpStatus.OK);
    }

    @PostMapping("getService")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public ResponseEntity<ServiceDto> getService(@RequestBody Long id) {
        return new ResponseEntity<>(workService.getService(id), HttpStatus.OK);
    }

    @GetMapping("createWorkWeek")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void createWorkWeek() {
        workService.createWorkWeek();
    }

    @GetMapping("getWorkWeeks")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public ResponseEntity<List<WorkWeekDto>> getWorkWeeks() {
        return new ResponseEntity<>(workService.getCurrentWorkWeeks(), HttpStatus.OK);
    }

    @PostMapping("setClient")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public ResponseEntity<Boolean> setClient(@RequestBody RecordDto record) {
        return new ResponseEntity<>(workService.setClient(record), HttpStatus.OK);
    }

    @GetMapping("getRecordsForClient")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public ResponseEntity<List<RecordDto>> getRecords(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(workService.getRecords(user.getId()), HttpStatus.OK);
    }

    @GetMapping("getRecordsForMaster")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public ResponseEntity<List<RecordDto>> getRecordsForMaster(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(workService.getRecordsForMaster(user.getId()), HttpStatus.OK);
    }

    @PutMapping("checkRecord")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> checkRecord(@RequestBody Map<String, Long> props) {
        workService.checkRecord(props);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
