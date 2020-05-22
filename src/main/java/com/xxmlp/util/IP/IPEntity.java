package com.xxmlp.util.IP;

public class IPEntity {
    String nation;   //国家:0级地址
    String province; //省:0级地址
    String city;     //市:1级地址
    String region;   //区:2级地址

    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public IPEntity(){

    }

}