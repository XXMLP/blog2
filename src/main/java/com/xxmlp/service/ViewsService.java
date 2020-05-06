package com.xxmlp.service;

import com.xxmlp.po.Views;

public interface ViewsService {

    void saveViews(Views views);
    /**
     * 更新浏览数
     */
    void updateViewsSize(Long userId);


    Views isViewsed(Long blogId, Long userId);
}