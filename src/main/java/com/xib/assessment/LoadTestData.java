package com.xib.assessment;

import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.service.AgentService;
import com.xib.assessment.service.ManagerService;
import com.xib.assessment.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class LoadTestData {
    @Autowired
    AgentService agentService;

    @Autowired
    TeamService teamService;

    @Autowired
    ManagerService managerService;

    @PostConstruct
    @Transactional
    public void execute() {

        try{

            Manager karen = managerService.createManager("Karen");
            Manager dave = managerService.createManager("Dave");
            Manager doughlas = managerService.createManager("Douglas");

            Set<Manager> oneManager = new HashSet<>();
            oneManager.add(karen);

            Set<Manager> twoManagers = new HashSet<>();
            twoManagers.add(karen);
            twoManagers.add(dave);

            Set<Manager> managerWhoHasThreeTeams = new HashSet<>();
            managerWhoHasThreeTeams.add(doughlas);

            Team marvel = teamService.createTeam ("Marvel");
            marvel = (Team) teamService.assignManagers(marvel, oneManager);

            Team dc = teamService.createTeam ("DC");
            dc =(Team) teamService.assignManagers(dc, twoManagers);

            Team powerPuff = teamService.createTeam ("PowerPuff");
            Team powerPuff2 = teamService.createTeam ("PowerPuff2");
            Team powerPuff3 = teamService.createTeam ("PowerPuff3");
            powerPuff = (Team) teamService.assignManagers(powerPuff, managerWhoHasThreeTeams);
            powerPuff2 = (Team) teamService.assignManagers(powerPuff2, managerWhoHasThreeTeams);
            powerPuff3 = (Team) teamService.assignManagers(powerPuff3, managerWhoHasThreeTeams);

            agentService.createAgent("Bruce", "Alone", "1011125190081");
            agentService.createAgentAndAssignToTeam("Bruce", "No Manager", "1011125190081", marvel);
            agentService.createAgentAndAssignToManager("Bruce", "No Team", "1011125190081", karen);

            agentService.createAgentAndSetup("Tony", "Stark", "6912115191083", marvel, karen);
            agentService.createAgentAndSetup("Peter", "Parker", "7801115190084", marvel, karen);
            agentService.createAgentAndSetup("Bruce", "Wayne", "6511185190085", dc, karen);
            agentService.createAgentAndSetup("Clark", "Kent", "5905115190086",dc, dave);



        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
