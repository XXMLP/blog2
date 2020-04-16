package com.lrm.web.user;

import com.lrm.po.Img;
import com.lrm.po.User;
import com.lrm.service.ImgService;
import com.lrm.service.UserService;
import com.lrm.vo.ImgQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/user")
public class UserImgController {

    @Autowired
    private ImgService imgService;

    @Autowired
    private UserService userService;

    @GetMapping("/img")
    public String img(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model,ImgQuery img,HttpSession session,User user) {
        user=(User) session.getAttribute("user");
        img.setUserId(user.getId());
        model.addAttribute("userId",user.getId());
        model.addAttribute("users", userService.listUser());
        model.addAttribute("page",imgService.listImg(pageable,img));
        return "user/img";
    }

    @PostMapping("/img/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, ImgQuery img) {
        model.addAttribute("page", imgService.listImg(pageable, img));
        return "user/img :: imgList";
    }

    @GetMapping("/img/input")
    public String input(Model model) {
        model.addAttribute("img", new Img());
        return "user/img-input";
    }

    @GetMapping("/img/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("img", imgService.getImg(id));
        return "user/img-input";
    }


    @GetMapping("/img/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        imgService.deleteImg(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/user/img";
    }

    @PostMapping("/img")
    public String uploadImage(@RequestParam(value = "file") MultipartFile file, RedirectAttributes attributes,
                              @Valid Img img, HttpSession session) throws Exception{

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message","文件为空");
            return "user/img-input";
        }
        if (file.getSize()>1024*1024*100) {
            attributes.addFlashAttribute("message","文件过大，上传失败，请选择不超过100MB的文件");
            return "user/img-input";
        }
        String fileName = file.getOriginalFilename();  // 文件名
        //file.Transto 来保存上传的文件
        //String filePath = ClassUtils.getDefaultClassLoader().getResource(File.separator).getRPath() + "static"+File.separator+"uploadFile"+File.separator+file.getContentType(); // 上传后的路径
        //linux绝对路径 
        String filePath=File.separator+"uploadFile"+File.separator+file.getContentType()+File.separator+fileName;
        File path = new File(filePath);
        if (!path.getParentFile().exists()) {
            path.getParentFile().mkdirs();
        }
        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        img.setPath(filePath);
        img.setName(file.getOriginalFilename());
        if (file.getSize()<1024){
            img.setSize(file.getSize()+"B");
        }
        if (file.getSize()>=1024){
            img.setSize(file.getSize()/1024+"KB");
        }
        if (file.getSize()>1024*1024){
            img.setSize(file.getSize()/1024/1024+"MB");
        }
        img.setUser((User) session.getAttribute("user"));
        Img i = imgService.saveImg(img);
        if (i == null ) {
            attributes.addFlashAttribute("message", "上传失败");
        } else {
            attributes.addFlashAttribute("message", "上传成功");
        }
        return "redirect:/user/img";
    }

}
