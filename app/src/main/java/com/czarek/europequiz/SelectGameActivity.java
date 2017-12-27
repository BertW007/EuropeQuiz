package com.czarek.europequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        Button mapGameButton = findViewById(R.id.map_game_btn);

        mapGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapGameIntent = new Intent(SelectGameActivity.this, MapGameActivity.class);
                startActivity(mapGameIntent);
            }
        });

        Button flagsGameButton = findViewById(R.id.flags_game_btn);

        flagsGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "This game is not provided in this version", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
