package com.recruitment.service;

import com.recruitment.entity.Admin;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-30 20:27
 */
public interface AdminService {
    /**
     * 根据用户名查询用户信息，用于登录判断
     * @param username
     * @return
     */
    public Admin findByUsername(String username);

    /**
     * 根据id修改密码
     * @param id
     */
    public int modifyPassword(String password,int id);
}
