package com.xib.assessment.service;

import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team createTeam(String name){
        Team t = new Team();
        t.setName(name);
        return teamRepository.saveAndFlush(t);
    }

    public Object assignManagers(Team existingTeam, Set<Manager> teamManagers) {

        try{
            if(teamManagers.size() > 2){
                throw new Exception("Any one team can be managed by at most 2 managers");
            }else{
                existingTeam.setTeamManagers(teamManagers);
                return teamRepository.saveAndFlush(existingTeam);
            }

        }catch(Exception exception){
            return new Exception(exception.getMessage());
        }
    }

    public Object assignManager(Team existingTeam, Manager newManager) {

        try{
            if(existingTeam.getTeamManagers().size() < 2){
                existingTeam.getTeamManagers().add(newManager);
                return teamRepository.saveAndFlush(existingTeam);
            }else{
                throw new Exception("Any one team can be managed by at most 2 managers");
            }

        }catch(Exception exception){
            return new Exception(exception.getMessage());
        }
    }

    public Optional<Team> findTeam(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public List<Team> findTeams() {
        return teamRepository.findAll();
    }

}
