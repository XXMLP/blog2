package com.xxmlp.service;


import com.xxmlp.po.Address;
import com.xxmlp.po.Blog;
import com.xxmlp.po.User;
import com.xxmlp.vo.AddrQuery;
import com.xxmlp.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdressService {

    Address save(Address address);

    Page<Address> listAllLog(Pageable pageable);

    Page<Address> listAddress(Pageable pageable, AddrQuery addrQuery);

    void deleteAddr(User user);
}
