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
import java.util.regex.Pattern;

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
        }
        Example<Address> example=Example.of(address);
        Query query=new Query(Criteria.byExample(example));
       if (addrQuery.getAddress()!=null && !addrQuery.getAddress().equals("")){
           Pattern pattern = Pattern.compile("^.*"+addrQuery.getAddress()+".*$", Pattern.CASE_INSENSITIVE);
           query.addCriteria(Criteria.where("address").regex(pattern));
        }
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

    @Override
    public Long countNetType(String netType) {
        // 模糊匹配正则规则
        Pattern pattern = Pattern.compile("^.*"+netType+".*$", Pattern.CASE_INSENSITIVE);
        Query query=new Query();
        query.addCriteria(Criteria.where("netType").regex(pattern));
        long countNetType = mongoTemplate.count(query,Address.class);
        return countNetType;
    }

    @Override
    public Long countOthers() {
        Address address=new Address();
        Example<Address> example=Example.of(address);
        Query query=new Query(Criteria.byExample(example));
        long total = mongoTemplate.count(query,Address.class);
        long totalOhers=total-countNetType("移动")-countNetType("联通")-countNetType("电信")-countNetType("腾讯")-countNetType("阿里")-countNetType("CZ88");
        return totalOhers;
    }

    @Override
    public Long countDeviceType(String deviceType) {
        // 模糊匹配正则规则
        Pattern pattern = Pattern.compile("^.*"+deviceType+".*$", Pattern.CASE_INSENSITIVE);
        Query query=new Query();
        query.addCriteria(Criteria.where("deviceType").regex(pattern));
        long countDeviceType = mongoTemplate.count(query,Address.class);
        return countDeviceType;
    }

    @Override
    public Long countOthersDevice() {
        Address address=new Address();
        Example<Address> example=Example.of(address);
        Query query=new Query(Criteria.byExample(example));
        long total = mongoTemplate.count(query,Address.class);
        long totalOhers=total-countDeviceType("MOBILE")-countDeviceType("COMPUTER");
        return totalOhers;
    }

    @Override
    public Long countDate(Date startTime,Date endTime) {
        Address address=new Address();
        Example<Address> example=Example.of(address);
        Query query=new Query(Criteria.byExample(example));
        query.addCriteria(Criteria.where("loginTime").gte(startTime).lte(endTime));
        long total = mongoTemplate.count(query,Address.class);
        return total;
    }

    @Override
    public Long countAddress(String province) {
        Query query=new Query();
        Pattern pattern = Pattern.compile("^.*"+province+".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("address").regex(pattern));
        long countAddress = mongoTemplate.count(query,Address.class);
        return countAddress;
    }
}
