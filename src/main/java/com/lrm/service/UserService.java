package com.lrm.service;

import com.lrm.po.Type;
import com.lrm.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserService {

    User checkUser(String username, String password);

    User getUser(Long id);


    Page<User> listUser(Pageable pageable);


    User updateUser(Long id,User user);

}
