package com.xxmlp.service;

import com.xxmlp.dao.BlogRepository;
import com.xxmlp.dao.CollectionRepository;
import com.xxmlp.dao.RelationshipRepository;
import com.xxmlp.dao.UserRepository;
import com.xxmlp.po.Blog;
import com.xxmlp.po.Collection;
import com.xxmlp.po.Relationship;
import com.xxmlp.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private BlogRepository blogRepository;
    @Override
    public Page<Blog> listCollection(Long userId, Pageable pageable) {
        List<Integer> collectionList = collectionRepository.findByUserId(userId);
        Page<Blog> blogPage = blogRepository.findByIdIn(collectionList, pageable);
        return blogPage;
    }
    @Override
    public void saveCollection(Collection collection) {
        //添加关注
        collectionRepository.save(collection);
        //更新收藏数
        updateCollectionSize(collection.getBlogId());
    }
    @Override
    public void removeCollection(Collection collection) {
        //删除关系
        collectionRepository.delete(collection);
        //更新收藏数
        updateCollectionSize(collection.getBlogId());
    }
    @Override
    public void updateCollectionSize(Long blogId) {
        Blog blog = blogRepository.findOne(blogId);
        blog.setCollected(collectionRepository.countByBlogId(blogId));
        blogRepository.save(blog);
    }

    @Override
    public Collection isCollected(Long blogId, Long userId) {
      Collection  collection=collectionRepository.isCollected(blogId,userId);
        return collection;
    }


}