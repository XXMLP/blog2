package com.lrm.service;

import com.lrm.po.Img;
import com.lrm.vo.ImgQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImgService {
    Img saveImg(Img img);

    Img getImg(Long id);


    Page<Img> listImg(Pageable pageable);

    Page<Img> listImg(Pageable pageable, ImgQuery img);


    void deleteImg(Long id);
}
