package com.xib.assessment;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.AgentRepository;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoadTestData {
    @Autowired
    AgentRepository agentRepository;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    ManagerRepository managerRepository;

    @PostConstruct
    @Transactional
    public void execute() {

        Manager manager1 = createManager("Karen");
        Manager manager2 = createManager("Dave");
        Manager manager3 = createManager("Douglas");

        //Team team1 = createTeam("Marvel", manager1);
        //Team team2 = createTeam("DC", manager2, manager3);

        Team team1 = new Team("Marvel");
        team1.addManager(manager1);
        team1 =  teamRepository.saveAndFlush(team1);

        Team team2 = new Team("DC");
        team2.addManager(manager2);
        team2.addManager(manager3);
        team2 =  teamRepository.saveAndFlush(team2);

        createAgent("Bruce", "Alone", "1011125190081");
        createAgent("Bruce", "No Manager", "1011125190081", team1);
        createAgent("Bruce", "No Team", "1011125190081", manager1);
        createAgent("Tony", "Stark", "6912115191083", team1, manager1);
        createAgent("Peter", "Parker", "7801115190084", team1, manager1);
        createAgent("Bruce", "Wayne", "6511185190085", team2, manager1);
        createAgent("Clark", "Kent", "5905115190086",team2, manager2);


    }



    private Agent createAgent(String firstName, String lastName, String idNumber) {
        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        return agentRepository.saveAndFlush(a);
    }

    private Agent createAgent(String firstName, String lastName, String idNumber, Team team1) {
        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a.setTeam(team1);
        return agentRepository.saveAndFlush(a);
    }

    private Agent createAgent(String firstName, String lastName, String idNumber, Manager manager1) {
        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a.setManager(manager1);
        return agentRepository.saveAndFlush(a);
    }

    private Agent createAgent(String firstName, String lastName, String idNumber, Team team, Manager manager) {
        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a.setTeam(team);
        a.setManager(manager);
        return agentRepository.saveAndFlush(a);
    }

    private Manager createManager(String abc) {
        Manager m = new Manager();
        m.setName(abc);
        return managerRepository.saveAndFlush(m);
    }

    private Team createTeam(String name, Manager manager) {
        Team t = new Team();
        t.setName(name);
        t.addManager(manager);
        return teamRepository.saveAndFlush(t);
    }

    private Team createTeam(String name, Manager manager, Manager assistantManager) {
        Team t = new Team();
        t.setName(name);
        t.addManager(manager);
        t.addManager(assistantManager);
        return teamRepository.saveAndFlush(t);
    }


}
