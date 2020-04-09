package com.lrm.web.admin;

import com.lrm.po.Img;
import com.lrm.po.User;
import com.lrm.service.ImgService;
import com.lrm.service.UserService;
import com.lrm.vo.BlogQuery;
import com.lrm.vo.ImgQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;


@Controller
@RequestMapping("/admin")
public class ImgController {

    @Autowired
    private ImgService imgService;

    @Autowired
    private UserService userService;

    @GetMapping("/img")
    public String img(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model,ImgQuery img) {
        model.addAttribute("users", userService.listUser());
        model.addAttribute("page",imgService.listImg(pageable,img));
        return "admin/img";
    }

    @PostMapping("/img/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, ImgQuery img) {
        model.addAttribute("page", imgService.listImg(pageable, img));
        return "admin/img :: imgList";
    }

    @GetMapping("/img/input")
    public String input(Model model) {
        model.addAttribute("img", new Img());
        return "admin/img-input";
    }

    @GetMapping("/img/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("img", imgService.getImg(id));
        return "admin/img-input";
    }


    @GetMapping("/img/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        imgService.deleteImg(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/img";
    }

    @PostMapping("/img")
    public String uploadImage(@RequestParam(value = "file") MultipartFile file, RedirectAttributes attributes,
                              @Valid Img img, HttpSession session) throws Exception{

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message","文件为空");
            return "admin/img-input";
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadFile/"+file.getContentType(); // 上传后的路径
        File path = new File(filePath + "/"+ fileName);
        if (!path.getParentFile().exists()) {
            path.getParentFile().mkdirs();
        }
        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        img.setPath("/uploadFile/"+file.getContentType()+"/"+fileName);
        img.setName(file.getOriginalFilename());
        if (file.getSize()<1024){
            img.setSize(file.getSize()+"B");
        }
        if (file.getSize()>=1024){
            img.setSize(file.getSize()/1024+"K");
        }
        if (file.getSize()>1024*1024){
            img.setSize(file.getSize()/1024/1024+"M");
        }
        img.setUser((User) session.getAttribute("user"));
        Img i = imgService.saveImg(img);
        if (i == null ) {
            attributes.addFlashAttribute("message", "上传失败");
        } else {
            attributes.addFlashAttribute("message", "上传成功");
        }
        return "redirect:/admin/img";
    }

}
