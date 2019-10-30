package com.example.hotelli;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Mika on 31.12.2017.
 */

public class GameBoard {

    ArrayList<Plot> game_board_plots = new ArrayList<Plot>();

    static private final int __prize = 7;
    static private final int __entrance = 26;
    static private final int __plots = 31;  // from 0 -> 30
    int prize = __prize;
    int entrance = __entrance;
    static ArrayList<Hotel> hotels = new ArrayList<Hotel>();
    Hotel boomerang;
    Hotel fujiyama;
    Hotel president;
    Hotel royal;
    Hotel letoile;
    Hotel waikiki;
    Hotel tajmahal;
    Hotel safari;

    ArrayList<Plot.Type> __plot_type = new ArrayList<>();
    ArrayList<PlotCoords> __plot_coords = new ArrayList<>();

    public GameBoard()
    {
        create_hotels();
        create_plots();
    }

    private void add_plot_types()
    {
        __plot_type.add(Plot.Type.NO_ACTION);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.FREE_ENTRANCE);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD_FREE);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.FREE_ENTRANCE);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.BUILD_FREE);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUILD);
        __plot_type.add(Plot.Type.BUY);
        __plot_type.add(Plot.Type.FREE_ENTRANCE);
        __plot_type.add(Plot.Type.BUILD);
    }

    private void add_car_coordinates(){
        __plot_coords.add(new PlotCoords(132, 454));
        __plot_coords.add(new PlotCoords(92, 355));
        __plot_coords.add(new PlotCoords(111,272));
        __plot_coords.add(new PlotCoords(167, 215));
        __plot_coords.add(new PlotCoords(255, 173));
        __plot_coords.add(new PlotCoords(339, 174));
        __plot_coords.add(new PlotCoords(410, 206));
        __plot_coords.add(new PlotCoords(506, 209));
        __plot_coords.add(new PlotCoords(587, 180));
        __plot_coords.add(new PlotCoords(660, 201));
        __plot_coords.add(new PlotCoords(723, 253));
        __plot_coords.add(new PlotCoords(809, 297));
        __plot_coords.add(new PlotCoords(896, 323));
        __plot_coords.add(new PlotCoords(984, 330));
        __plot_coords.add(new PlotCoords(1066, 374));
        __plot_coords.add(new PlotCoords(1097, 435));
        __plot_coords.add(new PlotCoords(1090, 521));
        __plot_coords.add(new PlotCoords(1031, 587));
        __plot_coords.add(new PlotCoords(945, 606));
        __plot_coords.add(new PlotCoords(860, 592));
        __plot_coords.add(new PlotCoords(765, 564));
        __plot_coords.add(new PlotCoords(686, 540));
        __plot_coords.add(new PlotCoords(601, 521));
        __plot_coords.add(new PlotCoords(526, 568));
        __plot_coords.add(new PlotCoords(519, 648));
        __plot_coords.add(new PlotCoords(485, 732));
        __plot_coords.add(new PlotCoords(370, 750));
        __plot_coords.add(new PlotCoords(316, 698));
        __plot_coords.add(new PlotCoords(298, 615));
        __plot_coords.add(new PlotCoords(293, 533));
        __plot_coords.add(new PlotCoords(241, 473));

        Iterator<Plot> plotIterator = game_board_plots.iterator();
        Iterator<PlotCoords> carIterator = __plot_coords.iterator();
        while (plotIterator.hasNext()) {
            Plot _plot = plotIterator.next();
            _plot.car = carIterator.next();
        }
    }

    private void create_plots()
    {
        add_plot_types();
        for (int i = 0; i < __plots; i++)
        {
            Plot _plot = new Plot(__plot_type.get(i));
            Iterator<Hotel> hotelIterator = hotels.iterator();
            while (hotelIterator.hasNext()) {
                Hotel _hotel = hotelIterator.next();
                for (int j = 0;j < _hotel.plots.length; j++)
                {
                    if (_hotel.plots[j] == _plot.index)
                    {
                        _plot.hotels.add(_hotel);
                    }
                }
            }
            add_plot(_plot);
        }
        add_car_coordinates();
    }

    private void create_hotels()
    {
        boomerang = new Hotel("Boomerang", 150, 500);
        boomerang._rent = new int[] {100, 300, 500};
        boomerang._build_prices = new int[]{1500, 1000};
        boomerang.plots = new  int[] {2, 3, 4, 5};
        boomerang.info_coords = new PlotCoords(254, 75);
        boomerang.buy_coords = new PlotCoords(354, 75);
        boomerang.build_coords = new PlotCoords(354, 45);
        hotels.add(boomerang);

        fujiyama = new Hotel("Fujiyama", 100, 1000);
        fujiyama._rent = new int[] {100, 200, 250, 350, 450};
        fujiyama._build_prices = new int[]{1000, 800, 700, 1500};
        fujiyama.plots = new  int[] {1, 2, 3, 4, 5, 6};
        fujiyama.info_coords = new PlotCoords(175, 340);
        hotels.add(fujiyama);

        president = new Hotel("President", 250, 3000);
        president._rent = new int[] {200, 300, 400, 500, 600, 1000};
        president._build_prices = new int[]{4500, 3000, 2500, 2500, 4000};
        president.plots = new  int[] {9, 10, 11, 12, 13, 14, 15};
        president.info_coords = new PlotCoords(781, 99);
        hotels.add(president);

        royal = new Hotel("Royal", 200, 2000);
        royal._rent = new int[] {100, 200, 250, 350, 450, 650};
        royal._build_prices = new int[]{3000, 2000, 2000, 1500, 3000};
        royal.plots = new  int[] {11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        royal.info_coords = new PlotCoords(977, 419);
        hotels.add(royal);

        letoile = new Hotel("L'etoile", 200, 3000);
        letoile._rent = new int[] {200, 300, 300, 300, 400, 500, 600};
        letoile._build_prices = new int[]{2500, 2000, 2000, 2500, 2000, 3000};
        letoile.plots = new  int[] {8, 9, 10, 21, 22, 23, 24, 28, 29};
        letoile.info_coords = new PlotCoords(360, 515);
        hotels.add(letoile);

        waikiki = new Hotel("Waikiki", 200, 2000);
        waikiki._rent = new int[] {200, 250, 300, 350, 450, 650, 900};
        waikiki._build_prices = new int[]{3000, 2000, 2000, 1500, 1500, 3000};
        waikiki.plots = new  int[] {16, 17, 18, 19, 20};
        waikiki.info_coords = new PlotCoords(820, 670);
        hotels.add(waikiki);

        tajmahal = new Hotel("Taj Mahal", 100, 1000);
        tajmahal._rent = new int[] {200, 250, 300, 350, 450};
        tajmahal._build_prices = new int[]{1000, 1500, 2000, 1500};
        tajmahal.plots = new  int[] {21, 22, 23, 24, 25};
        tajmahal.info_coords = new PlotCoords(595, 625);
        hotels.add(tajmahal);

        safari = new Hotel("Safari", 150, 1500);
        safari._rent = new int[] {200, 250, 300, 350, 450};
        safari._build_prices = new int[]{2000, 1000, 1200, 1500};
        safari.plots = new  int[] {26, 27, 28, 29, 30};
        safari.info_coords = new PlotCoords(200, 624);
        hotels.add(safari);
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

    public ArrayList<Hotel> hotels_in_plot(int plot)
    {
        Plot _plot = game_board_plots.get(plot);
        return _plot.hotels();
    }

    public ArrayList<Hotel> hotels_all()
    {
        return hotels;
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

    public ArrayList<Hotel> player_buildable_hotels(int player)
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

    public ArrayList<String> player_buyable_entrances_str(int player)
    {
        ArrayList<String> _entrances_str = new ArrayList<>();
        Iterator<Plot> plotIterator = game_board_plots.iterator();
        while (plotIterator.hasNext()) {
            Plot _plot = plotIterator.next();
            if (_plot.hotels.size() == 0)
            {
                continue;  // No hotels_in_plot
            }
            if (_plot.has_entrance)
            {
                continue;
            }
            Iterator<Hotel> hotelIterator = _plot.hotels.iterator();
            while (hotelIterator.hasNext()) {
                Hotel _hotel = hotelIterator.next();
                if (_hotel.owner == player && _hotel.buildings > 0) {
                    _entrances_str.add(_hotel._name + ": " + _plot.index);
                }
            }
        }
        return _entrances_str;
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
