package com.czarek.europequiz;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link InteractiveMapActivity} - activity with interactive map of Europe.
 *
 * @author Czarek Pietrzak
 */
public class InteractiveMapActivity extends AppCompatActivity {

    /**
     * List of European countries.
     */
    ArrayList<Country> countries = createCountryList();

    /**
     * The mask used to read a touch using the color assigned to a given country.
     * It is invisible for the user.
     */
    ImageView mask;

    /**
     * The info button which pressed will show up a window with informations about selected country.
     */
    ImageButton infoButton;

    /**
     * The window with informations about selected country.
     */
    RelativeLayout countryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content of the activity by using the XML layout
        setContentView(R.layout.activity_interactive_map);

        //Get the views for mask, info button and window with informations about selected country
        mask = findViewById(R.id.europe_mask);
        infoButton = findViewById(R.id.info_button);
        countryInfo = findViewById(R.id.country_info);

        mask.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    //The user touched the screen
                    case MotionEvent.ACTION_UP:
                        //Get the position of touch on mask
                        int evX = (int) ev.getX();
                        int evY = (int) ev.getY();
                        float[] evXY = new float[]{evX, evY};

                        //Converting position of touch to single pixel
                        Matrix invertMatrix = new Matrix();
                        ((ImageView) v).getImageMatrix().invert(invertMatrix);

                        invertMatrix.mapPoints(evXY);
                        int x = Integer.valueOf((int) evXY[0]);
                        int y = Integer.valueOf((int) evXY[1]);

                        Drawable imgDrawable = ((ImageView) v).getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();

                        if (x < 0) x = 0;
                        else if (x > bitmap.getWidth() - 1) x = bitmap.getWidth() - 1;

                        if (y < 0) y = 0;
                        else if (y > bitmap.getHeight() - 1) y = bitmap.getHeight() - 1;

                        //Saving the touched pixel in a variable
                        int touchedRGB = bitmap.getPixel(x, y);

                        //Get color in the HEX code from the touched pixel
                        String RGB = Integer.toHexString(touchedRGB);

                        //Get country ID which is included in HEX code
                        String countryID = RGB.substring(6);

                        //Calling the function that changes the mask
                        changeHighlight(countryID);
                        break;
                }

                v.performClick();
                return true;
            }
        });

        infoButton.setOnClickListener(new OnClickListener() {
            //Po dotknięciu przycisku info
            @Override
            public void onClick(View v) {
                //Depending on info window visibility
                //Change visibility of the window and/or color of info button
                if (countryInfo.getVisibility() == View.GONE) {
                    countryInfo.setVisibility(View.VISIBLE);
                    infoButton.setImageResource(R.drawable.info_button_gray);
                } else {
                    countryInfo.setVisibility(View.GONE);
                    infoButton.setImageResource(R.drawable.info_button_red);
                }
            }
        });

        //This listener has been set to prevent the info window from disappearing when moving the map.
        //Thus, user can enable the info window for first selected country
        //and then, moving the map and selecting other countries for check the information.
        countryInfo.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        countryInfo.setVisibility(View.VISIBLE);
                        infoButton.setImageResource(R.drawable.info_button_gray);
                        break;
                }

                v.performClick();
                return true;
            }
        });

    }//onCreate()

    /**
     * The function that returns a list of European countries.
     *
     * @return  list of countries
     */
    public ArrayList<Country> createCountryList() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("Albania", "Tirana", "3 098 594", "28 748", R.drawable.highlight_albania, R.drawable.flag_albania, "01"));
        countries.add(new Country("Austria", "Vienna", "8 794 267", "83 879", R.drawable.highlight_austria, R.drawable.flag_austria, "02"));
        countries.add(new Country("Belgium", "Brussels", "11 303 528", "30 528", R.drawable.highlight_belgium, R.drawable.flag_belgium, "03"));
        countries.add(new Country("Belarus", "Minsk", "9 458 000", "207 560", R.drawable.highlight_belarus, R.drawable.flag_belarus, "04"));
        countries.add(new Country("Bosnia and Herzegovina", "Sarajevo", "3 843 126", "51 129", R.drawable.highlight_bosnia, R.drawable.flag_bosnia, "05"));
        countries.add(new Country("Bulgaria", "Sofia", "7 621 337", "110 910", R.drawable.highlight_bulgaria, R.drawable.flag_bulgaria, "06"));
        countries.add(new Country("Croatia", "Zagreb", "4 437 460", "56 542", R.drawable.highlight_croatia, R.drawable.flag_croatia, "07"));
        countries.add(new Country("Montenegro", "Podgorica", "616 258", "13 812", R.drawable.highlight_montenegro, R.drawable.flag_montenegro, "08"));
        countries.add(new Country("Czech Republic", "Prague", "10 256 760", "78 866", R.drawable.highlight_czech_republic, R.drawable.flag_czech_republic, "09"));

        countries.add(new Country("Denmark", "Copenhagen", "5 564 219", "43 094", R.drawable.highlight_denmark, R.drawable.flag_denmark, "10"));
        countries.add(new Country("Estonia", "Tallinn", "1 340 194", "45 226", R.drawable.highlight_estonia, R.drawable.flag_estonia, "11"));
        countries.add(new Country("Finland", "Helsinki", "5 157 537", "336 593", R.drawable.highlight_finland, R.drawable.flag_finland, "12"));
        countries.add(new Country("France", "Paris", "66 104 000", "547 030", R.drawable.highlight_france, R.drawable.flag_france, "13"));
        countries.add(new Country("Greece", "Athens", "11 123 034", "131 957", R.drawable.highlight_greece, R.drawable.flag_greece, "14"));
        countries.add(new Country("Spain", "Madrid", "47 059 533", "504 851", R.drawable.highlight_spain, R.drawable.flag_spain, "15"));
        countries.add(new Country("Netherlands", "Amsterdam", "16 902 103", "41 526", R.drawable.highlight_netherlands, R.drawable.flag_netherlands, "16"));
        countries.add(new Country("Ireland", "Dublin", "4 234 925", "70 280", R.drawable.highlight_ireland, R.drawable.flag_ireland, "17"));
        countries.add(new Country("Iceland", "Reykjavik", "307 261", "103 000", R.drawable.highlight_iceland, R.drawable.flag_iceland, "18"));
        countries.add(new Country("Lithuania", "Vilnius", "2 988 400", "65 300", R.drawable.highlight_lithuania, R.drawable.flag_lithuania, "19"));

        countries.add(new Country("Latvia", "Riga", "2 067 900", "64 589", R.drawable.highlight_latvia, R.drawable.flag_latvia, "20"));
        countries.add(new Country("Macedonia", "Skopje", "2 054 800", "25 713", R.drawable.highlight_macedonia, R.drawable.flag_macedonia, "21"));
        countries.add(new Country("Moldova", "Kishinev", "4 434 547", "33 843", R.drawable.highlight_moldova, R.drawable.flag_moldova, "22"));
        countries.add(new Country("Germany", "Berlin", "80 716 000", "357 021", R.drawable.highlight_germany, R.drawable.flag_germany, "23"));
        countries.add(new Country("Norway", "Oslo", "5 018 836", "385 178", R.drawable.highlight_norway, R.drawable.flag_norway, "24"));
        countries.add(new Country("Poland", "Warsaw", "38 625 478", "312 685", R.drawable.highlight_poland, R.drawable.flag_poland, "25"));
        countries.add(new Country("Portugal", "Lisbon", "10 409 995", "91 568", R.drawable.highlight_portugal, R.drawable.flag_portugal, "26"));
        countries.add(new Country("Russia", "Moscow", "143 975 923", "17 075 400", R.drawable.highlight_russia, R.drawable.flag_russia, "27"));
        countries.add(new Country("Romania", "Bucharest", "21 698 181", "238 391", R.drawable.highlight_romania, R.drawable.flag_romania, "28"));
        countries.add(new Country("Serbia", "Belgrade", "7 120 666", "88 361", R.drawable.highlight_serbia, R.drawable.flag_serbia, "29"));

        countries.add(new Country("Slovakia", "Bratislava", "5 422 366", "48 845", R.drawable.highlight_slovakia, R.drawable.flag_slovakia, "30"));
        countries.add(new Country("Slovenia", "Ljubljana", "2 050 189", "20 273", R.drawable.highlight_slovenia, R.drawable.flag_slovenia, "31"));
        countries.add(new Country("Switzerland", "Bern", "7 507 000", "41 290", R.drawable.highlight_switzerland, R.drawable.flag_switzerland, "32"));
        countries.add(new Country("Sweden", "Stockholm", "9 090 113", "449 964", R.drawable.highlight_sweden, R.drawable.flag_sweden, "33"));
        countries.add(new Country("Turkey", "Ankara", "77 695 904", "783 562", R.drawable.highlight_turkey, R.drawable.flag_turkey, "34"));
        countries.add(new Country("Ukraine", "Kiev", "45 360 000", "603 700", R.drawable.highlight_ukraine, R.drawable.flag_ukraine, "35"));
        countries.add(new Country("Hungary", "Budapest", "10 075 034", "93 030", R.drawable.highlight_hungary, R.drawable.flag_hungary, "36"));
        countries.add(new Country("United Kingdom", "London", "65 110 000", "244 820", R.drawable.highlight_united_kingdom, R.drawable.flag_united_kingdom, "37"));
        countries.add(new Country("Italy", "Rome", "60 655 464", "301 230", R.drawable.highlight_italy, R.drawable.flag_italy, "38"));

        return countries;
    }//createCountryList()

    /**
     * The function responsible for changing the mask, depending on the selected country.
     *
     * @param countryID     ID of the touched country
     */
    public void changeHighlight(String countryID) {
        //Getting current map mask
        ImageView highlight = findViewById(R.id.country_mask);

        //Setting clear Europe map as new default mask
        int newHighlight = R.drawable.europe_map;

        //Getting views of info button and info window
        infoButton = findViewById(R.id.info_button);
        countryInfo = findViewById(R.id.country_info);

        //A loop in which the country with the given ID is searched
        for (Country country : countries) {
            //In the case of finding the right country
            if (country.getCountryID().equals(countryID)) {
                //The new mask is replaced by the mask of the country
                newHighlight = country.getHighlightMask();

                //Setting the informations about the selected country
                setCountryInfo(country);
                break;
            }
        }

        //In case of not finding a country by ID the new mask has not been replaced
        if (newHighlight == R.drawable.europe_map) {
            //Hide the info window
            countryInfo.setVisibility(View.GONE);

            //The info button is set to red again
            infoButton.setImageResource(R.drawable.info_button_red);
        }

        //Calling the function responsible for info button visibility
        showButton(newHighlight);

        //Changing the mask to a new one
        highlight.setImageResource(newHighlight);
    }//changeHighlight()

    /**
     * The function responsible for the visibility of the info button.
     *
     * @param newHighlight new mask, set depending on the selected country
     */
    private void showButton(int newHighlight) {
        //Getting the info button view
        ImageButton infoButton = findViewById(R.id.info_button);

        //The default visibility setting
        int visibility = View.GONE;

        //If a new mask is not the default one
        if (newHighlight != R.drawable.europe_map) {
            //The visibility is changed
            visibility = View.VISIBLE;
        }

        //Setting the visibility of the info button
        infoButton.setVisibility(visibility);
    }//showButton()

    /**
     * The function that sets information about the selected country in the info window.
     *
     * @param country   country selected by the user
     */
    private void setCountryInfo(Country country) {
        //Getting views where information about country will be placed
        ImageView flagImage = findViewById(R.id.flag);
        TextView countryName = findViewById(R.id.country_name);
        TextView countryCapital = findViewById(R.id.country_capital);
        TextView countryPopulation = findViewById(R.id.country_population);
        TextView countryArea = findViewById(R.id.country_area);

        //Changing the country flag image
        flagImage.setImageResource(country.getFlag());

        //Changing other informations
        countryName.setText(country.getCountryName());
        countryCapital.setText(country.getCapital());
        countryPopulation.setText(country.getPopulation());
        countryArea.setText(country.getArea());
    }//setCountryInfo()

}//end of the InteractiveMapActivity class
