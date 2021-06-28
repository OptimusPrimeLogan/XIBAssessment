package com.xib.assessment.controller;

import com.xib.assessment.entity.Manager;
import com.xib.assessment.service.ManagerService;
import com.xib.assessment.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private MappingService mappingService;


    @GetMapping("manager/{id}")
    public Optional<Manager> findManager(@PathVariable("id") Long managerId) {
        return managerService.findManager(managerId);
    }

    @GetMapping("managers/")
    public List<Manager> findManagers() {
        return managerService.findManagers();
    }

    @PostMapping("manager/")
    public ResponseEntity<Object> createManager(@RequestBody Manager requestManager) {
        try{
            return new ResponseEntity<>(managerService.createManager(requestManager.getName()), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(
                    "Error while creating new manager: "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("manager/{managerId}/agent/{agentId}")
    public ResponseEntity<Object> assignAgentToManager(@PathVariable("managerId") Long managerId,
                                                       @PathVariable("agentId") Long agentId) throws Exception {

        Object assignedTeam =  mappingService.assignAgentToManager(managerId, agentId);

        if(assignedTeam instanceof Exception){
            return new ResponseEntity<>("Error while assigning agent to team: "+((Exception) assignedTeam).getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return new ResponseEntity<>(assignedTeam, HttpStatus.OK);
        }

    }

}
