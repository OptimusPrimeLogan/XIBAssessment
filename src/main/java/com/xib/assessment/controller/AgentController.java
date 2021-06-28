package com.xib.assessment.controller;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping("agent/{id}")
    public Optional<Agent> findAgent(@PathVariable("id") Long agentId) { return agentService.findAgent(agentId); }

    @GetMapping("agents/")
    public List<Agent> findAgents() {
        return agentService.findAgents();
    }

    @PostMapping("agent/")
    public ResponseEntity<Object> createAgent(@RequestBody Agent requestAgent) {

        try{
            return new ResponseEntity<>(agentService.createAgent(
                    requestAgent.getFirstName(), requestAgent.getLastName(), requestAgent.getIdNumber()),
                    HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(
                    "Error while creating new agent: "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("agents")
    public ResponseEntity<Object> getAgentsWithParameter(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder)
    {
        return new ResponseEntity<>(agentService.getAgentsWithParameter(pageNo, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("agents/notAssigned")
    public ResponseEntity<?> notAssignedAgents() {
        return new ResponseEntity<>(agentService.findByTeamAndManager(null, null),
                HttpStatus.OK);
    }

}
