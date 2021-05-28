package com.testWebApp.focusshiftsimulator;

import com.testWebApp.movements.MouseClicks;
import com.testWebApp.movements.MouseMovement;
import com.testWebApp.movements.TargetMovement;
//import javafx.util.Pair;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author Mohsen Parisay <mohsenparisay@gmail.com>
 * @version 1.0 <24.Dec.2020>
 */
class InteractionManager {

    private static InteractionManager InteractionManagerInstance = null;

    private static final Logger LOG = Logger.getLogger(InteractionManager.class.getName());
    String userId;
    String displayHours;
    ArrayList<String> symptoms;

    private InteractionManager() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LOG.info(timestamp.toString());
    }

    public static InteractionManager getInstance()
    {
        if (InteractionManagerInstance == null)
            InteractionManagerInstance = new InteractionManager();

        return InteractionManagerInstance;
    }

    void startTest(double averageAreaOfTarget, double averageWidths, double durationTime, int errorCounter, String eyeStrainRate, long mouseMovementIndex, ArrayList<Pair> eyeTrackingData, ArrayList<String> screenShotHtmls, HashMap<Integer, Integer> targetWidthMap, HashMap<Integer, Point> centersMap, HashMap<Integer, Point> errorsMap, MouseMovement mv, TargetMovement tm, int counter, int eyeTrackingPrecision, int screenWidth, int screenHeight, MouseClicks mc) {
        GraphicManager gm = new GraphicManager();
        gm.init(userId, displayHours, averageAreaOfTarget, averageWidths, durationTime, errorCounter, eyeStrainRate, mouseMovementIndex, eyeTrackingData, screenShotHtmls, targetWidthMap, centersMap, errorsMap, mv, tm, counter, eyeTrackingPrecision, screenWidth, screenHeight, symptoms, mc);
        gm.finishTrial();
    }
    
}
