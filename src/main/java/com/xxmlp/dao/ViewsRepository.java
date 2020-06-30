package com.xxmlp.dao;

import com.xxmlp.po.Views;
import com.xxmlp.po.ViewsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ViewsRepository extends JpaRepository<Views, ViewsPK> {

    @Query("select u from Views u where u.blogId = ?1 and u.userId = ?2")
    Views isViewsed(Long blogId, Long userId);
    
    Integer countByBlogId(Long blogId);

}