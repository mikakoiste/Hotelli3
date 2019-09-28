package com.example.hotelli;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private LinearLayout mLinearLayout;
    Button noppa;
    Button buy;
    Button build;
    TextView ruutu;
    TextView raha;
    TextView tieto;
    TextView noppa_value;
    int current_plot = 1;
    int money = 10000;
    final static int prize = 2000;
    GameBoard gameboard = new GameBoard();
    ArrayList<Player> players = new ArrayList<>();
    private PopupWindow buyPopupWindow;
    Player current_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noppa = findViewById(R.id.noppa_button);
        noppa_value = findViewById(R.id.noppa_value);
        buy = findViewById(R.id.buy_button);
        build = findViewById(R.id.build_button);
        ruutu = findViewById(R.id.ruutu);
        raha = findViewById(R.id.raha);
        tieto = findViewById(R.id.tieto);
        mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout);
        findViewById(R.id.noppa_button).setOnClickListener(this);
        findViewById(R.id.buy_button).setOnClickListener(this);
        findViewById(R.id.build_button).setOnClickListener(this);
        ruutu.setText(String.valueOf(current_plot));
        raha.setText(String.valueOf(money));
        ruutu.setText("PLOT: " + String.valueOf(current_plot));
        addPlayers();
        current_player = players.get(0);
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
        }

        ArrayList<Hotel> hotels = gameboard.available_hotels(current_plot);
        if (hotels.size() == 0)
        {
            buy.setEnabled(false);
        }
        else
        {
            buy.setEnabled(true);
        }
    }

    void buy()
    {
        final ArrayList<String> hotels = gameboard.available_hotels_str(current_plot);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
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

    void handle_buy(String hotel_name)
    {
        try {
            Hotel hotel = GameBoard.get_hotel_by_name(hotel_name);
            int dice = rollDice();
            int price = hotel._land_price;
            switch (dice){
                case 1:  // Pay double price
                    price = price * 2;
                    break;
                case 2:  // You get it free
                    price = 0;
                    break;
                case 3:  // Buy with normal price
                case 4:  // Buy with normal price
                case 5:  // Buy with normal price
                    break;
                case 6:  // Not allowed to buy, do nothing
                    return;
            }
            hotel.owner = current_player.player_id;
            current_player.funds = current_player.funds - price;

        }
        catch (Exception e) {
        }
    }

    void build() {
    }

    int rollDice(){
        return 1 + (int)(Math.random() * 6);
    }

    void move(){
        int luku = this.rollDice();
        noppa_value.setText(String.valueOf(luku));
        advance(luku);
    }

    public void addPlayers(){
        Player player1 = new Player("Pelaaja1", 10000);
        Player player2 = new Player("Pelaaja2", 10000);
        players.add(player1);
        players.add(player2);
    }

    public void advance(int count){
        int _prev_plot = current_plot;
        current_plot = current_plot + count;
        if (current_plot > gameboard.plots() - 1)  // 1st index is 0
        {
            current_plot = current_plot % gameboard.plots();
        };
        ruutu.setText("PLOT: " + String.valueOf(current_plot));
        if (_prev_plot < gameboard.prize && current_plot > gameboard.prize)
        {
            this.get_prize();
        }
        raha.setText(String.valueOf(money));
        printAvailableHotels();
    }

    public void printAvailableHotels()
    {
        ArrayList<Hotel> hotels = gameboard.available_hotels(current_plot);
        Iterator<Hotel> hotelIterator = hotels.iterator();
        String hotelsStr = "";
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            hotelsStr += _hotel._name + "\n";
        }
        tieto.setText(gameboard.plot_info(current_plot));
        tieto.append("\n");
        tieto.append(hotelsStr);
    }

    void get_prize()
    {
        money += prize;
    }

}
