package com.lrm.service;

import com.lrm.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserService {

    User saveUser(User user);

    User checkUser(String username, String password);

    User getUser(Long id);

    User getUserByName(String username);


    Page<User> listUser(Pageable pageable);

    Page<User> listUserInformation(Pageable pageable,Long id);


    User updateUser(Long id,User user);

    void deleteUser(Long id);

}
