package com.recruitment.service;

import com.recruitment.entity.Plan;
import com.recruitment.entity.Project;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-31 21:07
 */
public interface PlanService {
    /**
     * 查询所有招聘项目
     * @return
     */
    public List<Project> queryProjectList(Project project);

    /**
     * 根据id修改项目状态
     * @param project
     * @return
     */
    public int updateProjectStatus(Project project);

    /**
     * 查询所有招聘项目名称
     * @return
     */
    public List<String> queryProjectName();

    /**
     * 查询所有招聘项目名称
     * @return
     */
    public List<Project> queryProject();

    /**
     * 增加招聘项目
     * @param project
     * @return
     */
    public int addProject(Project project);

    /**
     * 查询招聘项目
     * @param id
     * @return
     */
    public Project queryProjectById(Integer id);

    /**
     * 修改招聘项目
     * @param project
     * @return
     */
    public int updateProjectById(Project project);

    /**
     * 删除招聘项目
     * @param id
     * @return
     */
    public int delProjectById(Integer id);

    /**
     * 批量删除招聘项目
     * @param ids
     * @return
     */
    public int batchDelProject(String[] ids);

    /***
     * 查找项目的id
     * @param name
     * @param releaseTime
     * @return
     */
    public int queryProjectID(String name, String releaseTime);








    /**
     * 查询所有招聘计划
     * @return
     */
    public List<Plan> queryPlanList(Plan plan);

    /**
     * 查询所有的项目ID
     * @return
     */
    public List<Integer> queryProjectIDs();

    /**
     * 根据项目id查询所有招聘计划的状态
     * @return
     */
    public List<String> queryPlanListStatus(int projectID);

    /**
     * 根据部门类别查询部门名称
     * @return
     */
    public List<String> queryDepartment(int category);

    /**
     * 查询招聘计划
     * @param name
     * @return
     */
    public int queryCategoryByName(String name);

    /**
     * 增加招聘计划
     * @param plan
     * @return
     */
    public int addPlan(Plan plan);

    /**
     * 查询招聘计划
     * @param id
     * @return
     */
    public Plan queryPlanById(Integer id);

    /**
     * 修改招聘计划
     * @param plan
     * @return
     */
    public int updatePlanById(Plan plan);

    /**
     * 删除招聘计划
     * @param id
     * @return
     */
    public int delPlanById(Integer id);

    /**
     * 批量删除招聘计划
     * @param ids
     * @return
     */
    public int batchDelPlan(String[] ids);

    /**
     * 根据项目ID删除所有招聘计划
     * @param projectID
     * @return
     */
    public int delPlanByProjectID(Integer projectID);

    /**
     * 查询所有的Plan
     * @return
     */
    public List<Plan> queryPlan();

    /**
     * 根据招聘项目id查询招聘计划，并汇总数据
     * @return
     */
    public List<Plan> queryPlanByProjectID(int ProjectID);
}
