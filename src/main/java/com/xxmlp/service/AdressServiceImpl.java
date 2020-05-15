package com.xxmlp.service;

import com.xxmlp.dao.AddressRepository;
import com.xxmlp.po.Address;
import com.xxmlp.po.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AdressServiceImpl implements AdressService{

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    @Override
    public Address save(Address address) {
            address.setLoginTime(new Date());
        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public Page<Address> listAllLog(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }
}
