package com.xxmlp.dao;

import com.xxmlp.po.Thumbs;
import com.xxmlp.po.ThumbsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThumbsRepository extends JpaRepository<Thumbs, ThumbsPK> {

    @Query("select u from Thumbs u where u.blogId = ?1 and u.userId = ?2")
    Thumbs isThumbed(Long blogId, Long userId);

    Integer countByBlogId(Long blogId);

}