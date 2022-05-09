package com.recruitment.service.impl;

import com.recruitment.entity.Plan;
import com.recruitment.entity.Project;
import com.recruitment.mapper.PlanMapper;
import com.recruitment.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-31 21:08
 */
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanMapper planMapper;

    @Override
    public List<Project> queryProjectList(Project project) {
        return planMapper.queryProjectList(project);
    }

    @Override
    public int updateProjectStatus(Project project) {
        return planMapper.updateProjectStatus(project);
    }

    @Override
    public List<String> queryProjectName() {
        return planMapper.queryProjectName();
    }

    @Override
    public List<Project> queryProject() {
        return planMapper.queryProject();
    }

    @Override
    public int addProject(Project project) {
        return planMapper.addProject(project);
    }

    @Override
    public Project queryProjectById(Integer id) {
        return planMapper.queryProjectById(id);
    }

    @Override
    public int updateProjectById(Project project) {
        return planMapper.updateProjectById(project);
    }

    @Override
    public int delProjectById(Integer id) {
        return planMapper.delProjectById(id);
    }

    @Override
    public int batchDelProject(String[] ids) {
        return planMapper.batchDelProject(ids);
    }

    @Override
    public int queryProjectID(String name, String releaseTime) {
        return planMapper.queryProjectID(name, releaseTime);
    }

    @Override
    public List<Plan> queryPlanList(Plan plan) {
        return planMapper.queryPlanList(plan);
    }

    @Override
    public List<Integer> queryProjectIDs() {
        return planMapper.queryProjectIDs();
    }

    @Override
    public List<String> queryPlanListStatus(int projectID) {
        return planMapper.queryPlanListStatus(projectID);
    }

    @Override
    public List<String> queryDepartment(int category) {
        return planMapper.queryDepartment(category);
    }

    @Override
    public int queryCategoryByName(String name) {
        return planMapper.queryCategoryByName(name);
    }

    @Override
    public int addPlan(Plan plan) {
        return planMapper.addPlan(plan);
    }

    @Override
    public Plan queryPlanById(Integer id) {
        return planMapper.queryPlanById(id);
    }

    @Override
    public int updatePlanById(Plan plan) {
        return planMapper.updatePlanById(plan);
    }

    @Override
    public int delPlanById(Integer id) {
        return planMapper.delPlanById(id);
    }

    @Override
    public int batchDelPlan(String[] ids) {
        return planMapper.batchDelPlan(ids);
    }

    @Override
    public int delPlanByProjectID(Integer projectID) {
        return planMapper.delPlanByProjectID(projectID);
    }

    @Override
    public List<Plan> queryPlan() {
        return planMapper.queryPlan();
    }

    @Override
    public List<Plan> queryPlanByProjectID(int ProjectID) {
        return planMapper.queryPlanByProjectID(ProjectID);
    }


}
