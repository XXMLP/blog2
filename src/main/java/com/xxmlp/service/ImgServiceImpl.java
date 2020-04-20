package com.xxmlp.service;

import com.xxmlp.dao.ImgRepository;
import com.xxmlp.po.Img;
import com.xxmlp.po.User;
import com.xxmlp.vo.ImgQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImgServiceImpl implements ImgService {

    @Autowired
    private ImgRepository imgRepository;

    @Transactional
    @Override
    public Img saveImg(Img img) {
        if (img.getId() == null) {
            img.setUploadTime(new Date());
            img.setUpdateTime(new Date());
        } else {
            img.setUpdateTime(new Date());
        }
        return imgRepository.save(img);
    }

    @Transactional
    @Override
    public Img getImg(Long id) {
        return imgRepository.findOne(id);
    }

    @Transactional
    @Override
    public void deleteImg(Long id) {
        imgRepository.delete(id);
    }

    @Override
    public Page<Img> listImg(Pageable pageable, ImgQuery img) {
        return imgRepository.findAll(new Specification<Img>() {
            @Override
            public Predicate toPredicate(Root<Img> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(img.getDescript()) && img.getDescript() != null) {
                    predicates.add(cb.like(root.<String>get("descript"), "%" + img.getDescript() + "%"));
                }
                if (!"".equals(img.getType()) && img.getType() != null) {
                    predicates.add(cb.equal(root.<String>get("type"), img.getType()));
                }
                if (img.getUserId() != null) {
                    predicates.add(cb.equal(root.<User>get("user").get("id"), img.getUserId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

}
