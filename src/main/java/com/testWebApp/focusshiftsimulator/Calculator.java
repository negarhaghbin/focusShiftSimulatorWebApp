package com.testWebApp.focusshiftsimulator;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Mohsen Parisay <mohsenparisay@gmail.com>
 * @version 1.0 <23.Dec.2020>
 */
class Calculator {

    double distances(Point p, Point q) {
        int x1 = p.x;
        int y1 = p.y;

        int x2 = q.x;
        int y2 = q.y;

        return calcDistance(x1, y1, x2, y2);
    }

    private double calcDistance(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        int valueX = (int) Math.pow(deltaX, 2);
        int valueY = (int) Math.pow(deltaY, 2);

        return Math.sqrt(valueX + valueY);
    }


    double getAverageDistances(ArrayList<Double> distances) {
        double sum = 0;

        for (double item : distances) {
            sum += item;
        }
        return sum / distances.size();
    }

    int getArea(int width, int height) {
        return width * height;
    }

}
