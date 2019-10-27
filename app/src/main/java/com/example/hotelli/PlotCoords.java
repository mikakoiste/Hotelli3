package com.example.hotelli;

public class PlotCoords {

    int carX = 0;
    int carY = 0;
    private static final int x_offset = 60;
    private static final int y_offset = 20;

    public PlotCoords(int x, int y)
    {
        carX = x - x_offset;
        carY = y - y_offset;
    }

}
