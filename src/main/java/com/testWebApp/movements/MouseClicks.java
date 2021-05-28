package com.testWebApp.movements;

import java.util.HashMap;

public class MouseClicks {
    private HashMap<Integer, Click> clicks;

    public HashMap<Integer, Click> getClicks() {
        return clicks;
    }

    public MouseClicks(HashMap<Integer, Click> mc) {
        this.clicks = mc;
    }
}
