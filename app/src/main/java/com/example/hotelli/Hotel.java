package com.example.hotelli;

import java.util.ArrayList;

/**
 * Created by Mika on 31.12.2017.
 */

public class Hotel {

    String _name = "";
    int _entrance_price = 0;
    // int _rent[] = {100, 200, 250, 350, 500, 1000};  // Rent per night
    // int _build_prices[] = {1500, 800, 700, 700, 500, 1600};
    int[] _rent;
    int[] _build_prices;
    int _land_price;
    boolean free = true;
    int buildings = 0;
    boolean can_build = true;
    ArrayList<Integer> plots = new ArrayList<Integer>();
    ArrayList<Integer> entrances = new ArrayList<Integer>();
    int owner = -1;  // -1 = not owned

    public Hotel(String name, int entrance_price, int land_price)
    {
        _name = name;
        _entrance_price = entrance_price;
        _land_price = land_price;
    }

    public int build_price()
    {
        return _build_prices[buildings];
    }

    public void build()
    {
        buildings += 1;
        if (buildings >= _build_prices.length)
        {
            can_build = false;
        }
    }

    public int current_rent()
    {
        return _rent[buildings];
    }

}
