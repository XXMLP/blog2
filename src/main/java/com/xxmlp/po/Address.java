package com.xxmlp.po;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name = "t_addr")
@Document(collection = "address")
public class Address implements Serializable {

//    @Id
//    @GeneratedValue
//    private Long id;

    @NotBlank(message = "IP不能为空")
    private String ip;


//    @NotBlank(message = "地址不能为空")
    private String address;

    private String deviceType;

    private String netType;

    private String webName;

    private String webType;

    private String webVersion;

    private String sysName;

    private String Manufacturer;

    private Long userId;

    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getWebType() {
        return webType;
    }

    public void setWebType(String webType) {
        this.webType = webType;
    }

    public String getWebVersion() {
        return webVersion;
    }

    public void setWebVersion(String webVersion) {
        this.webVersion = webVersion;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ip='" + ip + '\'' +
                ", address='" + address + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", netType='" + netType + '\'' +
                ", webName='" + webName + '\'' +
                ", webType='" + webType + '\'' +
                ", webVersion='" + webVersion + '\'' +
                ", sysName='" + sysName + '\'' +
                ", Manufacturer='" + Manufacturer + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }
}
