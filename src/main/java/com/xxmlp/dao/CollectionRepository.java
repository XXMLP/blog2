package com.xxmlp.dao;

import com.xxmlp.po.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, CollectionPK> {

    @Query("select u from Collection u where u.blogId = ?1 and u.userId = ?2")
    Collection isCollected(Long blogId, Long userId);


    @Query("select blogId from Collection where userId =:userId")
    List<Integer> findByUserId(@Param("userId") Long userId);




    Integer countByBlogId(Long blogId);

}