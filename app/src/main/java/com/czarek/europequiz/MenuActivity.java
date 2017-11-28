package com.czarek.europequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button play = findViewById(R.id.play_button);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectGameIntent = new Intent(MenuActivity.this, SelectGameActivity.class);

                startActivity(selectGameIntent);
            }
        });

        Button viewMap = findViewById(R.id.view_map_button);

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMapIntent = new Intent(MenuActivity.this, InteractiveMapActivity.class);

                startActivity(viewMapIntent);
            }
        });

    }
}
