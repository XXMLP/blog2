package com.xxmlp.service;

import com.xxmlp.dao.BlogRepository;
import com.xxmlp.dao.ViewsRepository;
import com.xxmlp.po.Blog;
import com.xxmlp.po.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewsServiceImpl implements ViewsService {
    @Autowired
    private ViewsRepository viewsRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public void saveViews(Views views) {
        //添加关注
        viewsRepository.save(views);
        //更新点赞数
        updateViewsSize(views.getBlogId());
    }
    @Override
    public void updateViewsSize(Long blogId) {
        Blog blog = blogRepository.findOne(blogId);
        blog.setViews(viewsRepository.countByBlogId(blogId));
        blogRepository.save(blog);
    }

    @Override
    public Views isViewsed(Long blogId, Long userId) {
      Views  views=viewsRepository.isViewsed(blogId,userId);
        return views;
    }


}