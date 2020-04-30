package com.xxmlp.dao;

import com.xxmlp.po.Relationship;
import com.xxmlp.po.RelationshipPK;
import com.xxmlp.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipPK> {

    @Query("select u from Relationship u where u.fromUserId = ?1 and u.toUserId = ?2")
    Relationship isAttention(Long fromUserId, Long toUserId);
    /**
     * 根据关注者id查找所有记录（查找关注的人的id）
     *
     * @param fromUserId
     * @return
     */
    @Query("select toUserId from Relationship where fromUserId =:fromUserId")
    List<Integer> findByFromUserId(@Param("fromUserId") Long fromUserId);
    /**
     * 根据被关注者查找所有记录（查找粉丝的id）
     *
     * @param toUserId
     * @return
     */
    @Query("select fromUserId from Relationship where toUserId =:toUserId")
    List<Integer> findByToUserId(@Param("toUserId") Long toUserId);
    /**
     * 查询该用户的互相关注id
     * @param userId
     * @return
     */
                @Query("select a.toUserId from Relationship a,Relationship b\n" +
            "where a.fromUserId = b.toUserId\n" +
            "and a.toUserId = b.fromUserId\n" +
            "and a.fromUserId = ?1")
    List<Integer> findFriendsByUserId(Long userId);
    /**
     * 查询关注数
     * @param fromUserId
     * @return
     */
    Integer countByFromUserId(Long fromUserId);
    /**
     * 查询粉丝数
     * @param toUserId
     * @return
     */
    Integer countByToUserId(Long toUserId);
}