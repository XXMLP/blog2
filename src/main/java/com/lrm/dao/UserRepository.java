package com.lrm.dao;

import com.lrm.po.Type;
import com.lrm.po.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {



    User findByUsernameAndPassword(String username, String password);

}
