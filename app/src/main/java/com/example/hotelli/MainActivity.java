package com.example.hotelli;

/*
Puuttuu:
- Virkistyspaikkojen osto milloin vain
- Vuokran periminen

Bugeja:
- BUY nappi jää aktiiviseksi oston jälkeen
- Voi ostaa saman entrancen monta kertaa

 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private LinearLayout mLinearLayout;
    Button noppa_button;
    Button buy_button;
    Button build_button;
    Button end_turn_button;
    TextView ruutu;
    TextView raha;
    TextView tieto;
    TextView noppa_value;
    TextView player_tv;
    final static int prize = 2000;
    GameBoard gameboard = new GameBoard();
    static ArrayList<Player> players = new ArrayList<>();
    private PopupWindow buyPopupWindow;
    Player current_player;
    boolean playerRolled = false;
    boolean already_bought = false;
    TextView events_tv;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noppa_button = findViewById(R.id.noppa_button);
        noppa_value = findViewById(R.id.noppa_value);
        buy_button = findViewById(R.id.buy_button);
        build_button = findViewById(R.id.build_button);
        ruutu = findViewById(R.id.ruutu);
        raha = findViewById(R.id.raha);
        tieto = findViewById(R.id.tieto);
        player_tv = findViewById(R.id.player_tv);
        events_tv = findViewById(R.id.eventsText);
        end_turn_button = findViewById(R.id.end_turn_button);
        mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout);
        findViewById(R.id.noppa_button).setOnClickListener(this);
        findViewById(R.id.buy_button).setOnClickListener(this);
        findViewById(R.id.build_button).setOnClickListener(this);
        findViewById(R.id.end_turn_button).setOnClickListener(this);
        addPlayers();
        current_player = players.get(0);
        updateScreen();
    }

    private void updateScreen(){
        player_tv.setText(current_player.name);
        raha.setText(String.valueOf(current_player.funds));
        ruutu.setText("PLOT: " + String.valueOf(current_player.plot));
        // noppa_value.setText("-");
        printAvailableHotels();
        set_buttons();
    }

    public void onClick(View arg0) {
        Button btn = (Button)arg0;
        switch (btn.getId()) {
            case R.id.noppa_button:
                this.move();
                break;
            case R.id.buy_button:
                this.buy();
                break;
            case R.id.build_button:
                this.build();
                break;
            case R.id.end_turn_button:
                this.end_turn();
                break;
        }
        updateScreen();
    }

    private void end_turn()
    {
        int next_id = (current_player.player_id + 1) % players.size();
        current_player = players.get(next_id);
        playerRolled = false;
        already_bought = false;
    }

    private void buy()
    {
        final ArrayList<String> hotels = gameboard.available_hotels_str(current_player.plot);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog2;
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = inflater.inflate(R.layout.buy_layout, null);
        alertDialog.setView(convertView);
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Select Hotel to buy");
        final ListView lv = convertView.findViewById(R.id.hotels_to_buy);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, hotels);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        alertDialog.setView(convertView);
        alertDialog2 = alertDialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                handle_buy(hotels.get(position));
                alertDialog2.dismiss();
            }
        });

    }

    private void build()
    {
        final ArrayList<String> hotels = gameboard.player_buildable_hotels_str(current_player.player_id);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog2;
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = inflater.inflate(R.layout.build_layout, null);
        alertDialog.setView(convertView);
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Select Hotel to build");
        final ListView lv = convertView.findViewById(R.id.hotels_to_build);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, hotels);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        alertDialog.setView(convertView);
        alertDialog2 = alertDialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                handle_build(hotels.get(position));
                alertDialog2.dismiss();
            }
        });
    }

    private void buy_entrance(final boolean free_entrance)
    {
        final ArrayList<String> hotels = gameboard.player_buyable_entrances_str(current_player.player_id);
        if (hotels.size() == 0) {
            Toast.makeText(getApplicationContext(), "Don't have any hotels to buy entrances to", Toast.LENGTH_SHORT).show();
            return;
        }
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog2;
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = inflater.inflate(R.layout.build_layout, null);
        alertDialog.setView(convertView);
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Select Hotel and plot to buy entrance");
        ListView lv = convertView.findViewById(R.id.hotels_to_build);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hotels);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        alertDialog.setView(convertView);
        alertDialog2 = alertDialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                handle_buy_entrance(hotels.get(position));
                if (free_entrance)
                {
                    alertDialog2.dismiss();
                    return;
                }
                String removed_hotel_name = hotels.get(position).split(":")[0];
                Iterator<String> plotIterator = hotels.iterator();
                /*
                To be refactored. Now check if hotel name is included in list and remove if it is
                 */
                while (plotIterator.hasNext()) {
                    String _hotel = plotIterator.next();
                    if (_hotel.contains(removed_hotel_name)) {
                        plotIterator.remove();
                    }
                }
                adapter.notifyDataSetChanged();
                if (adapter.isEmpty())
                {
                    alertDialog2.dismiss();
                }
            }
        });
    }

    private void handle_buy(String _hotel_str)
    {
        String hotel_name = _hotel_str.split(":")[0];
        try {
            Hotel hotel = GameBoard.get_hotel_by_name(hotel_name);
            int price = hotel._land_price;
            hotel.owner = current_player.player_id;
            hotel.free = false;
            current_player.funds = current_player.funds - price;
            already_bought = true;
            build_button.setEnabled(false);
            Toast.makeText(getApplicationContext(),current_player.name + " bought " + hotel_name + " with " + price,Toast.LENGTH_SHORT).show();
            updateScreen();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Cannot find " + hotel_name,Toast.LENGTH_SHORT).show();
        }
    }

    private void handle_buy_entrance(String _hotel_str)
    {
        boolean free_entrance = false;
        Plot plot = gameboard.get_plot(current_player.plot);
        if (plot.type == Plot.Type.FREE_ENTRANCE)
            free_entrance = true;
        String hotel_name = _hotel_str.split(":")[0];
        String plot_str = _hotel_str.split(": ")[1];
        try {
            Hotel hotel = GameBoard.get_hotel_by_name(hotel_name);
            int price = hotel._entrance_price;
            int entrance_plot_index = Integer.valueOf(plot_str);
            Log.d(TAG, current_player.name + " buys entrance to " + hotel_name + " to plot " + plot_str);
            hotel.entrances.add(Integer.parseInt(plot_str));
            Plot entrance_plot = gameboard.get_plot(entrance_plot_index);
            entrance_plot.has_entrance = true;
            entrance_plot.entrance_hotel = hotel;
            if (free_entrance) {
                Toast.makeText(getApplicationContext(), current_player.name + " got free entrance to " + hotel_name + ": " + plot, Toast.LENGTH_SHORT).show();
                updateScreen();
            } else {
                current_player.funds = current_player.funds - hotel._entrance_price;
                Toast.makeText(getApplicationContext(), current_player.name + " bought entrance to " + hotel_name + ": " + plot + " with " + price, Toast.LENGTH_SHORT).show();
            }
            updateScreen();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot find " + hotel_name, Toast.LENGTH_SHORT).show();
        }
    }

    private void auction()
    {
        Toast.makeText(getApplicationContext(), current_player.name + " BANCRUPTED!", Toast.LENGTH_LONG).show();
    }

    private void handle_build(String _hotel_str)
    {
        String hotel_name = _hotel_str.split(":")[0];
        try {
            Hotel hotel = GameBoard.get_hotel_by_name(hotel_name);
            int dice = rollDice();
            int price = hotel.build_price();
            if (gameboard.get_plot(current_player.plot).type == Plot.Type.BUILD_FREE)
            {
                Toast.makeText(getApplicationContext(),"Build free to " + hotel_name,Toast.LENGTH_SHORT).show();
                price = 0;
                dice = -1;
            }
            switch (dice){
                case -1:  // Building free
                    break;
                case 1:  // Pay double price
                    price = price * 2;
                    Toast.makeText(getApplicationContext(),"Rolled 2x, double price to build to " + hotel_name,Toast.LENGTH_SHORT).show();
                    break;
                case 2:  // You get it free
                    price = 0;
                    Toast.makeText(getApplicationContext(),"Rolled H, build free to " + hotel_name,Toast.LENGTH_SHORT).show();
                    break;
                case 3:  // Build with normal price
                case 4:  // Build with normal price
                case 5:  // Build with normal price
                    Toast.makeText(getApplicationContext(),"Rolled GREEN, build with normal price to " + hotel_name,Toast.LENGTH_SHORT).show();
                    break;
                case 6:  // Not allowed to buy_button, do nothing
                    Toast.makeText(getApplicationContext(),"Rolled RED, can't build to " + hotel_name,Toast.LENGTH_SHORT).show();
                    return;
            }
            hotel.build();
            if (gameboard.get_plot(current_player.plot).type == Plot.Type.BUILD_FREE)
            {
                build_button.setEnabled(false);  // Can only build once for free
            }
            current_player.funds = current_player.funds - price;
            if (current_player.funds < 0)
            {
                auction();
            }

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Cannot find " + hotel_name,Toast.LENGTH_SHORT).show();
        }
    }


    private int rollDice(){
        return 1 + (int)(Math.random() * 6);
    }

    private void move(){
        int luku = this.rollDice();
        noppa_value.setText("Rolled " + String.valueOf(luku) + " ");
        events_tv.setText(String.format("%s rolled %d\n", current_player.name, luku));
        advance(luku);
        if (luku != 6)
        {
            // Enable this for real game
            // noppa_button.setEnabled(false);
        }
        playerRolled = true;
    }

    private void addPlayers(){
        Player player1 = new Player("Pelaaja1", 10000);
        Player player2 = new Player("Pelaaja2", 10000);
        players.add(player1);
        players.add(player2);
    }

    private void advance(int count){
        int _prev_plot = current_player.plot;
        current_player.plot = current_player.plot + count;
        while (player_in_plot(current_player.plot))
        {
            current_player.plot++;
        }
        if (current_player.plot > gameboard.plots() - 1)  // 1st index is 0
        {
            current_player.plot = current_player.plot % gameboard.plots();
        };
        ruutu.setText("PLOT: " + String.valueOf(current_player.plot));
        if (gameboard.get_plot(current_player.plot).type == Plot.Type.FREE_ENTRANCE)
        {
            buy_entrance(true);
        }
        if (_prev_plot <= gameboard.entrance && current_player.plot > gameboard.entrance)
        {
            buy_entrance(false);
        }
        if (_prev_plot <= gameboard.prize && current_player.plot > gameboard.prize)
        {
            get_prize();
        }
        if (gameboard.get_plot(current_player.plot).has_entrance)
        {
            handle_entrance_plot(gameboard.get_plot(current_player.plot));
        }
        raha.setText(String.valueOf(current_player.funds));
        printAvailableHotels();
        set_buttons();
    }

    private boolean player_in_plot(int plot)
    {
        for (Player player : players)
        {
            if (player.player_id == current_player.player_id){
                continue;
            }
            if (player.plot == current_player.plot)
            {
                Log.d(TAG, player.name + " already in plot " + plot);
                return true;
            }
        }
        return false;
    }

    private void handle_entrance_plot(Plot plot)
    {
        if (plot.entrance_hotel.owner == current_player.player_id)
        {
            Log.d(TAG, current_player.name + " owns " + plot.entrance_hotel._name);
            return;  // Own hotel
        }
        int nights = rollDice();
        int rent = plot.entrance_hotel.current_rent() * nights;
        String str = String.format("%s staying %d nights in %s, rent %d", current_player.name, nights, plot.entrance_hotel._name, rent);
        Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();
        current_player.funds = current_player.funds - rent;
        if (current_player.funds < 0)
        {
            auction();
        }
    }
    private void set_buttons()
    {
        int plot = current_player.plot;
        Plot _current_plot = gameboard.get_plot(plot);
        if (_current_plot.type == Plot.Type.BUILD || _current_plot.type == Plot.Type.BUILD_FREE)
        {
            if (gameboard.player_buildable_hotels(plot, current_player.player_id).size() > 0) {
                build_button.setEnabled(true);
            }
        }
        else
        {
            build_button.setEnabled(false);
        }
        if (_current_plot.type == Plot.Type.BUY) {
            // If there are no hotels_in_plot to buy or have already bought, disable buy
            if (gameboard.available_hotels(plot).size() == 0 || already_bought) {
                buy_button.setEnabled(false);
            }
            else if (playerRolled)  // Player must have rolled dice before buying
            {
                buy_button.setEnabled(true);
            }
        }
        else
        {
            buy_button.setEnabled(false);
        }
        if (!playerRolled) {
            noppa_button.setEnabled(true);
            end_turn_button.setEnabled(false);
        }
        else {
            noppa_button.setEnabled(false);
            end_turn_button.setEnabled(true);
        }
    }

    private void printAvailableHotels()
    {
        ArrayList<String> hotels_str = gameboard.hotels_str(current_player.plot);
        Iterator<String> hotelStrIterator = hotels_str.iterator();
        StringBuilder str = new StringBuilder();
        while (hotelStrIterator.hasNext()) {
            String _hotel = hotelStrIterator.next();
            str.append(_hotel);
            str.append("\n");
        }
        tieto.setText("PLOT INFO:");
        tieto.append(gameboard.plot_info(current_player.plot));
        tieto.append("\n");
        tieto.append(str.toString());

        ArrayList<Hotel> _hotels = gameboard.hotels_all();
        Iterator<Hotel> hotelIterator = _hotels.iterator();
        str = new StringBuilder();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            str.append(_hotel.info_str());
        }
        tieto.append("\nHOTEL INFO:\n");
        tieto.append("NAME\t\tRENT\tBUILDINGS\tENTRANCES\n");
        tieto.append(str);
    }

    private void get_prize()
    {
        current_player.funds += prize;
    }

}
