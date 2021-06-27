package com.xib.assessment.controller;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.TeamRepository;
import com.xib.assessment.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MappingService mappingService;

    @GetMapping("team/{id}/")
    public Optional<Team> findTeam(@PathVariable("id") Long id) {
        return teamRepository.findById(id);
    }

    @GetMapping("teams")
    public List<Team> findTeams() {
        return teamRepository.findAll();
    }

    @PostMapping("team/")
    public ResponseEntity<Object> createTeam(@RequestBody Team requestTeam) {
        try{
            Team transactionTeam = new Team();
            transactionTeam.setName(requestTeam.getName());
            return new ResponseEntity<>(teamRepository.saveAndFlush(transactionTeam), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(
                    "Error while creating new team: "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("team/{id}/agent")
    public ResponseEntity<Object> assignAgentToTeam(@PathVariable("id") Long teamId, @RequestBody Agent agent){

        try{
            //mappingService
            return new ResponseEntity<>("teamId: "+teamId+" agent:"+agent.getLastName(), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(
                    "Error while creating new team: "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
