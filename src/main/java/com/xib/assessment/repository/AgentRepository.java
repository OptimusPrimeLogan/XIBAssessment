package com.xib.assessment.repository;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    List<Agent> findByTeamAndManager(Team selfTeam, Manager selfManager);

}
