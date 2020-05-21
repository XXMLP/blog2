package com.xxmlp.dao;

import com.xxmlp.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    @Query("select b from Comment b where b.user.id = ?1")
    List<Comment> findByUserIdAndParentCommentNull(Long userId, Sort sort);

    @Query("select b from Comment b where b.checkComment = false")
    Page<Comment> findByNewComment(Pageable pageable);
    @Query("select b from Comment b where b.checkComment = true")
    Page<Comment> findByOldComment(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Comment c set c.checkComment = true where c.id = ?1")
    void check(Long id);
}
