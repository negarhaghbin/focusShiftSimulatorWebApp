package com.testWebApp.focusshiftsimulator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/PretestServlet")
public class PretestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        String studentID = request.getParameter("userId");
        String displayHours = request.getParameter("displayHours");

        InteractionManager im = InteractionManager.getInstance();
        im.displayHours = displayHours;
        im.userId = studentID;
    }
}
