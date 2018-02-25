package com.czarek.europequiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * {@link AboutActivity} - here are placed links to graphical sources used in the application.
 *
 * @author Czarek Pietrzak
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content of the activity by using the XML layout
        setContentView(R.layout.activity_about);

        //Finding the {@link TextView} - link to the graphical source, a map of Europe
        TextView mapOfEuropeLink = findViewById(R.id.map_of_europe_link);

        mapOfEuropeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://commons.wikimedia.org/wiki/File:Europe_map_clear.png"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to wikimedia
        TextView wikimediaLink = findViewById(R.id.wikimedia_link);

        wikimediaLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://commons.wikimedia.org/wiki/Main_Page"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the source, set of flags countries
        TextView flagsLink = findViewById(R.id.flags_link);

        flagsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/packs/international-flags-6"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the author's website (Freepik)
        TextView freepikLink = findViewById(R.id.freepik_link);

        freepikLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freepik.com/"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the Flaticon website
        TextView flaticonLink = findViewById(R.id.flaticon_link);

        flaticonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the source, info symbol
        TextView infoButtonLink = findViewById(R.id.info_button_link);

        infoButtonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/free-icon/information-button_108153"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the author's website (Freepik)
        TextView freepikLink2 = findViewById(R.id.freepik_link_2);

        freepikLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freepik.com/"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the Flaticon website
        TextView flaticonLink2 = findViewById(R.id.flaticon_link_2);

        flaticonLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the source, app logo
        TextView logoLink = findViewById(R.id.logo_link);

        logoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/free-icon/europe_664549"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the Smashicons website
        TextView smashiconsLink = findViewById(R.id.smashicons_link);

        smashiconsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://smashicons.com/"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });

        //Finding the {@link TextView} - link to the Flaticon website
        TextView flaticonLink3 = findViewById(R.id.flaticon_link_3);

        flaticonLink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to open a link in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/"));

                //Start a new activity
                startActivity(browserIntent);
            }
        });
    }//onCreate()
}//end of the AboutActivity class
