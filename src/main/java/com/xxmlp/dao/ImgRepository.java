package com.xxmlp.dao;

import com.xxmlp.po.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImgRepository extends JpaRepository<Img,Long>, JpaSpecificationExecutor<Img> {


}
