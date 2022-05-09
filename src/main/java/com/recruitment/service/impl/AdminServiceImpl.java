package com.recruitment.service.impl;

import com.recruitment.entity.Admin;
import com.recruitment.mapper.AdminMapper;
import com.recruitment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-30 20:27
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    @Override
    public int modifyPassword(String password, int id) {
        return adminMapper.modifyPassword(password, id);
    }
}
