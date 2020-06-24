package com.xxmlp.service;


import com.xxmlp.po.Address;
import com.xxmlp.vo.AddrQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdressService {

    void save(Address address);

    Page<Address> listAddress(Pageable pageable, AddrQuery addrQuery);

    void deleteAddr(Long userId);
}
