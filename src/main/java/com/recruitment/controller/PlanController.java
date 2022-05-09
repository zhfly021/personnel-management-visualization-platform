package com.recruitment.controller;

import com.recruitment.entity.Admin;
import com.recruitment.entity.Plan;
import com.recruitment.entity.Project;
import com.recruitment.service.PlanService;
import com.recruitment.utils.PageUtil;
import com.recruitment.utils.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-30 21:42
 */
@Slf4j
@Controller
public class PlanController {

    @Autowired
    PlanService planService;

    /**
     * 跳转至招聘计划页面
     * @return
     */
    @RequestMapping(value = "/plan/release/recruitment",method = RequestMethod.GET)
    public String toRecruitment(Model model){
        List<String> strings = planService.queryProjectName();
        model.addAttribute("projects",strings);
        return "plan/release/recruitment";
    }

    /**
     * 查询招聘项目
     * @param page
     * @param limit
     * @param project
     * @return
     */
    @RequestMapping("/plan/release/queryProjectList")
    @ResponseBody
    public RestResponse queryProjectList(Integer page, Integer limit, @Nullable Project project){
        if("".equals(project.getName())){
            project.setName(null);
        }
        if("".equals(project.getStatus())){
            project.setStatus(null);
        }

        List<Project> projects = planService.queryProjectList(project);

        if(!projects.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(projects, page, limit, projects.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    /**
     * 跳转至招聘计划添加页面
     * @return
     */
    @RequestMapping(value = "/plan/release/addRecruitment",method = RequestMethod.GET)
    public String toAddRecruitment(){
        return "plan/release/recruitment_add";
    }


    /**
     * 添加招聘项目
     * @param project
     * @return
     */
    @RequestMapping("/plan/release/addProject")
    @ResponseBody
    public RestResponse addProject(Project project, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        // 获取当前提交的时间
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        project.setReleaseTime(nowTime);
        project.setStatus("已开始");
        int i = planService.addProject(project);
        log.info(admin.getUsername() + "添加一条招聘项目数据：" + project);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"招聘项目发布失败...");
        }

    }

    /**
     * 跳转至招聘计划编辑页面
     * @return
     */
    @RequestMapping(value = "/plan/release/editRecruitment/{id}",method = RequestMethod.GET)
    public String toEditRecruitment(@PathVariable("id")Integer id, Model model){
        Project project = planService.queryProjectById(id);
        model.addAttribute("project",project);
        return "plan/release/recruitment_edit";
    }

    /**
     * 修改招聘计划
     * @param project
     * @param session
     * @return
     */
    @RequestMapping("/plan/release/updateProject")
    @ResponseBody
    public RestResponse updateProject(Project project, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Project oldProject = planService.queryProjectById(project.getId());
        // 根据id修改
        int i = planService.updateProjectById(project);
        log.info(admin.getUsername() + "将原招聘项目数据：" + oldProject + " ，修改为：" + project);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"招聘项目更新失败...");
        }

    }


    /**
     * 删除一个项目
     * @param id
     * @return
     */
    @RequestMapping("/plan/release/delProject")
    @ResponseBody
    public RestResponse delProject(Integer id,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Project oldProject = planService.queryProjectById(id);

        int i = planService.delProjectById(id);
        int j = planService.delPlanByProjectID(id);
        log.info(admin.getUsername() + " 删除了招聘项目：" + oldProject + " ，以及相关连的招聘计划详情");
        if((i != 0) && (j != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"招聘项目删除失败...");
        }
    }

    /**
     * 删除多个项目
     * @param data
     * @return
     */
    @RequestMapping("/plan/release/bacthDelProject")
    @ResponseBody
    public RestResponse bacthDelProject(String data,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        String[] fields = data.split(",");
        int i = planService.batchDelProject(fields);
        for (String projectID:fields) {
            planService.delPlanByProjectID(Integer.valueOf(projectID));

            Project oldProject = planService.queryProjectById(Integer.valueOf(projectID));
            log.info(admin.getUsername() + " 删除了招聘项目：" + oldProject + " ，以及相关连的招聘计划详情");
        }

        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"招聘项目删除失败...");
        }
    }






    /**
     * 跳转至招聘详情页面
     * @return
     */
    @RequestMapping(value = "/plan/particular/particular",method = RequestMethod.GET)
    public String toParticular(Model model){
        List<Project> projects = planService.queryProject();
        model.addAttribute("projects",projects);
        return "plan/particular/particular";
    }

    /**
     * 查询招聘计划详情
     * @param page
     * @param limit
     * @param plan
     * @return
     */
    @RequestMapping("/plan/particular/queryPlanList")
    @ResponseBody
    public RestResponse queryPlanList(Integer page, Integer limit, @Nullable Plan plan){
        if("".equals(plan.getUnit())){
            plan.setUnit(null);
        }
        if("".equals(plan.getStatus())){
            plan.setStatus(null);
        }

        List<Plan> plans = planService.queryPlanList(plan);

        if(!plans.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(plans, page, limit, plans.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    /**
     * 跳转至招聘计划详情添加页面
     * @return
     */
    @RequestMapping(value = "/plan/particular/addParticular",method = RequestMethod.GET)
    public String toAddParticular(Model model){
        List<Project> projects = planService.queryProject();
        model.addAttribute("projects",projects);
        return "plan/particular/particular_add";
    }

    /**
     * 获取部门名
     * @param hId
     * @return
     */
    @RequestMapping("/plan/particular/getDepart")
    @ResponseBody
    public List<String> getDepart(int hId){
        List<String> strings = planService.queryDepartment(hId);
        return strings;
    }

    /**
     * 添加招聘计划详情
     * @param plan
     * @return
     */
    @RequestMapping("/plan/particular/addPlan")
    @ResponseBody
    public RestResponse addPlan(Plan plan,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        plan.setStatus("未完成");
        int i = planService.addPlan(plan);
        log.info(admin.getUsername() + "添加了一条招聘计划详情：" + plan);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"招聘计划添加失败...");
        }

    }

    /**
     * 跳转至招聘详计划情编辑页面
     * @return
     */
    @RequestMapping(value = "/plan/particular/editParticular/{id}",method = RequestMethod.GET)
    public String toEditParticular(@PathVariable("id")Integer id, Model model){
        List<Project> projects = planService.queryProject();
        model.addAttribute("projects",projects);

        Plan plan = planService.queryPlanById(id);
        model.addAttribute("plan",plan);

        String unit = plan.getUnit();
        int category = planService.queryCategoryByName(unit);
        model.addAttribute("category",category);

        return "plan/particular/particular_edit";
    }

    /**
     * 修改招聘计划
     * @param plan
     * @return
     */
    @RequestMapping("/plan/particular/updatePlan")
    @ResponseBody
    public RestResponse updatePlan(Plan plan, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Plan oldPlan = planService.queryPlanById(plan.getId());

        int hireNumber = plan.getHireNumber();   // 招聘人数
        int hired = plan.getHired();                // 已录用人数
        if(hired >= hireNumber){
            // 已招满
            plan.setStatus("已完成");
        }else{
            // 未招满
            plan.setStatus("未完成");
        }

        // 根据id修改
        int i = planService.updatePlanById(plan);
        log.info(admin.getUsername() + "将原招聘计划详情：" + oldPlan + " ，修改为：" + plan);

        // 修改项目状态
        List<String> listStatus = planService.queryPlanListStatus(plan.getProjectID());
        boolean flag = listStatus.contains("未完成");
        Project project = new Project();
        if(flag == false){
            project.setId(plan.getProjectID());
            project.setCompleteTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            project.setStatus("已完成");
            planService.updateProjectStatus(project);
        }else {
            project.setId(plan.getProjectID());
            project.setCompleteTime(null);
            project.setStatus("已开始");
            planService.updateProjectStatus(project);
        }

        if(i != 0){
            return RestResponse.ok("修改成功");
        }else {
            return RestResponse.fail(200,"招聘计划更新失败...");
        }

    }


    /**
     * 删除一个计划
     * @param id
     * @return
     */
    @RequestMapping("/plan/particular/delPlan")
    @ResponseBody
    public RestResponse delPlan(Integer id, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Plan oldPlan = planService.queryPlanById(id);

        int i = planService.delPlanById(id);

        log.info(admin.getUsername() + "删除了一条招聘计划详情：" + oldPlan);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"招聘计划删除失败...");
        }
    }

    /**
     * 删除多个计划
     * @param data
     * @return
     */
    @RequestMapping("/plan/particular/bacthDelPlan")
    @ResponseBody
    public RestResponse bacthDelPlan(String data, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        String[] fields = data.split(",");
        int i = planService.batchDelPlan(fields);

        for (String id:fields) {
            Plan oldPlan = planService.queryPlanById(Integer.valueOf(id));
            log.info(admin.getUsername() + "删除了一条招聘计划详情：" + oldPlan);
        }
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"招聘计划删除失败...");
        }
    }
}
