package com.recruitment.controller;

import com.recruitment.entity.Plan;
import com.recruitment.entity.Project;
import com.recruitment.service.PlanService;
import com.recruitment.utils.File.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(value = "excelUpload/")
@Controller
public class ExcelController {

    @Autowired
    PlanService planService;

    // 导入
    @RequestMapping(value = "importData", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Map<String,Object> importData(@RequestPart(value = "file") MultipartFile file) {
        //获取文件名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
        log.info("导入的项目名为---"+fileName);
        //发布时间
        String releaseTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        //创建一个新Project对象
        Project project = new Project(fileName,releaseTime,"已开始");
        //获取excel的数据
        List<Plan> list = ExcelUtil.readExcel(file);

        int i = 0, projectId = 0, sum = 0;
        //添加项目
        if (null != list){
            i = planService.addProject(project);
            log.info("添加项目成功？---"+i);
            projectId = planService.queryProjectID(fileName,releaseTime);
        }
        //添加招聘计划
        if ( i > 0 ){
            for (int j=0; j<list.size(); j++){
                Plan plan = list.get(j);
                plan.setProjectID(projectId);
                plan.setResume(0);
                plan.setAssessment(0);
                plan.setHired(0);
                plan.setStatus("未完成");
                sum += planService.addPlan(plan);
            }
        }
        log.info("添加招聘计划成功？--"+sum);

        Map<String, Object> map=new HashMap<String, Object>();
        if (i > 0 && sum > 0){
            map.put("code","200");
            map.put("message","导入成功");
        }else {
            map.put("code","100");
            map.put("message","导入失败");
        }
        return map;
    }


}
