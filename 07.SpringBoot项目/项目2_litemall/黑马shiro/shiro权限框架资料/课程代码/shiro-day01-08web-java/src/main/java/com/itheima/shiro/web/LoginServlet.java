package com.itheima.shiro.web;

import com.itheima.shiro.service.LoginService;
import com.itheima.shiro.service.impl.LoginServiceImpl;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：登录web层
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得用户名和密码
        String username = req.getParameter("loginName");
        String password = req.getParameter("password");
        //构建登录使用的token
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //登录操作
        LoginService loginService = new LoginServiceImpl();
        boolean isLogin = loginService.login(token);
        //如果登录成功，跳转home.jsp
        if (isLogin){
            req.getRequestDispatcher("/home").forward(req, resp);
            return;
        }
        //如果登录失败，跳转继续登录页面
        resp.sendRedirect("login.jsp");
    }
}
