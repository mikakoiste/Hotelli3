package com.example.hotelli;

import java.util.ArrayList;

/**
 * Created by Mika on 31.12.2017.
 */

public class Hotel {

    String _name = "";
    int _entrance_price = 0;
    int _rent[][] = {
            {100, 200, 300, 400, 500, 600},
            {200, 400, 600, 800, 1000, 1200}
    };
    int _build_prices[] = {1500, 800};
    int _land_price = 0;
    int buildings = 0;
    ArrayList<Integer> plots = new ArrayList<Integer>();
    ArrayList<Integer> entrances = new ArrayList<Integer>();
    int owner = 0;  // 0 = not owned

    public Hotel(String name, int entrance_price, int land_price)
    {
        _name = name;
        _entrance_price = entrance_price;
        _land_price = land_price;
    }

}
