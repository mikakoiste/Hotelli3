package com.example.hotelli;

import java.util.ArrayList;

/**
 * Created by Mika on 18.1.2018.
 */

public class Player {

    int player_id = 0;
    static private int players = 0;
    int funds;
    int plot = 0;
    String name;
    ArrayList<Hotel> owned_hotels;

    public Player(String _name, int initial_funds)
    {
        name = _name;
        funds = initial_funds;
        player_id = players;
        players++;
        owned_hotels = new ArrayList<>();
    }
}
