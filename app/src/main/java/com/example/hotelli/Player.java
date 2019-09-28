package com.example.hotelli;

import java.util.ArrayList;

/**
 * Created by Mika on 18.1.2018.
 */

public class Player {

    int player_id = 0;
    int funds = 0;

    public Player(String _name, int initial_funds)
    {
        String name = _name;
        int id = player_id;
        funds = initial_funds;
        player_id++;
        ArrayList<Hotel> owned_hotels = new ArrayList<>();
        int money = 0;
    }
}
