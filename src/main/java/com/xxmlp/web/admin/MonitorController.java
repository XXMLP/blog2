package com.xxmlp.web.admin;

import com.xxmlp.service.AdressService;
import com.xxmlp.util.DateStartAndEnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class MonitorController {

    private static final String[] province={"北京","陕西","甘肃","青海","宁夏","新疆","天津","上海","重庆","河北","山西","辽宁","吉林", "黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南","台湾", "内蒙古","广西","西藏","香港","澳门","南海"};

    @Autowired
    private AdressService adressService;

    @GetMapping("/monitor")
    public String monitor(Model model) {

        //近半个月国内用户分布图
        long max=adressService.countAddress(province[0]);
        for (int i=0;i<province.length;i++){
             model.addAttribute("p"+i,adressService.countAddress(province[i]));
             if (adressService.countAddress(province[i])>max)
              max=adressService.countAddress(province[i]);
        }
        model.addAttribute("max",max);

        //近半月网络类型分布
        model.addAttribute("a",adressService.countNetType("移动"));
        model.addAttribute("b",adressService.countNetType("联通"));
        model.addAttribute("c",adressService.countNetType("电信"));
        model.addAttribute("d",adressService.countNetType("腾讯"));
        model.addAttribute("e",adressService.countNetType("阿里"));
        model.addAttribute("f",adressService.countNetType("CZ88"));
        model.addAttribute("g",adressService.countOthers());

        //近半月设备类型分布
        model.addAttribute("computer",adressService.countDeviceType("COMPUTER"));
        model.addAttribute("mobile",adressService.countDeviceType("MOBILE"));
        model.addAttribute("otherDevice",adressService.countOthersDevice());

        //近十天浏览量
        Date date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        //当天统计数据
        model.addAttribute("time",df.format(date));
        model.addAttribute("day",adressService.countDate(DateStartAndEnd.getStartTime(date),DateStartAndEnd.getEndTime(date)));
        Calendar calendar = Calendar.getInstance();
        for (int i=1;i<=6;i++){
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,-i);
            Date day=calendar.getTime();
            model.addAttribute("time"+i,df.format(day));
            model.addAttribute("day"+i,adressService.countDate(DateStartAndEnd.getStartTime(day),DateStartAndEnd.getEndTime(day)));
        }
        return "monitor/monitor_users";
    }
}
