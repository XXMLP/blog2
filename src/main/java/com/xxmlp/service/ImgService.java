package com.xxmlp.service;

import com.xxmlp.po.Img;
import com.xxmlp.vo.ImgQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImgService {

    Img saveImg(Img img);

    Img getImg(Long id);

    Page<Img> listImg(Pageable pageable, ImgQuery img);

    void deleteImg(Long id);
}
