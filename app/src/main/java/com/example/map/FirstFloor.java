package com.example.map;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.map.databinding.FloorFirstBinding;
import com.example.map.utils.TableStateThread;
import com.google.android.material.snackbar.Snackbar;

public class FirstFloor extends AppCompatActivity implements View.OnClickListener {

    private FloorFirstBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FloorFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView stair_up = (ImageView) findViewById(R.id.stair_up);
        stair_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SecondFloor.class);
                startActivity(intent);
            }
        });

        ImageView seat_0 = (ImageView) findViewById(R.id.seat_inside_0);
        ImageView seat_1 = (ImageView) findViewById(R.id.seat_inside_1);
        ImageView seat_2 = (ImageView) findViewById(R.id.seat_inside_2);
        ImageView seat_3 = (ImageView) findViewById(R.id.seat_outside_3);
        ImageView seat_4 = (ImageView) findViewById(R.id.seat_outside_4);
        ImageView seat_5 = (ImageView) findViewById(R.id.seat_outside_5);
        ImageView seat_6 = (ImageView) findViewById(R.id.seat_outside_6);
        ImageView seat_7 = (ImageView) findViewById(R.id.seat_outside_7);
        ImageView seat_8 = (ImageView) findViewById(R.id.seat_outside_8);
        ImageView seat_9 = (ImageView) findViewById(R.id.seat_outside_9);
        ImageView seat_10 = (ImageView) findViewById(R.id.seat_outside_10);

        ImageView[] seats = new ImageView[11];
        seats[0] = seat_0;
        seats[1] = seat_1;
        seats[2] = seat_2;
        seats[3] = seat_3;
        seats[4] = seat_4;
        seats[5] = seat_5;
        seats[6] = seat_6;
        seats[7] = seat_7;
        seats[8] = seat_8;
        seats[9] = seat_9;
        seats[10] = seat_10;

        for (ImageView seat : seats){
            tableStateChecker(seat);
        }

//        Expensive but automatic method
//        int id = getResources().getIdentifier("R.id.imageView" + i, "id", null);
//        seats[i] = (ImageView) findViewById(id);

//        seat_0.setOnClickListener(this);
//        seat_1.setOnClickListener(this);
//        seat_2.setOnClickListener(this);
//        seat_3.setOnClickListener(this);
//        seat_4.setOnClickListener(this);
//        seat_5.setOnClickListener(this);
//        seat_6.setOnClickListener(this);
//        seat_7.setOnClickListener(this);
//        seat_8.setOnClickListener(this);
//        seat_9.setOnClickListener(this);
//        seat_10.setOnClickListener(this);
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
                    Snackbar.make(findViewById(R.id.FirstFloorContent),
                            "This " + table_info[1] + " table has already been booked" + table_info[2], Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View view){
        String[] item_id = getResources().getResourceName(view.getId()).split("_");
        int table_id = Integer.parseInt(item_id[2]);
        Snackbar.make(findViewById(R.id.FirstFloorContent),
                "You have choosed " + item_id[1] + " table, number:" + item_id[2], Snackbar.LENGTH_SHORT).show();
        ImageView v = (ImageView) view;
        if(item_id[1].equals("outside")){
            v.setImageResource(R.drawable.seat_outside_selected);
        } else {
            v.setImageResource(R.drawable.seat_inside_selected);
        }
        Intent menu = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("tableId", table_id);
        menu.putExtras(bundle);
        startActivity(menu);
    }

}