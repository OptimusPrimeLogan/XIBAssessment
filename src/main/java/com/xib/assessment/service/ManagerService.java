package com.xib.assessment.service;

import com.xib.assessment.entity.Manager;
import com.xib.assessment.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public Manager createManager(String managerName) {
        Manager m = new Manager();
        m.setName(managerName);
        return managerRepository.saveAndFlush(m);
    }

    public Optional<Manager> findManager(Long managerId) {
        return managerRepository.findById(managerId);
    }

    public List<Manager> findManagers() {
        return managerRepository.findAll();
    }

}
