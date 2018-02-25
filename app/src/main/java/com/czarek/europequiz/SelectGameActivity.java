package com.czarek.europequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * {@link SelectGameActivity} - activity where user selecting the game
 *
 * @author Czarek Pietrzak
 */
public class SelectGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content of the activity by using the XML layout
        setContentView(R.layout.activity_select_game);

        //Find {@link Button} responsible for moving to map game
        Button mapGameButton = findViewById(R.id.map_game_btn);

        mapGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new {@link Intent} object to move to map game
                Intent mapGameIntent = new Intent(SelectGameActivity.this, MapGameActivity.class);

                //Start a new activity
                startActivity(mapGameIntent);
            }
        });

        //Find {@link Button} responsible for moving to arcade game with flags
        Button flagsGameButton = findViewById(R.id.flags_game_btn);

        flagsGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new {@link Intent} object to move to arcade game with flags
                Intent flagGameIntent = new Intent(SelectGameActivity.this, FlagGameActivity.class);

                //Start a new activity
                startActivity(flagGameIntent);
            }
        });
    }//onCreate()
}//end of the SelectGameActivity class
