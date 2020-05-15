package com.xxmlp.service;


import com.xxmlp.po.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdressService {

    Address save(Address address);

    Page<Address> listAllLog(Pageable pageable);

}
