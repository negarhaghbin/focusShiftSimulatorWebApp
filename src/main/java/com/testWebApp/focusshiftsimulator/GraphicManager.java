package com.testWebApp.focusshiftsimulator;

//import javafx.util.Pair;

import com.testWebApp.io.FileOperation;
import com.testWebApp.movements.Click;
import com.testWebApp.movements.MouseClicks;
import com.testWebApp.movements.MouseMovement;
import com.testWebApp.movements.TargetMovement;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Mohsen Parisay <mohsenparisay@gmail.com>
 * @version 1.0 <24.Dec.2020>
 */
public class GraphicManager{

    private final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
    private final DecimalFormat df = (DecimalFormat) nf;


    private long mouseMovementIndex = 0;

    private HashMap<Integer, Integer> targetWidthMap;
    private HashMap<Integer, Point> centersMap;
    private HashMap<Integer, Point> errorsMap;

    private ArrayList < Pair > eyeTrackingData;
    private ArrayList<String> screenShotHTMLs;

    private ArrayList<String> symptoms;

    private ArrayList<Double> distancesList;

    private MouseMovement mv;
    private TargetMovement tm;
    private MouseClicks mc;

    private double durationTime = -1;
    private final String SEPARATOR = ";";
    private int errorCounter = 0;
    private String eyeStrainRate;

    private int counter = 0;

    private String userId;
    private String displayHours;
    private int screenWidth;
    private int screenHeight;
    private double averageAreaOfTarget;
    private double averageWidths;

    private final Calculator calc = new Calculator();
    private FileOperation fo;

    private int eyeTrackingPrecision;

    public void init(String userId, String displayHours, double averageAreaOfTarget, double averageWidths, double durationTime, int errorCounter, String eyeStrainRate, long mouseMovementIndex, ArrayList < Pair > eyeTrackingData, ArrayList<String> screenShotHtmls, HashMap<Integer, Integer> targetWidthMap, HashMap<Integer, Point> centersMap, HashMap<Integer, Point> errorsMap, MouseMovement mv, TargetMovement tm, int counter, int eyeTrackingPrecision, int screenWidth, int screenHeight, ArrayList<String> symptoms, MouseClicks mc) {
        this.userId = userId;
        this.displayHours = displayHours;
        this.averageAreaOfTarget = averageAreaOfTarget;
        this.averageWidths = averageWidths;
        this.durationTime = durationTime;
        this.errorCounter = errorCounter;
        this.mouseMovementIndex = mouseMovementIndex;
        this.eyeStrainRate = eyeStrainRate;
        this.eyeTrackingData = eyeTrackingData;
        this.screenShotHTMLs = screenShotHtmls;
        this.targetWidthMap = targetWidthMap;
        this.centersMap = centersMap;
        this.errorsMap = errorsMap;
        this.mv = mv;
        this.tm = tm;
        this.counter = counter;
        this.eyeTrackingPrecision = eyeTrackingPrecision;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.symptoms = symptoms;
        this.mc = mc;


        df.applyPattern("#.##");
        fo = new FileOperation();
    }


    void finishTrial(){
        saveMovements();
        saveClicks();
        saveEyeTracking();
        saveScreenshots();
        //saveScreenCaptureDate();

        saveCenterPoints();
        saveWidths();
        saveErrors();

        generateSummary();
    }

    private void saveEyeTracking() {
        String eyeTrackingFileName = "eye_tracking.csv";

        StringBuilder eyeData = new StringBuilder();
        String temp;

        eyeData.append("X;Y \n");

        for (Pair eyePoint : eyeTrackingData) {
            String x = String.format("%.2f", eyePoint.getKey());
            String y = String.format("%.2f", eyePoint.getValue());

            temp = x + SEPARATOR + y;
            eyeData.append(temp);
            eyeData.append("\n");
        }
        fo.writeOutput(eyeTrackingFileName, eyeData.toString());

    }

    private void saveMovements() {
        //should receive mv and tm from client
        String mouseFileName = "pointer_movements.csv";
        String targetFileName = "target_movements.csv";

        HashMap<Long, Point> mouseMovements = mv.getMovements();
        StringBuilder mouseData = new StringBuilder();
        String temp;

        mouseData.append("INDEX;X;Y \n");

        for (Map.Entry<Long, Point> entry : mouseMovements.entrySet()) {
            Long key = entry.getKey();
            Point value = entry.getValue();

            temp = key + SEPARATOR + value.getX() + SEPARATOR + value.getY();
            mouseData.append(temp);
            mouseData.append("\n");
        }
        fo.writeOutput(mouseFileName, mouseData.toString());

        HashMap<Integer, Point> targetMovements = tm.getMovements();
        StringBuilder targetData = new StringBuilder();
        String tempTarget;

        targetData.append("INDEX;X;Y \n");
        for (Map.Entry<Integer, Point> entry : targetMovements.entrySet()) {
            Integer key = entry.getKey();
            Point value = entry.getValue();

            tempTarget = key + SEPARATOR + value.getX() + SEPARATOR + value.getY();
            targetData.append(tempTarget);
            targetData.append("\n");
        }
        fo.writeOutput(targetFileName, targetData.toString());
    }

    private void saveClicks() {
        String mouseClicksFileName = "pointer_clicks.csv";

        HashMap<Integer, Click> mouseClicks = mc.getClicks();
        StringBuilder targetData = new StringBuilder();
        String tempTarget;

        targetData.append("INDEX;X;Y;STATUS \n");
        for (Map.Entry<Integer, Click> entry : mouseClicks.entrySet()) {
            Integer key = entry.getKey();
            Click value = entry.getValue();

            tempTarget = key + SEPARATOR + value.getPoint().getX() + SEPARATOR + value.getPoint().getY() + SEPARATOR + value.getStatus();
            targetData.append(tempTarget);
            targetData.append("\n");
        }
        fo.writeOutput(mouseClicksFileName, targetData.toString());
    }


    private void saveCenterPoints() {
        distancesList = new ArrayList<>();

        String fileName = "center_points.csv";
        StringBuilder sb = new StringBuilder();
        ArrayList<Point> pointsList = new ArrayList<>();

        sb.append("INDEX;CENTER_X;CENTER_Y \n");
        String tmp;

        for (Map.Entry<Integer, Point> entry : centersMap.entrySet()) {
            Integer key = entry.getKey();
            Point value = entry.getValue();
            pointsList.add(value);

            tmp = key + SEPARATOR + value.getX() + SEPARATOR + value.getY() + "\n";
            sb.append(tmp);

        }// end of for        

        // calculate distances:
        for (int i = 0; i < pointsList.size() - 1; i += 2) {
            Point first = pointsList.get(i);
            Point second = pointsList.get(i + 1);

            double distance = calc.distances(first, second);
            distancesList.add(distance);
        }// end of for

        saveDistances(distancesList);
        fo.writeOutput(fileName, sb.toString());
    }

    private void saveWidths() {
        //should receive targetWidthMap from client
        String fileName = "target_widths.csv";

        StringBuilder sb = new StringBuilder();
        String tmp;
        sb.append("INDEX;TARGET_WIDTH \n");

        for (Map.Entry<Integer, Integer> entry : targetWidthMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            tmp = key + SEPARATOR + value + "\n";
            sb.append(tmp);
        }

        fo.writeOutput(fileName, sb.toString());
    }

    private void saveErrors() {
        //should receive errorsMap from client
        String fileName = "errors.csv";

        StringBuilder sb = new StringBuilder();
        String tmp;
        sb.append("INDEX;X;Y \n");

        for (Map.Entry<Integer, Point> entry : errorsMap.entrySet()) {
            Integer key = entry.getKey();
            Point value = entry.getValue();

            tmp = key + SEPARATOR + value.getX() + SEPARATOR + value.getY() + "\n";
            sb.append(tmp);
        }

        fo.writeOutput(fileName, sb.toString());
    }

    private void saveDistances(ArrayList<Double> distancesList) {
        StringBuilder sb = new StringBuilder();
        String fileName = "distances.csv";

        sb.append("DISTANCE \n");

        for (Double distance : distancesList) {
            sb.append(df.format(distance));
            sb.append("\n");
        }
        fo.writeOutput(fileName, sb.toString());

    }

    private  void  saveScreenshots(){
        fo.saveScreenshots(counter, screenShotHTMLs);
    }

    private String symptomsToString(ArrayList<String> symptoms){
        StringBuilder result = new StringBuilder();
        for (int i=0; i< symptoms.size(); i++){
            result.append(symptoms.get(i));
            if (i<symptoms.size()-1){
                result.append(", ");
            }
        }
        return result.toString();
    }

    private void generateSummary() {
        StringBuilder sb = new StringBuilder();

        String header = "UserID;DisplayHours;Timestamp;Trials;AoS;AVG_AoT;Distances;Widths;Duration;Errors;Pointer_Movements;User_Rating;Eye_Tracking_Precision;Resolution;Symptoms\n";
        String timeStamp = fo.getTimeStamp();

        sb.append(header);

        double averageDistances = calc.getAverageDistances(distancesList);
        int calcGetArea = calc.getArea(screenWidth, screenHeight);

        String symptomsString = symptomsToString(symptoms);
        String resolution = Integer.toString(screenWidth) + " x " + Integer.toString(screenHeight);

        String line = userId + SEPARATOR + displayHours + SEPARATOR + timeStamp + SEPARATOR + counter + SEPARATOR
                + calcGetArea + SEPARATOR + df.format(averageAreaOfTarget)
                + SEPARATOR + df.format(averageDistances) + SEPARATOR + df.format(averageWidths) + SEPARATOR
                + durationTime + SEPARATOR + errorCounter + SEPARATOR + mouseMovementIndex
                + SEPARATOR + eyeStrainRate + SEPARATOR + eyeTrackingPrecision + SEPARATOR + resolution + SEPARATOR + symptomsString;

        sb.append(line);

        String fileName = "summary.csv";
        fo.writeOutput(fileName, sb.toString());
    }

}
