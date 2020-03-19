package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.UserRepository;
import com.lrm.po.User;
import com.lrm.util.MD5Utils;
import com.lrm.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }


    @Transactional
    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }


    @Transactional
    @Override
    public Page<User> listUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }



    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        User u = userRepository.findOne(id);
        if (u == null) {
            throw new NotFoundException("不存在该用户");
        }
        BeanUtils.copyProperties(user,u,MyBeanUtils.getNullPropertyNames(user));
        u.setUpdateTime(new Date());
        u.setPassword(MD5Utils.code(user.getPassword()));
        return userRepository.save(u);
    }

}
