package com.xxmlp.service;

import com.xxmlp.dao.BlogRepository;
import com.xxmlp.dao.ThumbsRepository;
import com.xxmlp.po.Blog;
import com.xxmlp.po.Thumbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThumbsServiceImpl implements ThumbsService {
    @Autowired
    private ThumbsRepository thumbsRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public void saveThumbs(Thumbs thumbs) {
        //添加关注
        thumbsRepository.save(thumbs);
        //更新点赞数
        updateThumbsSize(thumbs.getBlogId());
    }
    @Override
    public void removeThumbs(Thumbs thumbs) {
        //删除关系
        thumbsRepository.delete(thumbs);
        //更新点赞数
        updateThumbsSize(thumbs.getBlogId());
    }
    @Override
    public void updateThumbsSize(Long blogId) {
        Blog blog = blogRepository.findOne(blogId);
        blog.setThumbs(thumbsRepository.countByBlogId(blogId));
        blogRepository.save(blog);
    }

    @Override
    public Thumbs isThumbed(Long blogId, Long userId) {
      Thumbs  thumbs=thumbsRepository.isThumbed(blogId,userId);
        return thumbs;
    }


}