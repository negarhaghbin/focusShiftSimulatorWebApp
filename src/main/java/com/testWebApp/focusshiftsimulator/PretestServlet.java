package com.testWebApp.focusshiftsimulator;

import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/PretestServlet")
public class PretestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        String studentID = request.getParameter("userId");
        String displayHours = request.getParameter("displayHours");
        ArrayList<String> symptoms = parseSymptoms(request.getParameter("symptoms"));

        InteractionManager im = InteractionManager.getInstance();
        im.displayHours = displayHours;
        im.userId = studentID;
        im.symptoms = symptoms;
    }

    private ArrayList<String> parseSymptoms(String symptomsJSONString){
        ArrayList<String> symptoms = new ArrayList<>();
        JSONArray symptomsJSONArray = new JSONArray(symptomsJSONString);

        for (int i=0;i<symptomsJSONArray.length();i++){
            String tempSymptom = symptomsJSONArray.getString(i);
            symptoms.add(tempSymptom);
        }
        return symptoms;
    }
}


