package com.xib.assessment.controller;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Team;
import com.xib.assessment.service.MappingService;
import com.xib.assessment.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private MappingService mappingService;

    @GetMapping("team/{id}/")
    public Optional<Team> findTeam(@PathVariable("id") Long teamId) {
        return teamService.findTeam(teamId);
    }

    @GetMapping("teams")
    public List<Team> findTeams() {
        return teamService.findTeams();
    }

    @PostMapping("team/")
    public ResponseEntity<Object> createTeam(@RequestBody Team requestTeam) {
        try{
            return new ResponseEntity<>(teamService.createTeam(requestTeam.getName()), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(
                    "Error while creating new team: "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("team/{id}/agent")
    public ResponseEntity<Object> assignAgentToTeam(@PathVariable("id") Long teamId, @RequestBody Agent agent) {

            Object assignedTeam =  mappingService.assignAgentToTeam(teamId, agent);

            if(assignedTeam instanceof Exception){
                return new ResponseEntity<>("Error while assigning agent to team: "+((Exception) assignedTeam).getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(assignedTeam, HttpStatus.OK);
            }

    }

}
