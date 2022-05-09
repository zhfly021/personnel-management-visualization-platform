package com.recruitment.controller;

import com.recruitment.entity.Plan;
import com.recruitment.entity.Project;
import com.recruitment.service.PlanService;
import com.recruitment.utils.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.jta.WebSphereUowTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-30 21:41
 */
@Slf4j
@Controller
public class ConsoleController {

    @Autowired
    private PlanService planService;



    /**
     * 页面第一次加载时跳转至控制台
     * @return
     */
    @RequestMapping(value = "/home/console/{id}")
    public String toPassword(@PathVariable(name = "id")  int id,
                             Model model){
        //项目列表
        List<Project> projects = planService.queryProject();
        model.addAttribute("projects",projects);

        if(projects.size() == 0){
            return "home/console";
        }

        //根据项目id查询对应的招聘计划详情数据，并根据招聘人数进行降序排序，
        List<Plan> list = new ArrayList<>();
        int projectId = 0;
        if (id == 0){
            projectId = projects.get(0).getId();
            list = planService.queryPlanByProjectID(projectId);
            log.info("查询的是第一条项目对应的招聘信息");
        }else {
            list = planService.queryPlanByProjectID(id);
            log.info("查询的是项目id为"+id+"对应的招聘信息");
        }

        model.addAttribute("list",list);
        model.addAttribute("length",list.size()-1);

        return "home/console";
    }


    /**
     * 页面第一次加载时跳转至控制台图表
     * @return
     */
    @RequestMapping(value = "/home/bar/{id}")
    @ResponseBody
    public Map<String,Object> toBar(@PathVariable(name = "id")  int id){
        Map<String, Object> map=new HashMap<String, Object>();    //！hashMap是无序的

        List<Project> projects = planService.queryProject();
        //根据项目id查询对应的招聘计划详情数据，并根据招聘人数进行降序排序，
        List<Plan> list = new ArrayList<>();
        int projectId = 0;
        if(id == 0){
            projectId = projects.get(0).getId();
            list = planService.queryPlanByProjectID(projectId);
        }else{
            list = planService.queryPlanByProjectID(id);
        }

        //招聘人数
        List<Integer> hireList = new ArrayList<Integer>();
        //已经录用的人数
        List<Integer> hiredList = new ArrayList<Integer>();

        for (int i=0; i<list.size(); i++){
            hireList.add(i,list.get(i).getHireNumber());
            hiredList.add(i,list.get(i).getHired());
        }

        map.put("code","0");
        map.put("message","获取成功");
        map.put("hireList",hireList);
        map.put("hiredList",hiredList);

        return map ;
    }

    /**
     * 请求数据到跳转至控制台改变数据和图表
     * @return
     */
    @RequestMapping(value = "/home/toAjxa/{id}")
    @ResponseBody
    public Map<String, Object> toAjxa(@PathVariable(name = "id")  int id,
                               Model model){
        Map<String, Object> map=new HashMap<String, Object>();    //！hashMap是无序的
        //项目列表
        List<Project> projects = planService.queryProject();

        //根据项目id查询对应的招聘计划详情数据，并根据招聘人数进行降序排序，
        List<Plan> list = new ArrayList<>();
        int projectId = 0;
        if (id == 0){
            projectId = projects.get(0).getId();
            list = planService.queryPlanByProjectID(projectId);
            log.info("查询的是第一条项目对应的招聘信息");
        }else {
            list = planService.queryPlanByProjectID(id);
            log.info("查询的是项目id为"+id+"对应的招聘信息");
        }

        List<Integer> hireList = new ArrayList<Integer>();
        //已经录用的人数
        List<Integer> hiredList = new ArrayList<Integer>();

        for (int i=0; i<list.size(); i++){
            hireList.add(i,list.get(i).getHireNumber());
            hiredList.add(i,list.get(i).getHired());
        }

        map.put("code","0");
        map.put("message","获取成功");
        map.put("list",list);
        map.put("hireList",hireList);
        map.put("hiredList",hiredList);
        map.put("length",list.size()-1);

        return map;

    }



}
