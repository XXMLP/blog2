package com.lrm.dao;

import com.lrm.po.Blog;
import com.lrm.po.Img;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img,Long>, JpaSpecificationExecutor<Img> {


}
