package com.xxmlp.service;

import com.xxmlp.po.Thumbs;

public interface ThumbsService {

    void saveThumbs(Thumbs thumbs);

    void removeThumbs(Thumbs thumbs);
    /**
     * 更新关注数
     */
    void updateThumbsSize(Long userId);

    Thumbs isThumbed(Long blogId, Long userId);
}