package com.itheima.shiro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：订单列表
 */
@WebServlet(urlPatterns = "/order-list")
public class OrderListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得当前主体
        Subject subject = SecurityUtils.getSubject();
        //判断当前主体是否具有admin的角色
        boolean isAdmin = subject.hasRole("admin");
        if (isAdmin){
            req.getRequestDispatcher("order-list.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/login").forward(req, resp);
    }
}
