package com.xib.assessment.service;

import com.xib.assessment.entity.Agent;
import com.xib.assessment.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;


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

}
