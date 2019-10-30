package com.example.hotelli;

public class PlotCoords {

    int x = 0;
    int y = 0;
    private static final int x_offset = 60;
    private static final int y_offset = 20;

    public PlotCoords(int _x, int _y)
    {
        x = _x - x_offset;
        y = _y - y_offset;
    }

}
