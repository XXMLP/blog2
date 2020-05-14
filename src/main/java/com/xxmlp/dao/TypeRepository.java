package com.xxmlp.dao;

import com.xxmlp.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);


    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

    @Query("select t from Type t where t.user.id = ?1")
    Page<Type> findByUserId(Pageable pageable, Long id);
}
