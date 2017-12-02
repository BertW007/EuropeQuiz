package com.czarek.europequiz;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class InteractiveMapActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_map);

        ArrayList<Country> countries = createCountryList();

        ImageView mask = findViewById(R.id.europe_mask);
        ImageView highlight = findViewById(R.id.country_mask);

        mask.setOnTouchListener (this);
    }

    public ArrayList<Country> createCountryList() {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries.add(new Country("Albania",                "Tirana",       "3 098 594",    "28 748",       R.drawable.highlight_albania,           Color.rgb(204,170, 1)));
        countries.add(new Country("Austria",                "Vienna",       "8 794 267",    "83 879",       R.drawable.highlight_austria,           Color.rgb(204,170, 2)));
        countries.add(new Country("Belgium",                "Brussels",     "11 303 528",   "30 528",       R.drawable.highlight_belgium,           Color.rgb(204,187, 3)));
        countries.add(new Country("Belarus",                "Minsk",        "9 458 000",    "207 560",      R.drawable.highlight_belarus,           Color.rgb(204,187, 4)));
        countries.add(new Country("Bosnia and Herzegovina", "Sarajevo",     "3 843 126",    "51 129",       R.drawable.highlight_bosnia,            Color.rgb(204,187, 5)));
        countries.add(new Country("Bulgaria",               "Sofia",        "7 621 337",    "110 910",      R.drawable.highlight_bulgaria,          Color.rgb(204,187, 6)));
        countries.add(new Country("Croatia",                "Zagreb",       "4 437 460",    "56 542",       R.drawable.highlight_croatia,           Color.rgb(204,204, 7)));
        countries.add(new Country("Montenegro",             "Podgorica",    "616 258",      "13 812",       R.drawable.highlight_montenegro,        Color.rgb(204,204, 8)));
        countries.add(new Country("Czech Republic",         "Prague",       "10 256 760",   "78 866",       R.drawable.highlight_czech_republic,    Color.rgb(204,204, 9)));

        countries.add(new Country("Denmark",                "Copenhagen",   "5 564 219",    "43 094",       R.drawable.highlight_denmark,           Color.rgb(204,221, 16)));
        countries.add(new Country("Estonia",                "Tallinn",      "1 340 194",    "45 226",       R.drawable.highlight_estonia,           Color.rgb(204,238, 17)));
        countries.add(new Country("Finland",                "Helsinki",     "5 157 537",    "336 593",      R.drawable.highlight_finland,           Color.rgb(204,255, 18)));
        countries.add(new Country("France",                 "Paris",        "66 104 000",   "547 030",      R.drawable.highlight_france,            Color.rgb(204,255, 19)));
        countries.add(new Country("Greece",                 "Athens",       "11 123 034",   "131 957",      R.drawable.highlight_greece,            Color.rgb(204,170, 20)));
        countries.add(new Country("Spain",                  "Madrid",       "47 059 533",   "504 851",      R.drawable.highlight_spain,             Color.rgb(204,187, 21)));
        countries.add(new Country("Netherlands",            "Amsterdam",    "16 902 103",   "41 526",       R.drawable.highlight_netherlands,       Color.rgb(204,187, 22)));
        countries.add(new Country("Ireland",                "Dublin",       "4 234 925",    "70 280",       R.drawable.highlight_ireland,           Color.rgb(204,204, 23)));
        countries.add(new Country("Iceland",                "Reykjavik",    "307 261",      "103 000",      R.drawable.highlight_iceland,           Color.rgb(204,204, 24)));
        countries.add(new Country("Lithuania",              "Vilnius",      "2 988 400",    "65 300",       R.drawable.highlight_lithuania,         Color.rgb(204,221, 25)));

        countries.add(new Country("Latvia",                 "Riga",         "2 067 900",    "64 589",       R.drawable.highlight_latvia,            Color.rgb(204,221, 32)));
        countries.add(new Country("Macedonia",              "Skopje",       "2 054 800",    "25 713",       R.drawable.highlight_macedonia,         Color.rgb(204,238, 33)));
        countries.add(new Country("Moldova",                "Kishinev",     "4 434 547",    "33 843",       R.drawable.highlight_moldova,           Color.rgb(204,238, 34)));
        countries.add(new Country("Germany",                "Berlin",       "80 716 000",   "357 021",      R.drawable.highlight_germany,           Color.rgb(204,255, 35)));
        countries.add(new Country("Norway",                 "Oslo",         "5 018 836",    "385 178",      R.drawable.highlight_norway,            Color.rgb(204,255, 36)));
        countries.add(new Country("Poland",                 "Warsaw",       "38 625 478",   "312 685",      R.drawable.highlight_poland,            Color.rgb(204,170, 37)));
        countries.add(new Country("Portugal",               "Lisbon",       "10 409 995",   "91 568",       R.drawable.highlight_portugal,          Color.rgb(204,170, 38)));
        countries.add(new Country("Russia",                 "Moscow",       "143 975 923",  "17 075 400",   R.drawable.highlight_russia,            Color.rgb(204,187, 39)));
        countries.add(new Country("Romania",                "Bucharest",    "21 698 181",   "238 391",      R.drawable.highlight_romania,           Color.rgb(204,187, 40)));
        countries.add(new Country("Serbia",                 "Belgrade",     "7 120 666",    "88 361",       R.drawable.highlight_serbia,            Color.rgb(204,204, 41)));

        countries.add(new Country("Slovakia",               "Bratislava",   "5 422 366",    "48 845",       R.drawable.highlight_slovakia,          Color.rgb(204,204, 48)));
        countries.add(new Country("Slovenia",               "Ljubljana",    "2 050 189",    "20 273",       R.drawable.highlight_slovenia,          Color.rgb(204,204, 49)));
        countries.add(new Country("Switzerland",            "Bern",         "7 507 000",    "41 290",       R.drawable.highlight_switzerland,       Color.rgb(204,204, 50)));
        countries.add(new Country("Sweden",                 "Stockholm",    "9 090 113",    "449 964",      R.drawable.highlight_sweden,            Color.rgb(204,204, 51)));
        countries.add(new Country("Turkey",                 "Ankara",       "77 695 904",   "783 562",      R.drawable.highlight_turkey,            Color.rgb(204,221, 52)));
        countries.add(new Country("Ukraine",                "Kiev",         "45 360 000",   "603 700",      R.drawable.highlight_ukraine,           Color.rgb(204,238, 53)));
        countries.add(new Country("Hungary",                "Budapest",     "10 075 034",   "93 030",       R.drawable.highlight_hungary,           Color.rgb(204,255, 54)));
        countries.add(new Country("United Kingdom",         "London",       "65 110 000",   "244 820",      R.drawable.highlight_united_kingdom,    Color.rgb(204,255, 55)));
        countries.add(new Country("Italy",                  "Rome",         "60 655 464",   "301 230",      R.drawable.highlight_italy,             Color.rgb(204,255, 56)));

        return countries;
    }

    @Override
    public boolean onTouch(View view, MotionEvent ev) {
        final int action = ev.getAction();
        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        return false;
    }
}
