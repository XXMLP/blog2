package com.lrm.dao;

import com.lrm.po.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImgRepository extends JpaRepository<Img,Long>, JpaSpecificationExecutor<Img> {


}
