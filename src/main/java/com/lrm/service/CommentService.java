package com.lrm.service;

import com.lrm.po.Blog;
import com.lrm.po.Comment;
import com.lrm.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    Page<Comment> listNewComment(Pageable pageable);
    Page<Comment> listOldComment(Pageable pageable);
    Page<Comment> listAllComment(Pageable pageable);

    void checkComment(Long id);


    void deleteComment(Long id);
}
