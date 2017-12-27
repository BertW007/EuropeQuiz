package com.czarek.europequiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class MapGameActivity extends AppCompatActivity {

    ArrayList<Country> countries = createCountryList();
    String touchedCountryID;
    String IDofCountryToFind;
    Country countryToFind;
    Bitmap actualHighlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_game);

        final TextView leftCountries = findViewById(R.id.left_countries);
        leftCountries.setText("" + countries.size());

        final ImageView mask = findViewById(R.id.europe_mask);

        mask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                switch(ev.getAction()){
                    case MotionEvent.ACTION_UP:
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
                        touchedCountryID = RGB.substring(6);
                        changeHighlight(touchedCountryID);
                        break;
                }

                v.performClick();
                return true;
            }
        });

        final RelativeLayout answerBox = findViewById(R.id.answer_box);

        answerBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch(ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        answerBox.setVisibility(View.VISIBLE);
                        break;
                }

                v.performClick();
                return true;
            }
        });

        final RelativeLayout finishBox = findViewById(R.id.finish_box);

        finishBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch(ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        finishBox.setVisibility(View.VISIBLE);
                        break;
                }

                v.performClick();
                return true;
            }
        });

        final TextView countryToFindView = findViewById(R.id.country_to_find);

        countryToFindView.setText(randomCountry());

        final Button confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmButton.setVisibility(View.GONE);
                mask.setEnabled(false);

                String selectedCountryName = "";
                for (Country country : countries) {
                    if (country.getCountryID().equals(touchedCountryID)) {
                        setCountryInfo(country);
                        selectedCountryName = country.getCountryName();
                    }
                }
                TextView message = findViewById(R.id.message);
                RelativeLayout answerBox = findViewById(R.id.answer_box);

                if(isSelectedCountryCorrect(touchedCountryID, IDofCountryToFind)) {
                    message.setText("Correct!");
                } else {
                    message.setText("Wrong! You selected " + selectedCountryName);
                }

                answerBox.setVisibility(View.VISIBLE);
            }
        });

        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView europeMap = findViewById(R.id.europe_map);
                ImageView countryMask = findViewById(R.id.country_mask);
                answerBox.setVisibility(View.GONE);
                mask.setEnabled(true);
                BitmapDrawable drawable = (BitmapDrawable) europeMap.getDrawable();
                actualHighlight = drawable.getBitmap();

                if(isSelectedCountryCorrect(touchedCountryID, IDofCountryToFind)){

                    Bitmap grayHighlight = null;
                    for(Country country : countries) {
                        if(country.getCountryID().equals(IDofCountryToFind)) {
                            grayHighlight = BitmapFactory.decodeResource(view.getResources(), country.getGrayMask());
                        }
                    }

                    actualHighlight = mergeToPin(actualHighlight, grayHighlight);

                    if(countries.size() > 1) {
                        countries.remove(countryToFind);
                        leftCountries.setText("" + countries.size());
                    } else {
                        showFinishBox();
                    }
                }

                countryMask.setVisibility(View.GONE);
                europeMap.setImageBitmap(actualHighlight);
                countryToFindView.setText(randomCountry());
            }
        });

        Button goToMenuButton = findViewById(R.id.go_to_menu_button);

        goToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectGameIntent = new Intent(MapGameActivity.this, MenuActivity.class);
                startActivity(selectGameIntent);
            }
        });

    }

    public static Bitmap mergeToPin(Bitmap back, Bitmap front) {
        Bitmap result = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(result);
        int widthBack = back.getWidth();
        int widthFront = front.getWidth();
        float move = (widthBack - widthFront) / 2;
        canvas.drawBitmap(back, 0f, 0f, null);
        canvas.drawBitmap(front, move, move, null);
        return result;
    }

    private void showFinishBox() {
        RelativeLayout finishBox = findViewById(R.id.finish_box);
        finishBox.setVisibility(View.VISIBLE);
    }

    private boolean isSelectedCountryCorrect(String touchedID, String IDToFind) {
        return touchedID.equals(IDToFind);
    }

    private String randomCountry() {
        String countryName = "";
        int max = countries.size();
        int randomIndex = (int) (Math.random() * max);
        countryToFind = countries.get(randomIndex);

        IDofCountryToFind = countryToFind.getCountryID();
        countryName = countryToFind.getCountryName();
        setCountryInfo(countryToFind);

        return countryName;
    }

    public ArrayList<Country> createCountryList() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("Albania", "Tirana", "3 098 594", "28 748", R.drawable.highlight_albania2, R.drawable.gray_highlight_albania, R.drawable.flag_albania, "01"));
        countries.add(new Country("Austria", "Vienna", "8 794 267", "83 879", R.drawable.highlight_austria2, R.drawable.gray_highlight_austria, R.drawable.flag_austria, "02"));
        countries.add(new Country("Belgium", "Brussels", "11 303 528", "30 528", R.drawable.highlight_belgium2, R.drawable.gray_highlight_belgium, R.drawable.flag_belgium, "03"));
        countries.add(new Country("Belarus", "Minsk", "9 458 000", "207 560", R.drawable.highlight_belarus2, R.drawable.gray_highlight_belarus, R.drawable.flag_belarus, "04"));
        countries.add(new Country("Bosnia and Herzegovina", "Sarajevo", "3 843 126", "51 129", R.drawable.highlight_bosnia2, R.drawable.gray_highlight_bosnia, R.drawable.flag_bosnia, "05"));
        countries.add(new Country("Bulgaria", "Sofia", "7 621 337", "110 910", R.drawable.highlight_bulgaria2, R.drawable.gray_highlight_bulgaria, R.drawable.flag_bulgaria, "06"));
        countries.add(new Country("Croatia", "Zagreb", "4 437 460", "56 542", R.drawable.highlight_croatia2, R.drawable.gray_highlight_croatia, R.drawable.flag_croatia, "07"));
        countries.add(new Country("Montenegro", "Podgorica", "616 258", "13 812", R.drawable.highlight_montenegro2, R.drawable.gray_highlight_montenegro, R.drawable.flag_montenegro, "08"));
        countries.add(new Country("Czech Republic", "Prague", "10 256 760", "78 866", R.drawable.highlight_czech_republic2, R.drawable.gray_highlight_czech_republic, R.drawable.flag_czech_republic, "09"));

        countries.add(new Country("Denmark", "Copenhagen", "5 564 219", "43 094", R.drawable.highlight_denmark2, R.drawable.gray_highlight_denmark, R.drawable.flag_denmark, "10"));
        countries.add(new Country("Estonia", "Tallinn", "1 340 194", "45 226", R.drawable.highlight_estonia2, R.drawable.gray_highlight_estonia, R.drawable.flag_estonia, "11"));
        countries.add(new Country("Finland", "Helsinki", "5 157 537", "336 593", R.drawable.highlight_finland2, R.drawable.gray_highlight_finland, R.drawable.flag_finland, "12"));
        countries.add(new Country("France", "Paris", "66 104 000", "547 030", R.drawable.highlight_france2, R.drawable.gray_highlight_france, R.drawable.flag_france, "13"));
        countries.add(new Country("Greece", "Athens", "11 123 034", "131 957", R.drawable.highlight_greece2, R.drawable.gray_highlight_greece, R.drawable.flag_greece, "14"));
        countries.add(new Country("Spain", "Madrid", "47 059 533", "504 851", R.drawable.highlight_spain2, R.drawable.gray_highlight_spain, R.drawable.flag_spain, "15"));
        countries.add(new Country("Netherlands", "Amsterdam", "16 902 103", "41 526", R.drawable.highlight_netherlands2, R.drawable.gray_highlight_netherlands, R.drawable.flag_netherlands, "16"));
        countries.add(new Country("Ireland", "Dublin", "4 234 925", "70 280", R.drawable.highlight_ireland2, R.drawable.gray_highlight_ireland, R.drawable.flag_ireland, "17"));
        countries.add(new Country("Iceland", "Reykjavik", "307 261", "103 000", R.drawable.highlight_iceland2, R.drawable.gray_highlight_iceland, R.drawable.flag_iceland, "18"));
        countries.add(new Country("Lithuania", "Vilnius", "2 988 400", "65 300", R.drawable.highlight_lithuania2, R.drawable.gray_highlight_lithuania, R.drawable.flag_lithuania, "19"));

        countries.add(new Country("Latvia", "Riga", "2 067 900", "64 589", R.drawable.highlight_latvia2, R.drawable.gray_highlight_latvia, R.drawable.flag_latvia, "20"));
        countries.add(new Country("Macedonia", "Skopje", "2 054 800", "25 713", R.drawable.highlight_macedonia2, R.drawable.gray_highlight_macedonia, R.drawable.flag_macedonia, "21"));
        countries.add(new Country("Moldova", "Kishinev", "4 434 547", "33 843", R.drawable.highlight_moldova2, R.drawable.gray_highlight_moldova, R.drawable.flag_moldova, "22"));
        countries.add(new Country("Germany", "Berlin", "80 716 000", "357 021", R.drawable.highlight_germany2, R.drawable.gray_highlight_germany, R.drawable.flag_germany, "23"));
        countries.add(new Country("Norway", "Oslo", "5 018 836", "385 178", R.drawable.highlight_norway2, R.drawable.gray_highlight_norway, R.drawable.flag_norway, "24"));
        countries.add(new Country("Poland", "Warsaw", "38 625 478", "312 685", R.drawable.highlight_poland2, R.drawable.gray_highlight_poland, R.drawable.flag_poland, "25"));
        countries.add(new Country("Portugal", "Lisbon", "10 409 995", "91 568", R.drawable.highlight_portugal2, R.drawable.gray_highlight_portugal, R.drawable.flag_portugal, "26"));
        countries.add(new Country("Russia", "Moscow", "143 975 923", "17 075 400", R.drawable.highlight_russia2, R.drawable.gray_highlight_russia, R.drawable.flag_russia, "27"));
        countries.add(new Country("Romania", "Bucharest", "21 698 181", "238 391", R.drawable.highlight_romania2, R.drawable.gray_highlight_romania, R.drawable.flag_romania, "28"));
        countries.add(new Country("Serbia", "Belgrade", "7 120 666", "88 361", R.drawable.highlight_serbia2, R.drawable.gray_highlight_serbia, R.drawable.flag_serbia, "29"));

        countries.add(new Country("Slovakia", "Bratislava", "5 422 366", "48 845", R.drawable.highlight_slovakia2, R.drawable.gray_highlight_slovakia, R.drawable.flag_slovakia, "30"));
        countries.add(new Country("Slovenia", "Ljubljana", "2 050 189", "20 273", R.drawable.highlight_slovenia2, R.drawable.gray_highlight_slovenia, R.drawable.flag_slovenia, "31"));
        countries.add(new Country("Switzerland", "Bern", "7 507 000", "41 290", R.drawable.highlight_switzerland2, R.drawable.gray_highlight_switzerland, R.drawable.flag_switzerland, "32"));
        countries.add(new Country("Sweden", "Stockholm", "9 090 113", "449 964", R.drawable.highlight_sweden2, R.drawable.gray_highlight_sweden, R.drawable.flag_sweden, "33"));
        countries.add(new Country("Turkey", "Ankara", "77 695 904", "783 562", R.drawable.highlight_turkey2, R.drawable.gray_highlight_turkey, R.drawable.flag_turkey, "34"));
        countries.add(new Country("Ukraine", "Kiev", "45 360 000", "603 700", R.drawable.highlight_ukraine2, R.drawable.gray_highlight_ukraine, R.drawable.flag_ukraine, "35"));
        countries.add(new Country("Hungary", "Budapest", "10 075 034", "93 030", R.drawable.highlight_hungary2, R.drawable.gray_highlight_hungary, R.drawable.flag_hungary, "36"));
        countries.add(new Country("United Kingdom", "London", "65 110 000", "244 820", R.drawable.highlight_united_kingdom2, R.drawable.gray_highlight_united_kingdom, R.drawable.flag_united_kingdom, "37"));
        countries.add(new Country("Italy", "Rome", "60 655 464", "301 230", R.drawable.highlight_italy2, R.drawable.gray_highlight_italy, R.drawable.flag_italy, "38"));

        return countries;
    }

    public void changeHighlight(String countryID) {
        ImageView highlight = findViewById(R.id.country_mask);
        Bitmap newHighlight = actualHighlight;
        Button confirmButton = findViewById(R.id.confirm_button);

        for (Country country : countries) {
            if (country.getCountryID().equals(countryID)) {
                newHighlight = BitmapFactory.decodeResource(getResources(), country.getHighlightMask());
                confirmButton.setVisibility(View.VISIBLE);
                break;
            }
        }

        if(newHighlight == actualHighlight) {
            confirmButton.setVisibility(View.GONE);
        }


        highlight.setImageBitmap(newHighlight);
        highlight.setVisibility(View.VISIBLE);
    }

    private void setCountryInfo(Country country) {
        ImageView flagImage = findViewById(R.id.flag);
        TextView countryName = findViewById(R.id.country_name);
        TextView countryCapital = findViewById(R.id.country_capital);
        TextView countryPopulation = findViewById(R.id.country_population);
        TextView countryArea = findViewById(R.id.country_area);

        flagImage.setImageResource(country.getFlag());
        countryName.setText(country.getCountryName());
        countryCapital.setText(country.getCapital());
        countryPopulation.setText(country.getPopulation());
        countryArea.setText(country.getArea());
    }
}
