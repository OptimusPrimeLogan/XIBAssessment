package com.xib.assessment.controller;

import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;

    @GetMapping("manager/{id}")
    public Optional<Manager> findAgent(@PathVariable("id") Long id) {
        return managerRepository.findById(id);
    }

    @GetMapping("managers/")
    public List<Manager> findAgents() {
        return managerRepository.findAll();
    }

    @PostMapping("manager/")
    public ResponseEntity<Object> createTeam(@RequestBody Manager requestManager) {
        try{
            Manager transactionManager = new Manager();
            transactionManager.setName(requestManager.getName());
            return new ResponseEntity<>(managerRepository.saveAndFlush(transactionManager), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(
                    "Error while creating new manager: "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
