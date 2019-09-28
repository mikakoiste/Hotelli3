package com.example.hotelli;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Mika on 31.12.2017.
 */

public class GameBoard {

    ArrayList<Plot> game_board_plots = new ArrayList<Plot>();

    static final int prize = 3;
    static ArrayList<Hotel> hotels = new ArrayList<Hotel>();

    public GameBoard()
    {
        Hotel hotel1 = new Hotel("hotel1", 150, 500);
        hotels.add(hotel1);
        Hotel hotel2 = new Hotel("hotel2", 250, 1500);
        hotels.add(hotel2);
        Plot plot0 = new Plot();
        plot0.type = Plot.Type.NO_ACTION;
        plot0.index = 0;
        add_plot(plot0);
        Plot plot1 = new Plot();
        plot1.type = Plot.Type.BUY;
        plot1.hotels.add(hotel1);
        plot1.index = 1;
        add_plot(plot1);
        Plot plot2 = new Plot();
        plot2.type = Plot.Type.BUILD;
        plot2.hotels.add(hotel2);
        plot2.index = 2;
        add_plot(plot2);
        Plot plot3 = new Plot();
        plot3.type = Plot.Type.BUILD_FREE;
        plot3.index = 3;
        add_plot(plot3);
        Plot plot4 = new Plot();
        plot4.index = 4;
        plot4.type = Plot.Type.FREE_ENTRANCE;
        plot4.hotels.add(hotel1);
        plot4.hotels.add(hotel2);
        add_plot(plot4);
        Plot plot5 = new Plot();
        plot5.type = Plot.Type.NO_ACTION;
        plot5.index = 5;
        add_plot(plot5);
    }

    public String plot_info(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.type_str();
    }

    public ArrayList<Hotel> available_hotels(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.available_hotels();
    }

    public ArrayList<String> available_hotels_str(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.available_hotels_str();
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
