package com.czarek.europequiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView mapOfEuropeLink = findViewById(R.id.map_of_europe_link);

        mapOfEuropeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://commons.wikimedia.org/wiki/File:Europe_map_clear.png"));
                startActivity(browserIntent);
            }
        });

        TextView wikimediaLink = findViewById(R.id.wikimedia_link);

        wikimediaLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://commons.wikimedia.org/wiki/Main_Page"));
                startActivity(browserIntent);
            }
        });

        TextView flagsLink = findViewById(R.id.flags_link);

        flagsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/packs/international-flags-6"));
                startActivity(browserIntent);
            }
        });

        TextView freepikLink = findViewById(R.id.freepik_link);

        freepikLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freepik.com/"));
                startActivity(browserIntent);
            }
        });

        TextView flaticonLink = findViewById(R.id.flaticon_link);

        flaticonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/"));
                startActivity(browserIntent);
            }
        });


        TextView infoButtonLink = findViewById(R.id.info_button_link);

        infoButtonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/free-icon/information-button_108153"));
                startActivity(browserIntent);
            }
        });

        TextView freepikLink2 = findViewById(R.id.freepik_link_2);

        freepikLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freepik.com/"));
                startActivity(browserIntent);
            }
        });

        TextView flaticonLink2 = findViewById(R.id.flaticon_link_2);

        flaticonLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/"));
                startActivity(browserIntent);
            }
        });
    }
}
