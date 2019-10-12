package com.example.hotelli;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Mika on 31.12.2017.
 */

public class GameBoard {

    ArrayList<Plot> game_board_plots = new ArrayList<Plot>();

    static final int __prize = 3;
    int prize = __prize;
    static ArrayList<Hotel> hotels = new ArrayList<Hotel>();
    Hotel boomerang;
    Hotel fujiyama;
    Hotel president;
    Hotel royal;
    Hotel letoile;
    Hotel waikiki;


    public GameBoard()
    {
        create_hotels();
        Plot plot0 = new Plot(Plot.Type.NO_ACTION);
        Plot plot1 = new Plot(Plot.Type.BUY);
        Plot plot2 = new Plot(Plot.Type.BUILD);
        Plot plot3 = new Plot(Plot.Type.BUILD_FREE);
        Plot plot4 = new Plot(Plot.Type.FREE_ENTRANCE);
        Plot plot5 = new Plot(Plot.Type.NO_ACTION);

        plot1.hotels.add(boomerang);
        plot2.hotels.add(fujiyama);
        plot4.hotels.add(boomerang);
        plot4.hotels.add(fujiyama);

        add_plot(plot0);
        add_plot(plot1);
        add_plot(plot2);
        add_plot(plot3);
        add_plot(plot4);
        add_plot(plot5);
    }

    private void create_hotels()
    {
        boomerang = new Hotel("Boomerang", 150, 500);
        boomerang._rent = new int[] {100, 300, 500};
        boomerang._build_prices = new int[]{1500, 1000};
        hotels.add(boomerang);

        fujiyama = new Hotel("Fujiyama", 100, 1000);
        fujiyama._rent = new int[] {100, 200, 250, 350, 450};
        fujiyama._build_prices = new int[]{1000, 800, 700, 1500};
        hotels.add(fujiyama);

        president = new Hotel("President", 250, 3000);
        president._rent = new int[] {200, 300, 400, 500, 600, 1000};
        president._build_prices = new int[]{4500, 3000, 2500, 2500, 4000};
        hotels.add(president);

        royal = new Hotel("Royal", 200, 2000);
        royal._rent = new int[] {100, 200, 250, 350, 450, 650};
        royal._build_prices = new int[]{3000, 2000, 2000, 1500, 3000};
        hotels.add(royal);

        letoile = new Hotel("L'etoile", 200, 3000);
        letoile._rent = new int[] {200, 300, 300, 300, 400, 500, 600};
        letoile._build_prices = new int[]{2500, 2000, 2000, 2500, 2000, 3000};
        hotels.add(letoile);

        waikiki = new Hotel("Waikiki", 200, 2000);
        waikiki._rent = new int[] {200, 250, 300, 350, 450, 650, 900};
        waikiki._build_prices = new int[]{3000, 2000, 2000, 1500, 1500, 3000};
        hotels.add(waikiki);

    }

    public String plot_info(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        String info = String.format("Plot %d %s", _plot.index, _plot.type_str());
        return info;
    }

    public Plot get_plot(int plot)
    {
        return game_board_plots.get(plot);
    }

    public ArrayList<Hotel> available_hotels(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.available_hotels();
    }

    public ArrayList<Hotel> hotels(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.hotels();
    }

    public ArrayList<String> available_hotels_str(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.available_hotels_str();
    }

    public ArrayList<Hotel> player_hotels(int plot, int player)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.player_hotels(player, false);
    }

    public ArrayList<Hotel> player_buildable_hotels(int plot, int player)
    {
        ArrayList<Hotel> _hotels = new ArrayList<>();
        Iterator<Hotel> hotelIterator = hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            if (_hotel.owner != player || !_hotel.can_build)
            {
                continue;
            }
            _hotels.add(_hotel);
        }
        return _hotels;
    }

    public ArrayList<String> player_buildable_hotels_str(int player)
    {
        ArrayList<String> _hotels_str = new ArrayList<>();
        Iterator<Hotel> hotelIterator = hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            if (_hotel.owner != player || !_hotel.can_build)
            {
                continue;
            }
            _hotels_str.add(_hotel._name + ": " + _hotel.build_price());
        }
        return _hotels_str;
    }

    public ArrayList<String> hotels_str(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.hotels_str();
    }

    public int plots()
    {
        return game_board_plots.size();
    }

    public void add_plot(Plot plot){
        this.game_board_plots.add(plot);
    }

    public static Hotel get_hotel_by_name(String name) throws Exception
    {
        Iterator<Hotel> itr = hotels.iterator();
        while (itr.hasNext()){
            Hotel element = itr.next();
            if (element._name.equals(name))
                return element;
        }
        throw new Exception("Cannot find hotel " + name);
    }
}
