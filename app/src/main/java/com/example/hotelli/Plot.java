package com.example.hotelli;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Mika on 31.12.2017.
 */

public class Plot {

    static private int __plots;
    // List of hotels which are adjacent to this plot
    ArrayList<Hotel> hotels = new ArrayList<Hotel>();
    Boolean has_entrance = false;
    int index = 0;
    Type type = Type.NO_ACTION;

    public enum Type {
        BUY,
        BUILD,
        BUILD_FREE,
        FREE_ENTRANCE,
        NO_ACTION
    }

    public Plot(Type plot_type)
    {
        type = plot_type;
        index = __plots;
        __plots++;
    }

    public ArrayList<Hotel> available_hotels()
    {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Iterator<Hotel> hotelIterator = this.hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            if (_hotel.free)
            {
                hotelList.add(_hotel);
            }
        }
        return hotelList;
    }

    public ArrayList<Hotel> hotels()
    {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Iterator<Hotel> hotelIterator = this.hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            hotelList.add(_hotel);
        }
        return hotelList;
    }

    public ArrayList<Hotel> player_hotels(int player, boolean only_buildable)
    {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Iterator<Hotel> hotelIterator = this.hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            if (_hotel.owner == player)
            {
                if (only_buildable && !_hotel.can_build)
                {
                    continue;
                }
                hotelList.add(_hotel);
            }
        }
        return hotelList;
    }

    public String type_str()
    {
        if (this.type == Type.NO_ACTION)
            return "NO_ACTION";
        if (this.type == Type.BUY)
            return "BUY";
        if (this.type == Type.BUILD)
            return "BUILD";
        if (this.type == Type.BUILD_FREE)
            return "BUILD_FREE";
        if (this.type == Type.FREE_ENTRANCE)
            return "FREE_ENTRANCE";
        return "NOT SET";
    }

    public ArrayList<String> available_hotels_str()
    {
        ArrayList<String> hotelList = new ArrayList<>();
        Iterator<Hotel> hotelIterator = this.hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            if (_hotel.free)
            {
                hotelList.add(_hotel._name + ": " + _hotel._land_price);
            }
        }
        return hotelList;
    }

    public ArrayList<String> hotels_str()
    {
        ArrayList<String> hotelList = new ArrayList<>();
        Iterator<Hotel> hotelIterator = this.hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            String owner;
            if (_hotel.free)
                owner = "-";
            else
                owner = MainActivity.players.get(_hotel.owner).name;
            String info = String.format("%s owner: %s", _hotel._name, owner);
            hotelList.add(info);
        }
        return hotelList;
    }

}
