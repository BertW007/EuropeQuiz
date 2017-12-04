package com.czarek.europequiz;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class InteractiveMapActivity extends AppCompatActivity {

    ArrayList<Country> countries = createCountryList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_map);

        ImageView mask = findViewById(R.id.europe_mask);

        mask.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                int evX = (int) ev.getX();
                int evY = (int) ev.getY();
                float[] evXY = new float[]{evX, evY};

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

                int touchedRGB = bitmap.getPixel(x, y);
                String RGB = Integer.toHexString(touchedRGB);
                String countryID = RGB.substring(6);
                changeHighlight(countries, countryID);

                v.performClick();
                return true;
            }
        });
    }

    public ArrayList<Country> createCountryList() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("Albania", "Tirana", "3 098 594", "28 748", R.drawable.highlight_albania, "01"));
        countries.add(new Country("Austria", "Vienna", "8 794 267", "83 879", R.drawable.highlight_austria, "02"));
        countries.add(new Country("Belgium", "Brussels", "11 303 528", "30 528", R.drawable.highlight_belgium, "03"));
        countries.add(new Country("Belarus", "Minsk", "9 458 000", "207 560", R.drawable.highlight_belarus, "04"));
        countries.add(new Country("Bosnia and Herzegovina", "Sarajevo", "3 843 126", "51 129", R.drawable.highlight_bosnia, "05"));
        countries.add(new Country("Bulgaria", "Sofia", "7 621 337", "110 910", R.drawable.highlight_bulgaria, "06"));
        countries.add(new Country("Croatia", "Zagreb", "4 437 460", "56 542", R.drawable.highlight_croatia, "07"));
        countries.add(new Country("Montenegro", "Podgorica", "616 258", "13 812", R.drawable.highlight_montenegro, "08"));
        countries.add(new Country("Czech Republic", "Prague", "10 256 760", "78 866", R.drawable.highlight_czech_republic, "09"));

        countries.add(new Country("Denmark", "Copenhagen", "5 564 219", "43 094", R.drawable.highlight_denmark, "10"));
        countries.add(new Country("Estonia", "Tallinn", "1 340 194", "45 226", R.drawable.highlight_estonia, "11"));
        countries.add(new Country("Finland", "Helsinki", "5 157 537", "336 593", R.drawable.highlight_finland, "12"));
        countries.add(new Country("France", "Paris", "66 104 000", "547 030", R.drawable.highlight_france, "13"));
        countries.add(new Country("Greece", "Athens", "11 123 034", "131 957", R.drawable.highlight_greece, "14"));
        countries.add(new Country("Spain", "Madrid", "47 059 533", "504 851", R.drawable.highlight_spain, "15"));
        countries.add(new Country("Netherlands", "Amsterdam", "16 902 103", "41 526", R.drawable.highlight_netherlands, "16"));
        countries.add(new Country("Ireland", "Dublin", "4 234 925", "70 280", R.drawable.highlight_ireland, "17"));
        countries.add(new Country("Iceland", "Reykjavik", "307 261", "103 000", R.drawable.highlight_iceland, "18"));
        countries.add(new Country("Lithuania", "Vilnius", "2 988 400", "65 300", R.drawable.highlight_lithuania, "19"));

        countries.add(new Country("Latvia", "Riga", "2 067 900", "64 589", R.drawable.highlight_latvia, "20"));
        countries.add(new Country("Macedonia", "Skopje", "2 054 800", "25 713", R.drawable.highlight_macedonia, "21"));
        countries.add(new Country("Moldova", "Kishinev", "4 434 547", "33 843", R.drawable.highlight_moldova, "22"));
        countries.add(new Country("Germany", "Berlin", "80 716 000", "357 021", R.drawable.highlight_germany, "23"));
        countries.add(new Country("Norway", "Oslo", "5 018 836", "385 178", R.drawable.highlight_norway, "24"));
        countries.add(new Country("Poland", "Warsaw", "38 625 478", "312 685", R.drawable.highlight_poland, "25"));
        countries.add(new Country("Portugal", "Lisbon", "10 409 995", "91 568", R.drawable.highlight_portugal, "26"));
        countries.add(new Country("Russia", "Moscow", "143 975 923", "17 075 400", R.drawable.highlight_russia, "27"));
        countries.add(new Country("Romania", "Bucharest", "21 698 181", "238 391", R.drawable.highlight_romania, "28"));
        countries.add(new Country("Serbia", "Belgrade", "7 120 666", "88 361", R.drawable.highlight_serbia, "29"));

        countries.add(new Country("Slovakia", "Bratislava", "5 422 366", "48 845", R.drawable.highlight_slovakia, "30"));
        countries.add(new Country("Slovenia", "Ljubljana", "2 050 189", "20 273", R.drawable.highlight_slovenia, "31"));
        countries.add(new Country("Switzerland", "Bern", "7 507 000", "41 290", R.drawable.highlight_switzerland, "32"));
        countries.add(new Country("Sweden", "Stockholm", "9 090 113", "449 964", R.drawable.highlight_sweden, "33"));
        countries.add(new Country("Turkey", "Ankara", "77 695 904", "783 562", R.drawable.highlight_turkey, "34"));
        countries.add(new Country("Ukraine", "Kiev", "45 360 000", "603 700", R.drawable.highlight_ukraine, "35"));
        countries.add(new Country("Hungary", "Budapest", "10 075 034", "93 030", R.drawable.highlight_hungary, "36"));
        countries.add(new Country("United Kingdom", "London", "65 110 000", "244 820", R.drawable.highlight_united_kingdom, "37"));
        countries.add(new Country("Italy", "Rome", "60 655 464", "301 230", R.drawable.highlight_italy, "38"));

        return countries;
    }

    public void changeHighlight(ArrayList<Country> countries, String countryID) {
        ImageView highlight = findViewById(R.id.country_mask);
        int newHighlight = R.drawable.europe_map;

        for (Country country : countries) {
            if (country.getCountryID().equals(countryID)) {
                newHighlight = country.getHighlightMask();
            }
        }

        highlight.setImageResource(newHighlight);
    }


}
