package com.recruitment.controller;

import com.recruitment.entity.Admin;
import com.recruitment.service.AdminService;
import com.recruitment.utils.MD5;
import com.recruitment.utils.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-30 20:26
 */
@Slf4j
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;


    /**
     * 页面跳转控制
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "user/login";
    }

    /**
     * 管理员登录系统
     * @param username
     * @param password
     * @param verifycode
     * @param map
     * @param session
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/admin/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("verifycode") String verifycode,
                        Map<String,Object> map, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Admin admin = adminService.findByUsername(username);
        String session_key = (String) session.getAttribute("SESSION_KEY");
        if (!(session_key.equalsIgnoreCase(verifycode))){
            map.put("msg","验证码错误...");
            return "user/login";
        }else {
            if (admin != null){
                String pwd = admin.getPassword();

                if(pwd.length() <= 6){  // 初始密码(弱密码)

                }else{  // 已修改初始密码密码
                    password = MD5.EncoderByMd5(password);
                }

                if(password.equals(pwd)){
                    log.info(admin.getUsername() + "登录【wxc人事招聘进度管理平台】...");
                    session.setAttribute("admin",admin);
                    return "index";
                }else {
                    map.put("msg","密码错误...");
                    return "user/login";
                }
            }else {
                map.put("msg","用户名不存在...");
                return "user/login";
            }
        }
    }


    /**
     * 跳转至修改密码页面
     * @return
     */
    @RequestMapping(value = "/user/password",method = RequestMethod.GET)
    public String toPassword(){
        return "user/password";
    }

    /**
     * 管理员修改密码
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/admin/modifyPassword")
    @ResponseBody
    public RestResponse<Object> modifyPassword(String oldPassword, String newPassword, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Admin admin = (Admin) session.getAttribute("admin");
        String pwd = admin.getPassword();
        if(pwd.length() <= 6){  // 初始密码(弱密码)

        }else{  // 已修改过初始密码
            oldPassword = MD5.EncoderByMd5(oldPassword);
        }
        if(oldPassword.equals(pwd)){
            adminService.modifyPassword(MD5.EncoderByMd5(newPassword),admin.getId());
            log.info(admin.getUsername() + "修改了密码...");
            return RestResponse.ok();
        }else{
            log.info(admin.getUsername() + "尝试修改密码，但是旧密码错误...");
            return RestResponse.fail(200,"旧密码错误，请重试...");
        }
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/admin/logout")
    public String logout(HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        session.removeAttribute("admin");
        if(admin != null){
            log.info(admin.getUsername() + "退出【wxc人事招聘进度管理平台】...");
        }
        return "user/login";
    }



}
