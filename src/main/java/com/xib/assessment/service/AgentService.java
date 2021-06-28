package com.xib.assessment.service;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private MappingService mappingService;

    public Agent createAgent(String firstName, String lastName, String idNumber) {
        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        return agentRepository.saveAndFlush(a);
    }

    public Object createAgentAndAssignToTeam(String firstName, String lastName, String idNumber, Team existingTeam) throws Exception {

        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a = agentRepository.saveAndFlush(a);
        Object serviceResult = mappingService.assignAgentToTeam(existingTeam.getId(), a);

        if(serviceResult instanceof Exception){
            return new Exception(((Exception) serviceResult).getMessage());
        }else{
            return serviceResult;
        }

    }

    public Agent createAgentAndAssignToManager(String firstName, String lastName, String idNumber, Manager existingManager) {

        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a.setManager(existingManager);
        return agentRepository.saveAndFlush(a);

    }

    public Object createAgentAndSetup(String firstName, String lastName, String idNumber, Team existingTeam, Manager existingManager) throws Exception {

        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a.setManager(existingManager);
        a = agentRepository.saveAndFlush(a);

        Object serviceResult = mappingService.assignAgentToTeam(existingTeam.getId(), a);

        if(serviceResult instanceof Exception){
            return new Exception(((Exception) serviceResult).getMessage());
        }else{
            return serviceResult;
        }

    }



    public List<? extends Object> getAgentsWithParameter(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        //By default, records are ordered in DESCENDING order
        if(sortOrder.equalsIgnoreCase("asc")){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        }

        Page<Agent> pagedResult = agentRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }

    }

    public Optional<Agent> findAgent(Long agentId) {
        return agentRepository.findById(agentId);
    }

    public List<Agent> findAgents() {
        return agentRepository.findAll();
    }

    public Object findByTeamAndManager(Team team, Manager manager) {
        return agentRepository.findByTeamAndManager(team, manager);
    }
}
