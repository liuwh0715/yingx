package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * (YxAdmin)表控制层
 *
 * @author makejava
 * @since 2020-06-30 17:44:43
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    /**
     * 服务对象
     */
    @Autowired
    private AdminService adminService;

    /**
     * 通过主键查询单条数据
     *
     * @param admin
     * @param code
     * @return 单条数据
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, String> query(Admin admin, HttpSession session, String code) {
        Map<String, String> map = new HashMap<>();
        if (session.getAttribute("imageCode").equals(code)) {
            try {
                Admin admins = adminService.query(admin);
                map.put("msg", "success");
                session.setAttribute("admins", admins);
            } catch (Exception e) {
                map.put("msg", e.getMessage());
            } finally {
                return map;
            }
        } else {
            map.put("msg", "验证码有误");
            return map;
        }
    }

    /**
     * 验证码
     */
    @RequestMapping("code")
    @ResponseBody
    public void code(HttpSession session, HttpServletResponse response) {
        //获取验证码随机字符
        String code = ImageCodeUtil.getSecurityCode();
        //储存验证码随机字符
        session.setAttribute("imageCode", code);
        //获取验证码图片
        BufferedImage image = ImageCodeUtil.createImage(code);
        //将验证码响应到页面
        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("admins");
        return "redirect:/login/login.jsp";

    }
}