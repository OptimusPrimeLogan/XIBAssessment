package com.xib.assessment.service;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.AgentRepository;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MappingService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public Object assignAgentToTeam(Long teamId, Agent agent){

        Manager selfManager = null;

        try{

            if(agent.getId() != null){
                agent = agentRepository.getOne(agent.getId());
            }else{
                throw new Exception("There's no such agent");
            }

            if(teamId != null && teamRepository.findById(teamId).isPresent()){

                Team existingTeam = teamRepository.findById(teamId).get();

                if(agent.getManager() !=null && agent.getManager().getId()!=null){//find agent's team's manager
                    selfManager = managerRepository.findById(agent.getManager().getId()).get();
                }

                //The value will be null if there is no such manager for the agent, create the entity and add it to later
                if(selfManager == null){
                    throw new Exception("There's no such manager details for the team");
                }else if (existingTeam.getTeamManagers().contains(selfManager)){
                    agent.setTeam(existingTeam);
                }

            }else{
                throw new Exception("There's no such team with Id "+teamId);
            }
            return agentRepository.saveAndFlush(agent);
        }catch (Exception ex){
            return new Exception(ex.getMessage());
        }
    }

    public Object assignAgentToManager(Long managerId, Long agentId) {

        Manager selfManager = null;
        Agent requestAgent = null;

        try{
            if(agentId != null){
                requestAgent = agentRepository.getOne(agentId);
            }else{
                throw new Exception("There's no such agent");
            }

            if(managerId!=null && managerRepository.findById(managerId).isPresent()){
                selfManager = managerRepository.findById(managerId).get();
            }else{
                throw new Exception("There's no such manager with id: "+managerId);
            }

            requestAgent.setManager(selfManager);
            return agentRepository.saveAndFlush(requestAgent);
        } catch (Exception ex){
            return new Exception(ex.getMessage());
        }

    }
}
