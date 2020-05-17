package com.xxmlp.dao;

import com.xxmlp.po.Address;
import com.xxmlp.po.Blog;
import com.xxmlp.po.Type;
import com.xxmlp.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long>, JpaSpecificationExecutor<Address> {

    @Modifying
    @Transactional
    @Query("delete from Address b where b.user = ?1")
    void deleteAddressesByUser(User user);
}
