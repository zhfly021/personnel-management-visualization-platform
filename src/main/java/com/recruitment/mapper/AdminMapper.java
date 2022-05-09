package com.recruitment.mapper;

import com.recruitment.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-30 20:27
 */
@Repository
public interface AdminMapper {

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
