package com.xxmlp.service;

import com.xxmlp.po.Blog;
import com.xxmlp.po.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionService {
    /**
     * 列出所有的收藏的博客
     *
     * @return
     */
    Page<Blog> listCollection(Long userId, Pageable pageable);

    void saveCollection(Collection collection);

    void removeCollection(Collection collection);
    /**
     * 更新关注数
     */
    void updateCollectionSize(Long userId);

    Collection isCollected(Long blogId, Long userId);
}