package com.testWebApp.movements;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author Mohsen Parisay <mohsenparisay@gmail.com>
 * @version 1.0 <31.Dec.2020>
 */
public class MouseMovement {

    private HashMap<Long, Point> movements;

    public HashMap<Long, Point> getMovements() {
        return movements;
    }

    public MouseMovement(HashMap<Long, Point> mv) {
        this.movements = mv;
    }

}
