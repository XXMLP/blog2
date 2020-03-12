package com.lrm.service;

import com.lrm.po.Comment;
import com.lrm.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    Page<Comment> listComment(Pageable pageable);

    void deleteComment(Long id);
}
