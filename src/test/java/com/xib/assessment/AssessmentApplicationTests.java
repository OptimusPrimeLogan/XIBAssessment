package com.xib.assessment;

import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import com.xib.assessment.service.TeamService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AssessmentApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private TeamService teamService;

	@Autowired
	private ManagerRepository managerRepository;

	//An agent can be assigned to only one team and reports to one manager.
	//An agent can be assigned to only one team and reports to one manager.
	//Controller Test
	@Test
	void agent_OneTeam_OneManager_Negative() throws Exception {

		this.mockMvc.perform(put("/team/1/agent/").content("{\"id\":1,\"manager\":{\"id\":3}}")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError())
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));

	}

	@Test
	void agent_OneTeam_OneManager_Positive() throws Exception {

		this.mockMvc.perform(put("/team/1/agent/").content("{\"id\":3,\"manager\":{\"id\":1}}")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));

	}

	//A manager can manage multiple teams, and any one team can be managed by at most 2 managers.
	//Unit Test
	@Test
	@Transactional
	void managerMultipleTeams_Negative() throws Exception {

		Team dcHas2Managers = teamRepository.getOne(2L);
		Manager doughlas = managerRepository.getOne(3L);

		Assertions.assertEquals("Any one team can be managed by at most 2 managers",
				( (Exception) teamService.assignManager(dcHas2Managers, doughlas)).getMessage());
	}





}
