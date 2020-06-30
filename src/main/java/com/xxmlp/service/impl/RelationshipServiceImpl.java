package com.xxmlp.service.impl;

import com.xxmlp.dao.RelationshipRepository;
import com.xxmlp.dao.UserRepository;
import com.xxmlp.po.Relationship;
import com.xxmlp.po.User;
import com.xxmlp.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> listFollows(Long userId, Pageable pageable) {
        List<Integer> relationshipList = relationshipRepository.findByFromUserId(userId);
        Page<User> userPage = userRepository.findByIdIn(relationshipList, pageable);
        return userPage;
    }

    @Override
    public Page<User> listFans(Long userId, Pageable pageable) {
        List<Integer> relationshipList = relationshipRepository.findByToUserId(userId);
        Page<User> userPage = userRepository.findByIdIn(relationshipList, pageable);
        return userPage;
    }

    @Override
    public Page<User> listFriends(Long userId,Pageable pageable) {
        List<Integer> relationshipList = relationshipRepository.findFriendsByUserId(userId);
        Page<User> userPage = userRepository.findByIdIn(relationshipList, pageable);
        return userPage;
    }

    @Override
    public void saveRelationship(Relationship relationship) {
        //添加关注
        relationshipRepository.save(relationship);
        //更新双方关注数和粉丝数
        updateFollowSize(relationship.getFromUserId());
        updateFanSize(relationship.getToUserId());
    }

    @Override
    public void removeRelationship(Relationship relationship) {
        //删除关系
        relationshipRepository.delete(relationship);
        //更新双方关注数和粉丝数
        updateFollowSize(relationship.getFromUserId());
        updateFanSize(relationship.getToUserId());
    }

    @Override
    public void updateFollowSize(Long userId) {
        User user = userRepository.findOne(userId);
        user.setFollowSize(relationshipRepository.countByFromUserId(userId));
        userRepository.save(user);
    }

    @Override
    public void updateFanSize(Long userId) {
        User user = userRepository.findOne(userId);
        user.setFanSize(relationshipRepository.countByToUserId(userId));
        userRepository.save(user);
    }

    @Override
    public Relationship isAttention(Long fromUserId, Long toUserId) {
      Relationship  relationship=relationshipRepository.isAttention(fromUserId,toUserId);
        return relationship;
    }

}