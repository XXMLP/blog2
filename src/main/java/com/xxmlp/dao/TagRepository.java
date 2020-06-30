package com.xxmlp.dao;

import com.xxmlp.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

    @Query("select t from Tag t where t.user.id = ?1")
    Page<Tag> findByUserId(Pageable pageable,Long id);
}
