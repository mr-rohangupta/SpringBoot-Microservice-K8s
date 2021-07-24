package com.java.userservice.service;

import com.java.userservice.entity.User;
import com.java.userservice.model.Department;
import com.java.userservice.model.ResponseTemplate;
import com.java.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService ..");
        return userRepository.save(user);
    }

    public ResponseTemplate getUserWithDepartment(Long userId) {
        ResponseTemplate responseTemplate = new ResponseTemplate();
        User user = userRepository.findByUserId(userId);
        Department department = restTemplate.
                getForObject("http://DEPARTMENT-SERVICE/department/" + user.getDepartmentId(),
                        Department.class);
        responseTemplate.setUser(user);
        responseTemplate.setDepartment(department);
        return responseTemplate;
    }
}
