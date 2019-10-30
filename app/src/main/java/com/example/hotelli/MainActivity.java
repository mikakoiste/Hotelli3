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
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout mRelativeLayout;
    Button buy_button;
    Button build_button;
    Button end_turn_button;
    TextView raha;
    TextView tieto;
    TextView player_tv;
    ImageView noppa_iv;
    final static int prize = 2000;
    GameBoard gameboard = new GameBoard();
    static ArrayList<Player> players = new ArrayList<>();
    private PopupWindow buyPopupWindow;
    Player current_player;
    boolean playerRolled = false;
    boolean already_bought = false;
    boolean cannot_build = false;
    boolean cannot_roll = false;
    //TextView events_tv;
    ImageView car;
    ImageView car2;
    ImageView boomerang_info;
    ImageView boomerang_buy;
    ImageView boomerang_build;
    ImageView fujiyama_info;
    ImageView fujiyama_buy;
    ImageView fujiyama_build;
    ImageView president_info;
    ImageView president_buy;
    ImageView president_build;
    ImageView royal_info;
    ImageView royal_buy;
    ImageView royal_build;
    ImageView waikiki_info;
    ImageView waikiki_buy;
    ImageView waikiki_build;
    ImageView letoile_info;
    ImageView letoile_buy;
    ImageView letoile_build;
    ImageView tajmahal_info;
    ImageView tajmahal_buy;
    ImageView tajmahal_build;
    ImageView safari_info;
    ImageView safari_buy;
    ImageView safari_build;

    ArrayList<String> clicks;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        buy_button = findViewById(R.id.buy_button);
        build_button = findViewById(R.id.build_button);
        raha = findViewById(R.id.raha);
        tieto = findViewById(R.id.tieto);
        player_tv = findViewById(R.id.player_tv);
        //events_tv = findViewById(R.id.eventsText);
        end_turn_button = findViewById(R.id.end_turn_button);
        mRelativeLayout = findViewById(R.id.relativeLayout);
        noppa_iv = findViewById(R.id.noppa_imageview);
        noppa_iv.bringToFront();
        buy_button.bringToFront();
        build_button.bringToFront();
        car = findViewById(R.id.car_imageview);
        car2 = findViewById(R.id.car2_imageview);
        boomerang_info = findViewById(R.id.boomerang_info);
        boomerang_buy = findViewById(R.id.boomerang_buy);
        boomerang_build = findViewById(R.id.boomerang_build);
        fujiyama_info = findViewById(R.id.fujiyama_info);
        fujiyama_buy = findViewById(R.id.fujiyama_buy);
        fujiyama_build = findViewById(R.id.fujiyama_build);
        president_info = findViewById(R.id.president_info);
        president_buy = findViewById(R.id.president_buy);
        president_build = findViewById(R.id.president_build);
        royal_info = findViewById(R.id.royal_info);
        royal_buy = findViewById(R.id.royal_buy);
        royal_build = findViewById(R.id.royal_build);
        waikiki_info = findViewById(R.id.waikiki_info);
        waikiki_buy = findViewById(R.id.waikiki_buy);
        waikiki_build = findViewById(R.id.waikiki_build);
        letoile_info = findViewById(R.id.letoile_info);
        letoile_buy = findViewById(R.id.letoile_buy);
        letoile_build = findViewById(R.id.letoile_build);
        tajmahal_info = findViewById(R.id.tajmahal_info);
        tajmahal_buy = findViewById(R.id.tajmahal_buy);
        tajmahal_build = findViewById(R.id.tajmahal_build);
        safari_info = findViewById(R.id.safari_info);
        safari_buy = findViewById(R.id.safari_buy);
        safari_build = findViewById(R.id.safari_build);
        /*
        mRelativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int mID = v.getId();
                int[] values = new int[2];
                v.getLocationOnScreen(values);
                Log.d("X & Y",values[0]+" "+values[1]);
            }
        });
        mRelativeLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float[] lastTouchDownXY = new float[2];
                // save the X,Y coordinates
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    lastTouchDownXY[0] = event.getX();
                    lastTouchDownXY[1] = event.getY();
                    String coords = String.format("%d: %d, %d", clicks.size(), Math.round(event.getX()), Math.round(event.getY()));
                    clicks.add(coords);
                }
                // let the touch event pass on to whoever needs it
                return false;
            }
        });
        */
        findViewById(R.id.noppa_imageview).setOnClickListener(this);
        findViewById(R.id.buy_button).setOnClickListener(this);
        findViewById(R.id.build_button).setOnClickListener(this);
        findViewById(R.id.end_turn_button).setOnClickListener(this);
        findViewById(R.id.boomerang_info).setOnClickListener(this);
        findViewById(R.id.boomerang_buy).setOnClickListener(this);
        findViewById(R.id.boomerang_build).setOnClickListener(this);
        findViewById(R.id.fujiyama_info).setOnClickListener(this);
        findViewById(R.id.fujiyama_buy).setOnClickListener(this);
        findViewById(R.id.fujiyama_build).setOnClickListener(this);
        findViewById(R.id.president_info).setOnClickListener(this);
        findViewById(R.id.president_buy).setOnClickListener(this);
        findViewById(R.id.president_build).setOnClickListener(this);
        findViewById(R.id.royal_info).setOnClickListener(this);
        findViewById(R.id.royal_buy).setOnClickListener(this);
        findViewById(R.id.royal_build).setOnClickListener(this);
        findViewById(R.id.waikiki_info).setOnClickListener(this);
        findViewById(R.id.waikiki_buy).setOnClickListener(this);
        findViewById(R.id.waikiki_build).setOnClickListener(this);
        findViewById(R.id.letoile_info).setOnClickListener(this);
        findViewById(R.id.letoile_buy).setOnClickListener(this);
        findViewById(R.id.letoile_build).setOnClickListener(this);
        findViewById(R.id.tajmahal_info).setOnClickListener(this);
        findViewById(R.id.tajmahal_buy).setOnClickListener(this);
        findViewById(R.id.tajmahal_build).setOnClickListener(this);
        findViewById(R.id.safari_info).setOnClickListener(this);
        findViewById(R.id.safari_buy).setOnClickListener(this);
        findViewById(R.id.safari_build).setOnClickListener(this);
        clicks = new ArrayList<>();
        addPlayers();
        addInfoIcons();
        addBuyAndBuildIcons();
        current_player = players.get(0);
        updateScreen();
    }

    private void addInfoIcons()
    {
        Iterator<Hotel> hotelIterator = GameBoard.hotels.iterator();
        while (hotelIterator.hasNext()) {
            Hotel _hotel = hotelIterator.next();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 50);
            int x = _hotel.info_coords.x;
            int y = _hotel.info_coords.y;
            layoutParams.leftMargin = x;
            layoutParams.topMargin = y;
            if (_hotel._name.equalsIgnoreCase("Boomerang"))
            {
                boomerang_info.setLayoutParams(layoutParams);
                boomerang_info.setVisibility(View.VISIBLE);
                _hotel.info = boomerang_info;
                _hotel.buy = boomerang_buy;
                _hotel.build = boomerang_build;
            }
            else if (_hotel._name.equalsIgnoreCase("Fujiyama"))
            {
                fujiyama_info.setLayoutParams(layoutParams);
                fujiyama_info.setVisibility(View.VISIBLE);
                _hotel.info = fujiyama_info;
                _hotel.buy = fujiyama_buy;
                _hotel.build = fujiyama_build;
            }
            else if (_hotel._name.equalsIgnoreCase("President"))
            {
                president_info.setLayoutParams(layoutParams);
                president_info.setVisibility(View.VISIBLE);
                _hotel.info = president_info;
                _hotel.buy = president_buy;
                _hotel.build = president_build;
            }
            else if (_hotel._name.equalsIgnoreCase("Royal"))
            {
                royal_info.setLayoutParams(layoutParams);
                royal_info.setVisibility(View.VISIBLE);
                _hotel.info = royal_info;
                _hotel.buy = royal_buy;
                _hotel.build = royal_build;
            }
            else if (_hotel._name.equalsIgnoreCase("L'etoile"))
            {
                letoile_info.setLayoutParams(layoutParams);
                letoile_info.setVisibility(View.VISIBLE);
                _hotel.info = letoile_info;
                _hotel.buy = letoile_buy;
                _hotel.build = letoile_build;
            }
            else if (_hotel._name.equalsIgnoreCase("Waikiki"))
            {
                waikiki_info.setLayoutParams(layoutParams);
                waikiki_info.setVisibility(View.VISIBLE);
                _hotel.info = waikiki_info;
                _hotel.buy = waikiki_buy;
                _hotel.build = waikiki_build;
            }
            else if (_hotel._name.equalsIgnoreCase("Taj Mahal"))
            {
                tajmahal_info.setLayoutParams(layoutParams);
                tajmahal_info.setVisibility(View.VISIBLE);
                _hotel.info = tajmahal_info;
                _hotel.buy = tajmahal_buy;
                _hotel.build = tajmahal_buy;
            }
            else if (_hotel._name.equalsIgnoreCase("Safari"))
            {
                safari_info.setLayoutParams(layoutParams);
                safari_info.setVisibility(View.VISIBLE);
                _hotel.info = safari_info;
                _hotel.buy = safari_buy;
                _hotel.build = safari_build;
            }
        }
    }

    private void gray_out(ImageView iv)
    {
        iv.setColorFilter(Color.argb(150,120,120,120));
    }

    private void enable_iv(ImageView iv)
    {
        iv.clearColorFilter();
    }

    private void addBuyAndBuildIcons()
    {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 50);
        RelativeLayout.LayoutParams infoLayoutParams;
        infoLayoutParams = (RelativeLayout.LayoutParams) boomerang_info.getLayoutParams();
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 50;
        layoutParams.topMargin = infoLayoutParams.topMargin - 40;
        boomerang_buy.setLayoutParams(layoutParams);
        boomerang_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 50;
        layoutParams.topMargin = infoLayoutParams.topMargin + 25;
        boomerang_build.setLayoutParams(layoutParams);
        boomerang_build.setVisibility(View.VISIBLE);

        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = 150;
        layoutParams.topMargin = 260;
        fujiyama_buy.setLayoutParams(layoutParams);
        fujiyama_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = 200;
        layoutParams.topMargin = 240;
        fujiyama_build.setLayoutParams(layoutParams);
        fujiyama_build.setVisibility(View.VISIBLE);

        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        infoLayoutParams = (RelativeLayout.LayoutParams) president_info.getLayoutParams();
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 25;
        layoutParams.topMargin = infoLayoutParams.topMargin + 50;
        president_buy.setLayoutParams(layoutParams);
        president_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 50;
        layoutParams.topMargin = infoLayoutParams.topMargin + 100;
        president_build.setLayoutParams(layoutParams);
        president_build.setVisibility(View.VISIBLE);

        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        infoLayoutParams = (RelativeLayout.LayoutParams) royal_info.getLayoutParams();
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 35;
        layoutParams.topMargin = infoLayoutParams.topMargin + 55;
        royal_buy.setLayoutParams(layoutParams);
        royal_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = infoLayoutParams.leftMargin;
        layoutParams.topMargin = infoLayoutParams.topMargin + 110;
        royal_build.setLayoutParams(layoutParams);
        royal_build.setVisibility(View.VISIBLE);

        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        infoLayoutParams = (RelativeLayout.LayoutParams) waikiki_info.getLayoutParams();
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 50;
        layoutParams.topMargin = infoLayoutParams.topMargin;
        waikiki_buy.setLayoutParams(layoutParams);
        waikiki_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 100;
        layoutParams.topMargin = infoLayoutParams.topMargin;
        waikiki_build.setLayoutParams(layoutParams);
        waikiki_build.setVisibility(View.VISIBLE);

        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        infoLayoutParams = (RelativeLayout.LayoutParams) tajmahal_info.getLayoutParams();
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 50;
        layoutParams.topMargin = infoLayoutParams.topMargin;
        tajmahal_buy.setLayoutParams(layoutParams);
        tajmahal_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 100;
        layoutParams.topMargin = infoLayoutParams.topMargin;
        tajmahal_build.setLayoutParams(layoutParams);
        tajmahal_build.setVisibility(View.VISIBLE);

        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        infoLayoutParams = (RelativeLayout.LayoutParams) letoile_info.getLayoutParams();
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 60;
        layoutParams.topMargin = infoLayoutParams.topMargin;
        letoile_buy.setLayoutParams(layoutParams);
        letoile_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = infoLayoutParams.leftMargin + 120;
        layoutParams.topMargin = infoLayoutParams.topMargin;
        letoile_build.setLayoutParams(layoutParams);
        letoile_build.setVisibility(View.VISIBLE);

        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        infoLayoutParams = (RelativeLayout.LayoutParams) safari_info.getLayoutParams();
        layoutParams.leftMargin = infoLayoutParams.leftMargin;
        layoutParams.topMargin = infoLayoutParams.topMargin + 50;
        safari_buy.setLayoutParams(layoutParams);
        safari_buy.setVisibility(View.VISIBLE);
        layoutParams = new RelativeLayout.LayoutParams(150, 50);
        layoutParams.leftMargin = infoLayoutParams.leftMargin;
        layoutParams.topMargin = infoLayoutParams.topMargin + 100;
        safari_build.setLayoutParams(layoutParams);
        safari_build.setVisibility(View.VISIBLE);
    }

    private void updateScreen(){
        player_tv.setText(current_player.name);
        raha.setText(String.valueOf(current_player.funds));
        printAvailableHotels();
        set_buttons();
        draw_car();
        //buy_button.setVisibility(View.GONE);
        //build_button.setVisibility(View.GONE);
    }

    public void onClick(View arg0) {
        int mID = arg0.getId();
        //Button btn = (Button)arg0;
        switch (mID) {
            case R.id.noppa_imageview:
                move();
                break;
            case R.id.buy_button:
                buy();
                break;
            case R.id.build_button:
                build();
                break;
            case R.id.end_turn_button:
                end_turn();
                break;
            case R.id.boomerang_buy:
                handle_buy("Boomerang");
                break;
            case R.id.fujiyama_buy:
                handle_buy("Fujiyama");
                break;
            case R.id.president_buy:
                handle_buy("President");
                break;
            case R.id.royal_buy:
                handle_buy("Royal");
                break;
            case R.id.waikiki_buy:
                handle_buy("Waikiki");
                break;
            case R.id.letoile_buy:
                handle_buy("L'etoile");
                break;
            case R.id.tajmahal_buy:
                handle_buy("Taj Mahal");
                break;
            case R.id.safari_buy:
                handle_buy("Safari");
                break;
            case R.id.boomerang_build:
                handle_build("Boomerang");
                break;
            case R.id.fujiyama_build:
                handle_build("Fujiyama");
                break;
            case R.id.president_build:
                handle_build("President");
                break;
            case R.id.royal_build:
                handle_build("Royal");
                break;
            case R.id.waikiki_build:
                handle_build("Waikiki");
                break;
            case R.id.letoile_build:
                handle_build("L'etoile");
                break;
            case R.id.tajmahal_build:
                handle_build("Taj Mahal");
                break;
            case R.id.safari_build:
                handle_build("Safari");
                break;
            default:
                return;  // Ignore and do not update screen
        }
        updateScreen();
    }

    private void end_turn()
    {
        int next_id = (current_player.player_id + 1) % players.size();
        current_player = players.get(next_id);
        playerRolled = false;
        already_bought = false;
        cannot_build = false;
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
            //build_button.setEnabled(false);
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
        Toast.makeText(getApplicationContext(), current_player.name + " BANKRUPTED!", Toast.LENGTH_LONG).show();
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
                    cannot_build = true;
                    updateScreen();
                    return;
            }
            hotel.build();
            if (gameboard.get_plot(current_player.plot).type == Plot.Type.BUILD_FREE)
            {
                cannot_build = true;  // Can only build once for free
            }
            current_player.funds = current_player.funds - price;
            updateScreen();
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

    private void set_dice_image(int luku)
    {
        switch (luku){
            case 1:
                noppa_iv.setImageResource(R.drawable.dice1);
                break;
            case 2:
                noppa_iv.setImageResource(R.drawable.dice2);
                break;
            case 3:
                noppa_iv.setImageResource(R.drawable.dice3);
                break;
            case 4:
                noppa_iv.setImageResource(R.drawable.dice4);
                break;
            case 5:
                noppa_iv.setImageResource(R.drawable.dice5);
                break;
            case 6:
                noppa_iv.setImageResource(R.drawable.dice6);
                break;
        }
    }

    private void draw_car()
    {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 50);
        int x = gameboard.get_plot(current_player.plot).car.x;
        int y = gameboard.get_plot(current_player.plot).car.y;
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        if (current_player.player_id == 0) {
            car.setLayoutParams(layoutParams);
            car.setVisibility(View.VISIBLE);
        }
        if (current_player.player_id == 1) {
            car2.setLayoutParams(layoutParams);
            car2.setVisibility(View.VISIBLE);
        }
    }

    private void move(){
        int luku = this.rollDice();
        set_dice_image(luku);
        //events_tv.setText(String.format("%s rolled %d\n", current_player.name, luku));
        advance(luku);
        if (luku != 6)
        {
            // Enable this for real game
            // cannot_roll = true;
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
        draw_car();
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
        raha.setText(String.valueOf(current_player.funds) + "€");
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
            if (gameboard.player_buildable_hotels(current_player.player_id).size() > 0 && !cannot_build && playerRolled) {
                build_button.setEnabled(true);
            }
            else
            {
                build_button.setEnabled(false);
            }
        }
        else
        {
            build_button.setEnabled(false);
        }

        set_buy_icons(_current_plot, plot);
        set_build_icons(_current_plot);

        if (!playerRolled) {
            cannot_roll = false;
            end_turn_button.setEnabled(false);
        }
        else {
            cannot_roll = true;
            end_turn_button.setEnabled(true);
        }
    }

    private void set_buy_icons(Plot _current_plot, int plot)
    {
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
        for (Hotel _hotel : GameBoard.hotels)
        {
            gray_out(_hotel.buy);
        }
        if (already_bought || !playerRolled)
        {
            return;  // Leave all dimmed
        }
        for (Hotel _hotel : _current_plot.hotels)
        {
            if (_current_plot.type == Plot.Type.BUY && _hotel.free)
            {
                enable_iv(_hotel.buy);
            }
        }
    }

    private void set_build_icons(Plot _current_plot)
    {
        for (Hotel _hotel : GameBoard.hotels)
        {
            gray_out(_hotel.build);
        }
        if (_current_plot.type != Plot.Type.BUILD && _current_plot.type != Plot.Type.BUILD_FREE)
        {
            return;
        }
        if (!playerRolled || cannot_build)
        {
            return;
        }
        ArrayList<Hotel> _hotels = gameboard.player_buildable_hotels(current_player.player_id);
        for (Hotel _hotel : _hotels)
        {
            enable_iv(_hotel.build);
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
