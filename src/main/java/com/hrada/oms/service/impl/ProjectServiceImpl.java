package com.hrada.oms.service.impl;

import com.hrada.oms.dao.log.ProjectRepository;
import com.hrada.oms.model.log.Project;
import com.hrada.oms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shin on 2018/12/10.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project save(Project project) {
        if(project.getTs()==1){
            project.setState(4);
        }else if(project.getBg()==1){
            project.setState(3);
        }else if(project.getYf()==1){
            project.setState(2);
        }else if(project.getKp()==1){
            project.setState(1);
        }
        return projectRepository.save(project);
    }
}
