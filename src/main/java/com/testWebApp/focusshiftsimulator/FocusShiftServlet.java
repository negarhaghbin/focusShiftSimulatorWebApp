package com.testWebApp.focusshiftsimulator;

import com.testWebApp.movements.MouseMovement;
import com.testWebApp.movements.TargetMovement;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@WebServlet("/userStudyFinished")
public class FocusShiftServlet extends HttpServlet {
    private ArrayList< Pair<Double,Double> > parseEyeTrackingData(String eyeTrackingJSONString){
        ArrayList< Pair<Double,Double> > eyeTrackingData = new ArrayList<>();
        JSONArray eyeTrackingJSONArray = new JSONArray(eyeTrackingJSONString);

        for (int i=0;i<eyeTrackingJSONArray.length();i++){
            Pair<Double, Double> tempPoint = new Pair<>(eyeTrackingJSONArray.getJSONObject(i).getDouble("x") , eyeTrackingJSONArray.getJSONObject(i).getDouble("y"));
            eyeTrackingData.add(tempPoint);
        }
        return  eyeTrackingData;
    }

    private ArrayList<String> parseScreenshots(String screenShotHTMLsJSONString){
        ArrayList<String> screenShotHTMLs = new ArrayList<>();
        JSONArray screenShotHTMLsJSONArray = new JSONArray(screenShotHTMLsJSONString);

        for (int i=0;i<screenShotHTMLsJSONArray.length();i++){
            String tempHtml = screenShotHTMLsJSONArray.getString(i);
            screenShotHTMLs.add(tempHtml);
        }
        return screenShotHTMLs;
    }

    private HashMap<Integer, Integer> parseTargetWidthMap(String targetWidthMapJSONString){
        HashMap<Integer, Integer> targetWidthMap = new HashMap<>();
        JSONObject targetWidthMapJSONObject = new JSONObject(targetWidthMapJSONString);

        Iterator<String> keys = targetWidthMapJSONObject.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            targetWidthMap.put(Integer.parseInt(key),targetWidthMapJSONObject.getInt(key));
        }
        return targetWidthMap;
    }

    private HashMap<Integer, Point> parseCentersMap(String centersMapJSONString){
        return getIntegerPointHashMap(centersMapJSONString);
    }

    private HashMap<Integer, Point> parseErrorsMap(String errorsMapJSONString){
        return getIntegerPointHashMap(errorsMapJSONString);
    }

    private HashMap<Integer, Point> getIntegerPointHashMap(String mapJSONString) {
        HashMap<Integer, Point> map = new HashMap<>();
        JSONObject mapJSONObject = new JSONObject(mapJSONString);
        Iterator<String> keys = mapJSONObject.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            Point p = new Point(mapJSONObject.getJSONObject(key).getInt("x"), mapJSONObject.getJSONObject(key).getInt("y"));
            map.put(Integer.parseInt(key),p);
        }
        return map;
    }

    private MouseMovement parseMouseMovement(String mvJSONString){
        HashMap<Long, Point> mouseMovement = new HashMap<>();
        JSONObject mvJSONObject = new JSONObject(mvJSONString);
        Iterator<String> keys = mvJSONObject.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            Point p = new Point(mvJSONObject.getJSONObject(key).getInt("x"), mvJSONObject.getJSONObject(key).getInt("y"));
            mouseMovement.put(Long.parseLong(key),p);
        }
        return new MouseMovement(mouseMovement);
    }

    private TargetMovement parseTargetMovement(String tmJSONString){
        HashMap<Integer, Point> targetMovement = new HashMap<>();
        JSONObject tmJSONObject = new JSONObject(tmJSONString);
        Iterator<String> keys = tmJSONObject.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            Point p = new Point(tmJSONObject.getJSONObject(key).getInt("x"), tmJSONObject.getJSONObject(key).getInt("y"));
            targetMovement.put(Integer.parseInt(key),p);
        }
        return new TargetMovement(targetMovement);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        int calcGetArea = Integer.parseInt(request.getParameter("calcGetArea"));
        double averageAreaOfTarget = Double.parseDouble(request.getParameter("averageAreaOfTarget"));
        double averageWidths = Double.parseDouble(request.getParameter("averageWidths"));
        double durationTime = Double.parseDouble(request.getParameter("durationTime"));
        int errorCounter = Integer.parseInt(request.getParameter("errorCounter"));
        String eyeStrainRate = request.getParameter("eyeStrainRate");
        long mouseMovementIndex = Long.parseLong(request.getParameter("mouseMovementIndex"));
        int counter = Integer.parseInt(request.getParameter("counter"));
        int eyeTrackingPrecision = Integer.parseInt(request.getParameter("eyeTrackingAccuracy"));

        ArrayList< Pair<Double,Double> > eyeTrackingData = parseEyeTrackingData(request.getParameter("eyeTrackingData"));
        ArrayList<String> screenShotHTMLs = parseScreenshots(request.getParameter("screenshots"));
        HashMap<Integer, Integer> targetWidthMap = parseTargetWidthMap(request.getParameter("targetWidthMap"));
        HashMap<Integer, Point> centersMap = parseCentersMap(request.getParameter("centersMap"));
        HashMap<Integer, Point> errorsMap = parseErrorsMap(request.getParameter("errorsMap"));
        MouseMovement mv = parseMouseMovement(request.getParameter("mv"));
        TargetMovement tm = parseTargetMovement(request.getParameter("tm"));

        InteractionManager im = InteractionManager.getInstance();
        im.startTest(calcGetArea, averageAreaOfTarget, averageWidths, durationTime, errorCounter, eyeStrainRate, mouseMovementIndex, eyeTrackingData, screenShotHTMLs, targetWidthMap, centersMap, errorsMap, mv, tm, counter, eyeTrackingPrecision);
    }
}


