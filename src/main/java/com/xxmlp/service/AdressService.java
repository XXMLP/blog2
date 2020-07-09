package com.xxmlp.service;


import com.xxmlp.po.Address;
import com.xxmlp.vo.AddrQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AdressService {

    void save(Address address);

    Page<Address> listAddress(Pageable pageable, AddrQuery addrQuery);

    void deleteAddr(Long userId);

    Long countNetType(String netType);

    Long countOthers();

    Long countDeviceType(String deviceType);

    Long countOthersDevice();

    Long countDate(Date startTime,Date endTime);

    Long countAddress(String address);
}
