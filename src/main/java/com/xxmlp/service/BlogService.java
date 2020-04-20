package com.xxmlp.service;

import com.xxmlp.po.Blog;
import com.xxmlp.po.User;
import com.xxmlp.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listTypeBlog(Pageable pageable,Long typeId);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listUserBlog(Pageable pageable, User user);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    Page<Blog> listSearchBlog(String content,Pageable pageable);

    Page<Blog> listUserSearchBlog(String content,Pageable pageable,User user);

    List<Blog> listRecommendBlogTop(Integer size);

    List<Blog> listUserRecommendBlogTop(Integer size,User user);

    List<Blog> listNewBlogTop(Integer size);

    List<Blog> listUserNewBlogTop(Integer size,User user);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);


    void deleteBlog(Long id);
}
