package com.zetcode.web;

import com.zetcode.service.CarService;
import com.zetcode.util.Utils;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

@WebServlet(name = "ManageCars", urlPatterns = {"/ManageCars"})
public class ManageCars extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray ar = CarService.getCarsJSON();

        response.getWriter().write(ar.toJSONString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String name = request.getParameter("NAME");
        int price = Integer.valueOf(request.getParameter("PRICE"));

        CarService.insertCar(name, price);

        getServletContext().log("Car " + name + " inserted");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        Map<String, String> dataMap = Utils.getParameterMap(request);

        String carName = dataMap.get("NAME");
        int carPrice = Integer.valueOf(dataMap.get("PRICE"));

        CarService.updateCar(carName, carPrice);

        getServletContext().log("Car " + carName + " updated" + carPrice);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        Map<String, String> dataMap = Utils.getParameterMap(request);

        String carName = dataMap.get("NAME");

        CarService.deleteCar(carName);

        getServletContext().log("Car:" + carName + " deleted");
    }
}
