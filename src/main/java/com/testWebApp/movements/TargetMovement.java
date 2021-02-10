package com.testWebApp.movements;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author Mohsen Parisay <mohsenparisay@gmail.com>
 * @version 1.0 <31.Dec.2020>
 */
public class TargetMovement {

    private HashMap<Integer, Point> movements;

    public HashMap<Integer, Point> getMovements() {
        return movements;
    }

    public TargetMovement(HashMap<Integer, Point> tm) {
        this.movements = tm;
    }

}
