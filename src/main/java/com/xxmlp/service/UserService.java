package com.xxmlp.service;

import com.xxmlp.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    User saveUser(User user);

    User checkUser(String username, String password);

    User getUser(Long id);

    User getUserByName(String username);


    Page<User> listUser(Pageable pageable);

    List<User> listUser();

    Page<User> listUserInformation(Pageable pageable,Long id);


    User updateUser(Long id,User user);

    Integer totalViews(Long id);

    Integer totalBlogs(User user);

    void deleteUser(Long id);

}
