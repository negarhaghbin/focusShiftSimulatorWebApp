package com.testWebApp.movements;

import java.awt.*;

public class Click {
    private Point point;
    private String status;

    public Point getPoint(){
        return this.point;
    }

    public String getStatus(){
        return  this.status;
    }

    public Click(Point point, String status){
        this.point = point;
        this.status = status;
    }
}
