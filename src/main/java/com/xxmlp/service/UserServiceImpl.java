package com.xxmlp.service;

import com.xxmlp.NotFoundException;
import com.xxmlp.dao.UserRepository;
import com.xxmlp.po.User;
import com.xxmlp.util.MD5Utils;
import com.xxmlp.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User saveUser(User user){
        if (user.getId()==null){
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setPassword(MD5Utils.code(user.getPassword()));
            user.setType(0);
        }else{
            user.setUpdateTime(new Date());
        }
        user.setCommentabled(true);
        return userRepository.save(user);

    }

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
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }
    @Transactional
    @Override
    public User getUserByNameOrEmail(String username) {
        return userRepository.getUserByNameOrEmail(username);
    }

    @Transactional
    @Override
    public User getUserByNameKey(String username,String key) {
        return userRepository.findByUsernameKey(username,key);
    }


    @Transactional
    @Override
    public Page<User> listUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<User> listUserInformation(Pageable pageable,Long id) {
        return userRepository.findUserInformation(pageable,id);
    }

    @Override
    public List<User> listUser() {
        return userRepository.findAll();
    }


    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        User u = userRepository.findOne(id);
        if (u == null) {
            throw new NotFoundException("不存在该用户");
        }
        BeanUtils.copyProperties(user,u, MyBeanUtils.getNullPropertyNames(user));
        u.setPassword(MD5Utils.code(user.getPassword()));
        return userRepository.save(u);
    }

    @Transactional
    @Override
    public User updateUserInfo(User user) {
        if (user == null) {
            throw new NotFoundException("不存在该用户");
        }
        BeanUtils.copyProperties(user, MyBeanUtils.getNullPropertyNames(user));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User resetpwd(User user,String pwd) {
        if (user == null) {
            throw new NotFoundException("不存在该用户");
        }
        user.setPassword(MD5Utils.code(pwd));
        BeanUtils.copyProperties(user, MyBeanUtils.getNullPropertyNames(user));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public Integer totalViews(Long id){
        return userRepository.totalView(id);
    }

    @Override
    public Integer totalBlogs(User user){
        return userRepository.totalBlogs(user);
    }

    @Override
    public String findEmailByUsername(String username){
        return userRepository.findEmailByUsername(username);
    }


}
