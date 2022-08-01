package com.itheima.shiro.web;

import com.itheima.shiro.service.LoginService;
import com.itheima.shiro.service.impl.LoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：home首页
 */
@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得当前主体
        Subject subject = SecurityUtils.getSubject();
        //当前主体是否登录
        boolean isAuthenticated = subject.isAuthenticated();
        if (isAuthenticated){
            req.getRequestDispatcher("home.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/login").forward(req, resp);
    }
}
