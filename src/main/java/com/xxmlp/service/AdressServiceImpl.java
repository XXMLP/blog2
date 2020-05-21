package com.xxmlp.service;

import com.xxmlp.dao.AddressRepository;
import com.xxmlp.po.Address;
import com.xxmlp.po.User;
import com.xxmlp.vo.AddrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public Page<Address> listAddress(Pageable pageable, AddrQuery address) {
        return addressRepository.findAll(new Specification<Address>() {
            @Override
            public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(address.getAddress()) && address.getAddress() != null) {
                    predicates.add(cb.like(root.<String>get("address"), "%"+address.getAddress()+"%"));
                }
                if (address.getUserId() != null) {
                    predicates.add(cb.equal(root.<User>get("user").get("id"), address.getUserId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

        @Transactional
        @Override
        public void deleteAddr(User user) {
        addressRepository.deleteAddressesByUser(user);
        }
}
