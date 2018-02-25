package com.czarek.europequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * {@link MenuActivity} - menu from which the user can go to three activities:
 *                        select game, interactive map and information about used resources.
 *
 * @author Czarek Pietrzak
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content of the activity by using the XML layout
        setContentView(R.layout.activity_menu);

        //Find {@link Button} responsible for moving to select game menu
        Button play = findViewById(R.id.play_button);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new {@link Intent} object to move to select game menu
                Intent selectGameIntent = new Intent(MenuActivity.this, SelectGameActivity.class);

                //Start a new activity
                startActivity(selectGameIntent);
            }
        });

        //Find {@link Button} responsible for moving to interactive map view
        Button viewMap = findViewById(R.id.view_map_button);

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new {@link Intent} object to move to interactive map view
                Intent viewMapIntent = new Intent(MenuActivity.this, InteractiveMapActivity.class);

                //Start a new activity
                startActivity(viewMapIntent);
            }
        });

        //Find {@link Button} responsible for moving to view about used resources
        Button about = findViewById(R.id.about_button);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new {@link Intent} object to move to view about used resources
                Intent aboutIntent = new Intent(MenuActivity.this, AboutActivity.class);

                //Start a new activity
                startActivity(aboutIntent);
            }
        });

    }//onCreate()
}//end of the MenuActivity class
