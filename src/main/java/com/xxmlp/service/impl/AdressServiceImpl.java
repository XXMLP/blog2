package com.xxmlp.service.impl;

import com.xxmlp.po.Address;
import com.xxmlp.service.AdressService;
import com.xxmlp.vo.AddrQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class AdressServiceImpl implements AdressService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Address address) {
            address.setLoginTime(new Date());
            mongoTemplate.save(address);
    }

    @Override
    public Page<Address> listAddress(Pageable pageable, AddrQuery addrQuery) {
        Address address=new Address();
        if (addrQuery.getUserId()!=null){
            address.setUserId(addrQuery.getUserId());
        }if (addrQuery.getAddress()!=null){
            address.setAddress(addrQuery.getAddress());
        }
        Example<Address> example=Example.of(address);
        Query query=new Query(Criteria.byExample(example));
        long total = mongoTemplate.count(query,Address.class);
        List<Address> addressesList = mongoTemplate.find(query.with(pageable), Address.class);
        Page<Address> addressesPage = new PageImpl(addressesList, pageable, total);
        return addressesPage;
    }

    public void deleteAddr(Long userId){
        Address address=new Address();
        address.setUserId(userId);
        Example<Address> example=Example.of(address);
        Query query=new Query(Criteria.byExample(example));
            mongoTemplate.remove(query,Address.class);
    }

}
