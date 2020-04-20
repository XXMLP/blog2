package com.xxmlp.dao;

import com.xxmlp.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);

    @Query("select u from User u where u.username = ?1")
   User findByUsername(String username);

    @Query("select u from User u where u.id = ?1")
    Page<User> findUserInformation(Pageable pageable,Long id);

    @Query("select sum(b.views) from Blog b where b.published = true and b.user = ?1")
    Integer totalView(User user);

    @Query("select count(b) from Blog b where b.user = ?1")
    Integer totalBlogs(User user);


}
