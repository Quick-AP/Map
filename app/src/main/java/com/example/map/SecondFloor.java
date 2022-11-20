package com.example.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map.databinding.FloorSecondBinding;
import com.example.map.utils.TableStateThread;
import com.google.android.material.snackbar.Snackbar;

public class SecondFloor extends AppCompatActivity implements View.OnClickListener {

    private FloorSecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FloorSecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView stair_up = (ImageView) findViewById(R.id.stair_down);
        stair_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FirstFloor.class);
                startActivity(intent);
            }
        });

        ImageView seat_11 = (ImageView) findViewById(R.id.seat_inside_11);
        ImageView seat_12 = (ImageView) findViewById(R.id.seat_inside_12);
        ImageView seat_13 = (ImageView) findViewById(R.id.seat_inside_13);
        ImageView seat_14 = (ImageView) findViewById(R.id.seat_inside_14);
        ImageView seat_15 = (ImageView) findViewById(R.id.seat_inside_15);
        ImageView seat_16 = (ImageView) findViewById(R.id.seat_outside_16);
        ImageView seat_17 = (ImageView) findViewById(R.id.seat_outside_17);
        ImageView seat_18 = (ImageView) findViewById(R.id.seat_outside_18);
        ImageView seat_19 = (ImageView) findViewById(R.id.seat_outside_19);

        ImageView[] seats = new ImageView[9];
        seats[0] = seat_11;
        seats[1] = seat_12;
        seats[2] = seat_13;
        seats[3] = seat_14;
        seats[4] = seat_15;
        seats[5] = seat_16;
        seats[6] = seat_17;
        seats[7] = seat_18;
        seats[8] = seat_19;

        // Set table states before start up
        for (ImageView seat : seats){
            tableStateChecker(seat);
        }
    }

    public void tableStateChecker(ImageView view){
        String[] table_info = getResources().getResourceName(view.getId()).split("_");
        int table_id = Integer.parseInt(table_info[2]);

        TableStateThread table_state = new TableStateThread();
        table_state.setTableID(table_id);

        Thread thread = new Thread(table_state);
        thread.start();
        try{
            thread.join();
        }catch (Exception ignore){
            System.out.println("Ignore a mistake");
        }
        boolean res = table_state.getRes();
        if (res) {
            view.setOnClickListener(this);
            return;
        } else {
            if (table_info[1].equals("outside"))
                view.setImageResource(R.drawable.seat_outside_selected);
            else
                view.setImageResource(R.drawable.seat_inside_selected);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(findViewById(R.id.SecondFloorContent),
                            "This " + table_info[1] + " table has already been booked" + table_info[2], Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View view){
        String[] item_id = getResources().getResourceName(view.getId()).split("_");
        int table_id = Integer.parseInt(item_id[2]);
        Snackbar.make(findViewById(R.id.SecondFloorContent),
                "You have choosed " + item_id[1] + " table, number:" + item_id[2], Snackbar.LENGTH_SHORT).show();
        ImageView v = (ImageView) view;
        if(item_id[1].equals("outside")){
            v.setImageResource(R.drawable.seat_outside_selected);
        } else {
            v.setImageResource(R.drawable.seat_inside_selected);
        }
        Intent menu = new Intent(this, MainActivity.class); // Replace MainActivity.class with menu.class
        Bundle bundle = new Bundle();
        bundle.putInt("tableId", table_id);
        menu.putExtras(bundle);
        startActivity(menu);
    }
}