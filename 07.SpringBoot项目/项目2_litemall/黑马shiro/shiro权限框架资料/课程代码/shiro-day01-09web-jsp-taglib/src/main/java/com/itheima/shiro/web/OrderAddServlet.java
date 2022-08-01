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
 * @Description：添加订单
 */
@WebServlet(urlPatterns = "/order-add")
public class OrderAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //拿到当前主体
        Subject subject = SecurityUtils.getSubject();
        //判断当前主体是否有对应权限
        boolean isPermitted = subject.isPermitted("order:add");
        if (isPermitted){
            req.getRequestDispatcher("order-add.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/login").forward(req, resp);
    }
}
