package com;

import com.google.gson.Gson;
import entity.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/16 0016.
 */
public class MyServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.write("<h1> hello first servlet! here is doing Post</h1>");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String data = request.getParameter("data");
        if ("json".equals(data)) {
            List<Item> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Item item = new Item();
                item.date = new Date();
                item.id = i;
                item.title = "title"+i;
                list.add(item);
            }
            Gson gson = new Gson();
            String json = gson.toJson(list);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(json);
        }else {

            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.write("<h1> hello this do get!</h1>");
        }



    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
