package com.chapter111.controller;

import com.chapter111.entity.TbUser;
import com.chapter111.service.TbUserService;
import com.chapter111.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TbUserController {
    @Autowired
    private TbUserService tbUserService;
    @RequestMapping(value = "/user/register",method = RequestMethod.GET)
    public String register(){
        return "jsp/register";
    }
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public String addUser(@ModelAttribute TbUser user,Model model){
        TbUser tbUser = new TbUser();
        tbUser.setUsername(user.getUsername());
        List<TbUser> list = tbUserService.selectSelective(tbUser);
        if (list.size() == 0){
            user.setPassword(Encryption.MD5(user.getPassword()));
            if (tbUserService.insert(user) == 1){
                model.addAttribute("status", 0);
            } else {
                model.addAttribute("status", 1);
            }
        } else {
            model.addAttribute("status", 2);
        }
        return "jsp/register";
    }

    @RequestMapping(value="/user/login", method = RequestMethod.GET)
    public String login() {
        return "jsp/login";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String loginValidate(HttpSession session, Model model, @ModelAttribute TbUser user) {
        TbUser tbUser  = new TbUser();
        tbUser.setUsername(user.getUsername());
        List<TbUser> list = tbUserService.selectSelective(tbUser);
        if (list.size() == 0) {
            model.addAttribute("status", 1);
        } else {
            tbUser.setPassword(Encryption.MD5(user.getPassword()));
            list = tbUserService.selectSelective(tbUser);
            if (list.size() == 0) {
                model.addAttribute("status", 2);
            }else {
                tbUser = list.get(0);
                session.setAttribute("userInfo", tbUser);
                model.addAttribute("status", 0);
            }
        }
        return "jsp/login";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        //session.removeAttribute("user");
        return "jsp/login";
    }

    @RequestMapping(value="/user/userInfo", method = RequestMethod.GET)
    public String TbUser(Model model, HttpSession session) {
        TbUser tbUser = (TbUser) session.getAttribute("userInfo");
        if(tbUser != null){
            model.addAttribute("tbUser", tbUser);
        }
        return "jsp/userInfo";
    }
}
