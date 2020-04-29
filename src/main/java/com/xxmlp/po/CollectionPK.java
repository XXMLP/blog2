package com.xxmlp.po;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CollectionPK implements Serializable {
    private Long userId;
    private Long blogId;
    @Column(name = "user_id", nullable = false)
    @Id
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Column(name = "blog_id", nullable = false)
    @Id
    public Long getBlogId() {
        return blogId;
    }
    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionPK that = (CollectionPK) o;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (blogId != null ? !blogId.equals(that.blogId) : that.blogId != null) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (blogId != null ? blogId.hashCode() : 0);
        return result;
    }
}