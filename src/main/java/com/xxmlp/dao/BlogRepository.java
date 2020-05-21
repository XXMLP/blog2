package com.xxmlp.dao;

import com.xxmlp.po.Blog;
import com.xxmlp.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.recommend = true and b.user = ?1")
    List<Blog> findUserTop(Pageable pageable,User user);

    @Query("select b from Blog b where b.published = true")
    List<Blog> findNewAll(Pageable pageable);

    @Query("select b from Blog b where b.published = true and b.user = ?1 ")
    List<Blog> findUserNewAll(Pageable pageable,User user);

    @Query("select b from Blog b where b.published = true")
    Page<Blog> findAll(Pageable pageable);

    @Query("select b from Blog b where b.published = true and b.user = ?1")
    Page<Blog> findUserAll(Pageable pageable, User user);

    @Query("select b from Blog b where b.published = true and b.type.id = ?1")
    Page<Blog> findBlogByType(Pageable pageable,Long typeId);



    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Query("select b from Blog b where (b.title like ?1 or b.content like ?1) and b.published = true ")
    Page<Blog> findBySearch(String content,Pageable pageable);

    @Query("select b from Blog b where (b.title like ?1 or b.content like ?1) and b.published = true and b.user = ?2")
    Page<Blog> findByUserSearch(String content,Pageable pageable,User user);


    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 and b.published = true")
    List<Blog> findByYear(String year);

    /**
     * 根据id集合查询博客，分页查询
     *
     * @param ids
     * @return
     */
    Page<Blog> findByIdIn(List<Integer> ids, Pageable pageable);
    /**
     * 根据id集合查询博客，不分页
     *
     * @param ids
     * @return
     */
    List<Blog> findByIdIn(List<Integer> ids);
}
