package com.xxmlp.web.admin;


import com.xxmlp.po.Server;
import com.xxmlp.po.Server;
import com.xxmlp.po.User;
import com.xxmlp.service.ServerService;
import com.xxmlp.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @GetMapping("/server")
    public String server(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page",serverService.listAllServer(pageable));
        return "admin/server";
    }

    @GetMapping("/server/input")
    public String input(Model model) {
        model.addAttribute("server", new Server());
        return "admin/server-input";
    }

    @GetMapping("/server/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("server", serverService.getServer(id));
        return "admin/server-input";
    }


    @PostMapping("/server")
    public String post(@Valid Server server, BindingResult result, RedirectAttributes attributes) {
        Server server1 = serverService.getServerByIp(server.getIp());
        if (server1 != null) {
            result.rejectValue("ip","nameError","地址已存在");
        }if (new ServerUtil().login(server.getIp(),server.getUsername(),server.getPassword())==false){
            result.rejectValue("ip","nameError","连接服务器失败，请检查ip地址或者账号密码是否正确");
        }
        if (result.hasErrors()) {
            return "admin/server-input";
        }
        Server t = serverService.saveServer(server);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/server";
    }


    @PostMapping("/server/{id}")
    public String editPost(@Valid Server server, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Server server1 = serverService.getServerByIp(server.getIp());
        if (server1 != null) {
            result.rejectValue("name","nameError","标签已存在");
        }
        if (result.hasErrors()) {
            return "admin/server-input";
        }
        Server t = serverService.updateServer(id,server);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/server";
    }

    @GetMapping("/server/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
            serverService.deleteServer(id);
            attributes.addFlashAttribute("message", "删除成功");
            return "redirect:/admin/server";
    }

}
