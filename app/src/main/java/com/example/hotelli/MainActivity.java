package com.example.hotelli;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
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
    TextView events_tv;

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
        set_buttons();
    }

    public void updateScreen(){
        player_tv.setText(current_player.name);
        raha.setText(String.valueOf(current_player.funds));
        ruutu.setText("PLOT: " + String.valueOf(current_player.plot));
        noppa_value.setText("-");
        if (!playerRolled)
            noppa_button.setEnabled(true);
        else
            noppa_button.setEnabled(false);
        if (!playerRolled)
            end_turn_button.setEnabled(false);
        else
            end_turn_button.setEnabled(true);

    }
    public void onClick(View arg0) {
        Button btn = (Button)arg0;
        switch (btn.getId()) {
            case R.id.noppa_button:
                this.move();
                playerRolled = true;
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

        ArrayList<Hotel> hotels = gameboard.hotels(current_player.plot);
        if (hotels.size() == 0)
        {
            buy_button.setEnabled(false);
        }
        else
        {
            buy_button.setEnabled(true);
        }
    }

    void end_turn()
    {
        int next_id = (current_player.player_id + 1) % players.size();
        current_player = players.get(next_id);
        playerRolled = false;
        set_buttons();
        updateScreen();
    }
    void buy()
    {
        final ArrayList<String> hotels = gameboard.available_hotels_str(current_player.plot);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final AlertDialog OptionDialog = alertDialog.create();
        View convertView = inflater.inflate(R.layout.buy_layout, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Select Hotel to buy");
        ListView lv = convertView.findViewById(R.id.hotels_to_buy);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, hotels);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                handle_buy(hotels.get(position));
            }
        });
        alertDialog.show();
    }

    void build()
    {
        final ArrayList<String> hotels = gameboard.player_buildable_hotels_str(current_player.player_id);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final AlertDialog OptionDialog = alertDialog.create();
        View convertView = inflater.inflate(R.layout.build_layout, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Select Hotel to build");
        ListView lv = convertView.findViewById(R.id.hotels_to_build);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, hotels);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                handle_build(hotels.get(position));
            }
        });
        alertDialog.show();
    }

    void handle_buy(String _hotel_str)
    {
        String hotel_name = _hotel_str.split(":")[0];
        try {
            Hotel hotel = GameBoard.get_hotel_by_name(hotel_name);
            int price = hotel._land_price;
            hotel.owner = current_player.player_id;
            hotel.free = false;
            current_player.funds = current_player.funds - price;
            Toast.makeText(getApplicationContext(),current_player.name + " bought " + hotel_name + " with " + price,Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Cannot find " + hotel_name,Toast.LENGTH_SHORT).show();
        }
    }

    void auction()
    {

    }

    void handle_build(String _hotel_str)
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
                    Toast.makeText(getApplicationContext(),"Rolled GREEN, build free to " + hotel_name,Toast.LENGTH_SHORT).show();
                    break;
                case 3:  // Build with normal price
                case 4:  // Build with normal price
                case 5:  // Build with normal price
                    break;
                case 6:  // Not allowed to buy_button, do nothing
                    Toast.makeText(getApplicationContext(),"Rolled RED, can't build to " + hotel_name,Toast.LENGTH_SHORT).show();
                    return;
            }
            hotel.build();
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


    int rollDice(){
        return 1 + (int)(Math.random() * 6);
    }

    void move(){
        int luku = this.rollDice();
        noppa_value.setText(String.valueOf(luku));
        events_tv.setText(String.format("%s rolled %d\n", current_player.name, luku));
        advance(luku);
        if (luku != 6)
        {
            // Enable this for real game
            // noppa_button.setEnabled(false);
        }
        end_turn_button.setEnabled(true);
    }

    public void addPlayers(){
        Player player1 = new Player("Pelaaja1", 10000);
        Player player2 = new Player("Pelaaja2", 10000);
        players.add(player1);
        players.add(player2);
    }

    public void advance(int count){
        int _prev_plot = current_player.plot;
        current_player.plot = current_player.plot + count;
        if (current_player.plot > gameboard.plots() - 1)  // 1st index is 0
        {
            current_player.plot = current_player.plot % gameboard.plots();
        };
        ruutu.setText("PLOT: " + String.valueOf(current_player.plot));
        if (_prev_plot <= gameboard.prize && current_player.plot > gameboard.prize)
        {
            this.get_prize();
        }
        raha.setText(String.valueOf(current_player.funds));
        printAvailableHotels();
        set_buttons();
    }

    public void set_buttons()
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
        if (gameboard.available_hotels(plot).size() == 0)
        {
            buy_button.setEnabled(false);
        }
    }

    public void printAvailableHotels()
    {
        ArrayList<String> hotels = gameboard.hotels_str(current_player.plot);
        ArrayList<String> available_hotels = gameboard.available_hotels_str(current_player.plot);
        if (available_hotels.size() == 0)
            buy_button.setEnabled(false);
        else
            buy_button.setEnabled(true);
        Iterator<String> hotelIterator = hotels.iterator();
        StringBuilder str = new StringBuilder();
        while (hotelIterator.hasNext()) {
            String _hotel = hotelIterator.next();
            str.append(_hotel);
            str.append("\n");
        }
        tieto.setText(gameboard.plot_info(current_player.plot));
        tieto.append("\n");
        tieto.append(str.toString());
    }

    void get_prize()
    {
        current_player.funds += prize;
    }

}
