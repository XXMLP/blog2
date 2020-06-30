package com.xxmlp.dao;

import com.xxmlp.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);

    @Query("select u from User u where u.username = ?1 and u.validataCode = ?2")
    User findByUsernameKey(String username, String key);

    @Query("select u from User u where u.username = ?1")
   User findByUsername(String username);

    @Query("select u from User u where u.username = ?1 or u.email = ?1")
   User getUserByNameOrEmail(String username);

    @Query("select u.email from User u where u.username = ?1")
   String findEmailByUsername(String username);

    @Query("select u from User u where u.id = ?1")
    Page<User> findUserInformation(Pageable pageable,Long id);

    @Query("select sum(b.views) from Blog b where b.user.id = ?1")
    Integer totalView(Long id);

    @Query("select count(b) from Blog b where b.user = ?1 and b.published = true ")
    Integer totalBlogs(User user);

    @Query("select b from User b")
    List<User> findNewAll(Pageable pageable);

    /**
     * 根据id集合查询用户，分页查询
     *
     * @param ids
     * @return
     */
    Page<User> findByIdIn(List<Integer> ids, Pageable pageable);
}
