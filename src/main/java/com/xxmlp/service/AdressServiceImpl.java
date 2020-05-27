package com.xxmlp.service;

import com.xxmlp.po.Address;
import com.xxmlp.po.User;
import com.xxmlp.vo.AddrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Service
@Component
public class AdressServiceImpl implements AdressService{
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @Transactional
//    @Override
//    public Address save(Address address) {
//            address.setLoginTime(new Date());
//        return addressRepository.save(address);
//    }
//
//    @Override
//    public Page<Address> listAddress(Pageable pageable, AddrQuery address) {
//        return addressRepository.findAll(new Specification<Address>() {
//            @Override
//            public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<>();
//                if (!"".equals(address.getAddress()) && address.getAddress() != null) {
//                    predicates.add(cb.like(root.<String>get("address"), "%"+address.getAddress()+"%"));
//                }
//                if (address.getUserId() != null) {
//                    predicates.add(cb.equal(root.<User>get("user").get("id"), address.getUserId()));
//                }
//                cq.where(predicates.toArray(new Predicate[predicates.size()]));
//                return null;
//            }
//        },pageable);
//    }
//
//        @Transactional
//        @Override
//        public void deleteAddr(User user) {
//        addressRepository.deleteAddressesByUser(user);
//        }

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
