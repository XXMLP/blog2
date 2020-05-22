package com.xxmlp.util.IP;

import java.io.File;

/**
 * @Note 地址切分____将全域地址切分为：  0级地址：国家或省
 * 									  1级地址：市
 * 									  2级地址：区
 *
 * 		 调用时直接调用IPseeker类：eg.  ipseeker.getAddress("xxx.xxx.xxx.xxx");
 *                                    ipseeker.getCountry("xxx.xxx.xxx.xxx");
 *                                    ipseeker.getIsp("xxx.xxx.xxx.xxx");
 *       由于ipentity.nation,province,city,region是作的切分,故 ipentity.getNation()等没有输入参数,调用直接输出
 *       							  ipentity.getNation();
 *       							  ipentity.getProvince();
 *       							  ipentity.getCity();
 *             						  ipentity.getRegion();
 *       测试ip:202.112.110.0 -----北京市 北京邮电大学
 *       		27.184.95.146 ------河北省石家庄市 电信
 *              219.136.134.157 -----广东省广州市越秀区 电信ADSL
 *              59.45.1.151 --------辽宁省盘锦市双台子区 胜星网吧(辽河北路与城北路交汇)
 *              61.128.101.255 -----新疆乌鲁木齐市 电信
 *              220.182.50.226 -----西藏拉萨市 波斯湾网络会所(宇拓路30号)
 *              210.136.134.157 -----日本  CZ88.NET
 *              207.46.13.93 -----美国  Microsoft公司
 *              27.122.12.0 ------香港 智通(Pacswitch)环球电讯
 *
 */
public class SplitAddress {
    public static void main(String[] args){
        try {
            IPSeeker ipseeker = new IPSeeker(new File("src/main/java/com/xxmlp/util/IP/qqwry.dat"));
            IPEntity ipentity = new IPEntity();
            String ipaddress = "182.254.52.17";
            SplitAddress splitaddress = new SplitAddress();
            splitaddress.SplitAddressAction(ipaddress, ipseeker, ipentity); //切分获得多级地址

            System.out.println("完整ip信息："+ipseeker.getAddress(ipaddress));
            System.out.println("完全地址:省/市/区:"+ipseeker.getCountry(ipaddress));
            System.out.println("nation:"+ipentity.getNation());
            System.out.println("province:"+ipentity.getProvince());
            System.out.println("city:"+ipentity.getCity());
            System.out.println("region:"+ipentity.getRegion());
            System.out.println("使用的网络(运营商ISP):"+ipseeker.getIsp(ipaddress));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SplitAddressAction(String ipaddress, IPSeeker ipseeker, IPEntity ipentity){
        try {
            String alladdress = ipseeker.getCountry(ipaddress);
            String[] part;
            //全国省市里唯一没有"市"字样的只有这4个省,直接作逗号","切分
            if(alladdress.startsWith("新疆")){
                ipentity.setProvince("新疆");
                alladdress = alladdress.replace("新疆", "新疆,");
            }
            else if(alladdress.startsWith("西藏")){
                ipentity.setProvince("西藏");
                alladdress = alladdress.replace("西藏", "西藏,");
            }
            else if(alladdress.startsWith("内蒙古")){
                ipentity.setProvince("内蒙古");
                alladdress = alladdress.replace("内蒙古", "内蒙古,");
            }
            else if(alladdress.startsWith("宁夏")){
                ipentity.setProvince("宁夏");
                alladdress = alladdress.replace("宁夏", "宁夏,");
            }
            alladdress = alladdress.replaceAll("省", "省,").replaceAll("市", "市,"); //最多切成3段:辽宁省,盘锦市,双台子区;
            part = alladdress.split(",");

            if(part.length==1){
                //只有1级地址
                ipentity.setNation(part[0]);
                ipentity.setProvince(part[0]);
            }
            else if(part.length==2){
                //有2级地址
                ipentity.setProvince(part[0]);
                ipentity.setCity(part[1]);
            }
            else if(part.length==3){
                //有3级地址
                ipentity.setProvince(part[0]);
                ipentity.setCity(part[1]);
                ipentity.setRegion(part[2]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}