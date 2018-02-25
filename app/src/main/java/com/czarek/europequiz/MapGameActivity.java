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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@link MapGameActivity} - activity with map game.
 *
 * @author Czarek Pietrzak
 */
public class MapGameActivity extends AppCompatActivity {
    /**
     * List of European countries.
     */
    ArrayList<Country> countries = createCountryList();

    /**
     * ID of the country selected by player.
     */
    String touchedCountryID;

    /**
     * ID of the country to find.
     */
    String IDofCountryToFind;

    /**
     * The country to find.
     */
    Country countryToFind;

    /**
     * The current mask in form of a bitmap.
     */
    Bitmap actualHighlight;

    /**
     * {@link TextView} which contains the number of countries remaining to guess.
     */
    TextView leftCountries;

    /**
     * A mask used to read a touch using the color assigned to a given country.
     * It is invisible to the player.
     */
    ImageView mask;

    /**
     * The window that shows up after each answer.
     * It contains information for the player whether his choice was correct or not.
     */
    RelativeLayout answerBox;

    /**
     * The window that appears after guessing all countries.
     */
    RelativeLayout finishBox;

    /**
     * {@link TextView} which contains the name of the country to guess.
     */
    TextView countryToFindView;

    /**
     * The button that the player confirms his choice.
     */
    Button confirmButton;

    /**
     * The button in the answerBox that the player goes to the next country.
     */
    Button nextButton;

    /**
     * The button in the finishBox that the player goes to the main menu.
     */
    Button goToMenuButton;

    /**
     * The country mask applied to the default mask.
     * It is visible for the player.
     */
    ImageView highlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content of the activity by using the XML layout
        setContentView(R.layout.activity_map_game);

        //Get the view containing the number of remaining countries to guess
        leftCountries = findViewById(R.id.left_countries);

        //Inserting the number of remaining countries to guess
        leftCountries.setText("" + countries.size());

        //Get the mask view
        mask = findViewById(R.id.europe_mask);

        mask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    //The player touched the screen
                    case MotionEvent.ACTION_UP:
                        //Get the mask touch position
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
                        touchedCountryID = RGB.substring(6);

                        //Calling the function that changes the mask
                        changeHighlight(touchedCountryID);
                        break;
                }

                v.performClick();
                return true;
            }
        });

        //Get the view of window that appears after each answer
        answerBox = findViewById(R.id.answer_box);

        answerBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    //The player releases the finger from the screen
                    case MotionEvent.ACTION_DOWN:
                        //The window remains visible
                        answerBox.setVisibility(View.VISIBLE);
                        break;
                }

                v.performClick();
                return true;
            }
        });

        //Get the view of window that appears after finishing the game
        finishBox = findViewById(R.id.finish_box);

        finishBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    //The player releases the finger from the screen
                    case MotionEvent.ACTION_DOWN:
                        //The window remains visible
                        finishBox.setVisibility(View.VISIBLE);
                        break;
                }

                v.performClick();
                return true;
            }
        });

        //Get a view containing the name of the country that is currently guessed
        countryToFindView = findViewById(R.id.country_to_find);

        //Setting the country name to guess
        countryToFindView.setText(randomCountry());

        //Getting the button confirming the user's choice
        confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide the button
                confirmButton.setVisibility(View.GONE);

                //Block the choice of country
                mask.setEnabled(false);

                //Searching for the selected country
                String selectedCountryName = "";
                for (Country country : countries) {
                    //If a matching country was found
                    if (country.getCountryID().equals(touchedCountryID)) {
                        //Set informations about this country in answerBox
                        setCountryInfo(country);
                        //Save the name of the selected country
                        selectedCountryName = country.getCountryName();
                    }
                }

                //Get the views of answerBox and TextView where will be entered the appropriate message
                answerBox = findViewById(R.id.answer_box);
                TextView message = findViewById(R.id.message);

                //Check if the selected country is the country you are looking for
                //Assign the appropriate message in the text field
                if (isSelectedCountryCorrect(touchedCountryID, IDofCountryToFind)) {
                    message.setText("Correct!");
                } else {
                    message.setText("Wrong! You selected " + selectedCountryName);
                }

                //Make the answerBox visible
                answerBox.setVisibility(View.VISIBLE);
            }
        });

        //Get the button that the player goes to the next country to guess
        nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the masks:
                //default (Europe, blue) and temporary (country, orange)
                ImageView europeMap = findViewById(R.id.europe_map);
                ImageView countryMask = findViewById(R.id.country_mask);

                //Hide the answerBox
                answerBox.setVisibility(View.GONE);

                //Unlock country selection
                mask.setEnabled(true);

                //Get the current mask visible for the user
                BitmapDrawable drawable = (BitmapDrawable) europeMap.getDrawable();

                //Save the current mask as a bitmap for processing
                actualHighlight = drawable.getBitmap();

                //If the user's choice was correct
                if (isSelectedCountryCorrect(touchedCountryID, IDofCountryToFind)) {
                    //Get the gray mask of the selected country
                    Bitmap grayHighlight = null;
                    for (Country country : countries) {
                        if (country.getCountryID().equals(IDofCountryToFind)) {
                            grayHighlight = BitmapFactory.decodeResource(view.getResources(), country.getGrayMask());
                        }
                    }

                    //Combination of the gray mask of the guessed country with the current mask
                    actualHighlight = mergeToPin(actualHighlight, grayHighlight);

                    if (countries.size() > 1) {
                        //Delete a guessed country from the list of countries
                        countries.remove(countryToFind);

                        //Change of the number of remaining countries to guess
                        leftCountries.setText("" + countries.size());
                    } else {
                        //If the player has guessed all countries
                        //The finishBox appears and the player can move to the main menu
                        showFinishBox();
                    }
                }

                //Hide the temporary mask of the country
                countryMask.setVisibility(View.GONE);

                //Change the mask visible to the player
                europeMap.setImageBitmap(actualHighlight);

                //Get a new country to guess
                countryToFindView.setText(randomCountry());
            }
        });

        //Get the button that the player can go to the main menu when the game is over
        goToMenuButton = findViewById(R.id.go_to_menu_button);

        goToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new {@link Intent} object to move to the main menu
                Intent selectGameIntent = new Intent(MapGameActivity.this, MenuActivity.class);

                //Start a new activity
                startActivity(selectGameIntent);
            }
        });

    }//onCreate()

    /**
     * The function that returns a new bitmap created from two others, given as parameters.
     *
     * @param back      bitmap - background
     * @param front     bitmap - foreground
     * @return          new bitmap created from two others
     */
    public static Bitmap mergeToPin(Bitmap back, Bitmap front) {
        //Save bitmap (background) to a variable
        Bitmap result = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());

        //Creating a {@link Canvas} object with a bitmap-background
        Canvas canvas = new Canvas(result);

        //Get the width of both bitmaps
        int widthBack = back.getWidth();
        int widthFront = front.getWidth();

        //Draw both bitmaps on the canvas
        float move = (widthBack - widthFront) / 2;
        canvas.drawBitmap(back, 0f, 0f, null);
        canvas.drawBitmap(front, move, move, null);

        return result;
    }//mergeToPin()

    /**
     * The function that causes the finishBox appearing on the screen.
     */
    private void showFinishBox() {
        //Get the finishBox view
        finishBox = findViewById(R.id.finish_box);

        //Make the finishBox visible
        finishBox.setVisibility(View.VISIBLE);
    }//showFinishBox()


    /**
     * The function that checks whether the country selected by the player is correct.
     *
     * @param touchedID ID of the country selected by the player
     * @param IDToFind  ID of the country to guess
     * @return          true/false
     */
    private boolean isSelectedCountryCorrect(String touchedID, String IDToFind) {
        return touchedID.equals(IDToFind);
    }//isSelectedCountryCorrect()

    /**
     * The function that returns a list of European countries.
     *
     * @return  list of countries
     */
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
    }//createCountryList()

    /**
     * The function that draws the country from the list of countries.
     *
     * @return  name of the country
     */
    private String randomCountry() {
        String countryName = "";
        int max = countries.size();
        int randomIndex = (int) (Math.random() * max);
        countryToFind = countries.get(randomIndex);

        IDofCountryToFind = countryToFind.getCountryID();
        countryName = countryToFind.getCountryName();
        setCountryInfo(countryToFind);

        return countryName;
    }//randomCountry()

    /**
     * The function responsible for changing the mask depending on the selected country.
     *
     * @param countryID     ID of the selected country
     */
    public void changeHighlight(String countryID) {
        highlight = findViewById(R.id.country_mask);
        Bitmap newHighlight = actualHighlight;
        confirmButton = findViewById(R.id.confirm_button);

        //Searching for country highlight mask by country ID
        for (Country country : countries) {
            if (country.getCountryID().equals(countryID)) {
                //Set the selected country's highlight mask as a new highlight mask
                newHighlight = BitmapFactory.decodeResource(getResources(), country.getHighlightMask());
                confirmButton.setVisibility(View.VISIBLE);
                break;
            }
        }

        if (newHighlight == actualHighlight) {
            confirmButton.setVisibility(View.GONE);
        }

        highlight.setImageBitmap(newHighlight);
        highlight.setVisibility(View.VISIBLE);
    }//changeHighlight()

    /**
     * The function that sets information about the selected country in the answerBox.
     *
     * @param country   the country selected by the player
     */
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
    }//setCountryInfo()
}//end of the MapGameActivity class
