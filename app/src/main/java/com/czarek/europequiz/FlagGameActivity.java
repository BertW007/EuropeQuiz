package com.czarek.europequiz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link FlagGameActivity} - activity with an arcade game.
 *
 * @author Czarek Pietrzak
 */
public class FlagGameActivity extends AppCompatActivity {

    /**
     * The view of the game, responsible for the logic of the game. Responds to the touch of the screen.
     */
    FlagGameView flagGameView;

    /**
     * List of all countries.
     */
    ArrayList<Country> countries = createCountryList();

    /**
     * List of countries whose flags are displayed on the screen during the game.
     */
    ArrayList<Country> fallingCountries;

    /**
     * List of flags that are displayed on the screen.
     */
    ArrayList<Flag> flags;

    /**
     * The country whose flag the player must catch.
     */
    Country countryToCatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialization of the game view
        flagGameView = new FlagGameView(this);
        setContentView(flagGameView);
    }//onCreate()

    /**
     * This function is called when the player starts the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        flagGameView.resume();
    }//onResume()

    /**
     * The function is called when the player pauses the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        flagGameView.pause();
    }//onPause()

    /**
     * {@link FlagGameView} creates a game view.
     */
    class FlagGameView extends SurfaceView implements Runnable {

        /**
         * Introduced for the correct {@link Runnable} working.
         */
        Thread gameThread = null;

        /**
         * Needed to use Paint and Canvas in gameThread.
         */
        SurfaceHolder holder;

        /**
         * The Canvas object on which the game will be drawn.
         */
        Canvas canvas;

        /**
         * The Paint object that will be used to draw game objects.
         */
        Paint paint;

        /**
         * Indicates whether the game is played.
         */
        boolean playing;

        /**
         * Indicates whether the game is in pause mode.
         */
        boolean paused = true;

        /**
         * The number of frames per second in the game.
         */
        long fps;

        /**
         * The width of the screen in pixels.
         */
        int screenX;

        /**
         * The height of the screen in pixels.
         */
        int screenY;

        /**
         * Number of lives in the game.
         */
        int lives = 3;

        /**
         * Left flags to catch.
         */
        int leftFlags = countries.size();

        /**
         * A platform that will "catch" flags falling from above.
         */
        Paddle paddle;

        /**
         * Used to help calculate frames per second.
         */
        private long timeThisFrame;

        private boolean firstFlagCaught = false;
        private boolean secondFlagCaught = false;
        private boolean thirdFlagCaught = false;

        /**
         * Creates a game view.
         *
         * @param context   context in which the view will be created
         */
        public FlagGameView(Context context) {
            super(context);

            //Initialization of SurfaceHolder and Paint objects
            holder = getHolder();
            paint = new Paint();

            //Get the {@link Display} object to get the screen resolution
            Display display = getWindowManager().getDefaultDisplay();

            //Store the screen resolution in the {@link Point} object
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            //Creating a platform
            paddle = new Paddle(screenX, screenY);

            //Creating an empty list of all flags
            flags = new ArrayList<>();

            //Creating an empty list of falling flags
            fallingCountries = new ArrayList<>();

            //Creating a temporary list of flags' IDs
            ArrayList<Integer> tempFlags = new ArrayList<>();

            //Get three random countries from list
            Country rndCountry1 = randomCountry();
            fallingCountries.add(rndCountry1);
            tempFlags.add(rndCountry1.getFlag());
            countries.remove(rndCountry1);

            Country rndCountry2 = randomCountry();
            fallingCountries.add(rndCountry2);
            tempFlags.add(rndCountry2.getFlag());
            countries.remove(rndCountry2);

            Country rndCountry3 = randomCountry();
            fallingCountries.add(rndCountry3);
            tempFlags.add(rndCountry3.getFlag());
            countries.remove(rndCountry3);

            countries = createCountryList();

            //Get random country to catch
            int max = fallingCountries.size();
            int randomIndex = (int) (Math.random() * max);
            countryToCatch = fallingCountries.get(randomIndex);

            //Save country flags for display
            for (Integer flagDrawable : tempFlags) {
                flags.add(new Flag(flagDrawable, screenX, tempFlags.indexOf(flagDrawable), getContext()));
            }

            restartGame();
        }

        /**
         * The function that sets the default game parameters in case of losing game.
         */
        public void restartGame() {
            if (lives == 0) {
                countries = createCountryList();
                leftFlags = countries.size();
                lives = 3;
            }
        }//restartGame()

        @Override
        public void run() {
            while (playing) {
                //Saving the current time in milliseconds
                long startFrameTime = System.currentTimeMillis();

                //Update game view
                if (!paused) {
                    update();
                }

                //Draw game view
                draw();

                checkCollisions();

                //Calculating the speed of the generated frame
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }
            }
        }//run()

        /**
         * The method that checks whether the flag has come into contact with the platform.
         */
        private void checkCollisions() {
            //Horizontal coordinate of the platform center
            int paddleXPosition = (int) (paddle.getRect().left + paddle.length / 2);

            //Line at the platform height
            int paddleLine = screenY - 100;

            //Vertical positions of flags
            int firstFlagYPosition = (int) flags.get(0).getY();
            int secondFlagYPosition = (int) flags.get(1).getY();
            int thirdFlagYPosition = (int) flags.get(2).getY();

            //Checking the collision conditions of objects
            if (paddleXPosition <= 30 + screenX / 3) {
                if (firstFlagYPosition == paddleLine) {
                    firstFlagCaught = true;
                    pause();
                }
            } else if ((paddleXPosition >= 30 + screenX / 3) && (paddleXPosition <= 30 + 2 * screenX / 3)) {
                if (secondFlagYPosition == paddleLine) {
                    secondFlagCaught = true;
                    pause();
                }
            } else if (paddleXPosition >= 30 + 2 * screenX / 3) {
                if (thirdFlagYPosition == paddleLine) {
                    thirdFlagCaught = true;
                    pause();
                }
            }
        }//checkCollisions()

        /**
         * The function responsible for updating the position of game objects.
         * Changes occur on the basis of elements' movement and their possible collisions.
         */
        public void update() {
            //Moving the platform
            paddle.update(fps, screenX);

            for (Flag flag : flags) {
                //Falling flags
                flag.update(fps);
            }
        }//update()

        /**
         * The function that draws a new, updated view.
         */
        public void draw() {
            if (holder.getSurface().isValid()) {
                //Preparing the canvas for drawing
                canvas = holder.lockCanvas();

                //Drawing a background
                canvas.drawColor(Color.argb(255, 29, 130, 109));

                //If the game is paused, a message with information is displayed
                if (paused) {
                    String message;
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(22.0f);
                    Typeface plain = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
                    Typeface bold = Typeface.create(plain, Typeface.BOLD);
                    paint.setTypeface(bold);
                    paint.setTextAlign(Paint.Align.CENTER);

                    //If a flag has been caught, an appropriate message is displayed
                    if (firstFlagCaught) {
                        message = "You caught flag of " + fallingCountries.get(0).getCountryName() + "!";
                        message = message.toUpperCase();
                        canvas.drawText(message, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                    } else if (secondFlagCaught) {
                        message = "You caught flag of " + fallingCountries.get(1).getCountryName() + "!";
                        message = message.toUpperCase();
                        canvas.drawText(message, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                    } else if (thirdFlagCaught) {
                        message = "You caught flag of " + fallingCountries.get(2).getCountryName() + "!";
                        message = message.toUpperCase();
                        canvas.drawText(message, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                    } else {
                        message = "Touch left side to move left";
                        message = message.toUpperCase();
                        canvas.drawText(message, canvas.getWidth() / 2, canvas.getHeight() / 2 - 25, paint);

                        message = "Touch right side to move right";
                        message = message.toUpperCase();
                        canvas.drawText(message, canvas.getWidth() / 2, canvas.getHeight() / 2 + 25, paint);
                    }
                }

                //Drawing flags
                for (Flag flag : flags) {
                    canvas.drawBitmap(flag.getFlagBitmap(), flag.getX(), flag.getY(), paint);
                }

                //Drawing a platform
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawRect(paddle.getRect(), paint);

                //Drawing {@link TextView} with the name of the country whose flag user should catch
                LinearLayout layout = new LinearLayout(getContext());
                layout.setBackgroundColor(Color.rgb(153, 204, 0));
                layout.setGravity(Gravity.CENTER);
                layout.setPadding(8, 8, 8, 8);

                TextView textView = new TextView(getContext());
                textView.setVisibility(View.VISIBLE);
                textView.setTextColor(Color.WHITE);
                textView.setAllCaps(true);
                textView.setTextSize(22.0f);
                textView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL), Typeface.BOLD);
                textView.setShadowLayer(2.0f, 2.2f, 2.2f, Color.rgb(102, 153, 0));
                textView.setText(String.format("Catch: %s", countryToCatch.getCountryName()));
                layout.addView(textView);

                layout.measure(canvas.getWidth(), canvas.getHeight() / 10);
                layout.layout(0, 0, canvas.getWidth(), canvas.getHeight() / 10);

                layout.draw(canvas);

                //Draw everything on the screen
                holder.unlockCanvasAndPost(canvas);
            }
        }//draw()

        /**
         * The function pauses the game and ends gameThread.
         */
        public void pause() {
            paused = true;
            playing = false;

            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }//pause()

        /**
         * The function responsible for running gameThread when the game is on.
         */
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }//resume()

        /**
         * Overwriting the onTouchEvent method that supports the touch and moving the platform.
         * Generally the screen is divided in half.
         * If the player touches the left side of the screen, the platform will move to the left.
         * Similarly for the right side, after touching it, the platform will move to the right.
         */
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                //The player touched the screen
                case MotionEvent.ACTION_DOWN:
                    paused = false;
                    // Setting the platform status depending on which side of the screen the player touched
                    if (motionEvent.getX() > screenX / 2) {
                        paddle.setMovementState(paddle.RIGHT);
                    } else {
                        paddle.setMovementState(paddle.LEFT);
                    }
                    break;
                //The player released his finger from the screen
                case MotionEvent.ACTION_UP:
                    //Stopping the platform
                    paddle.setMovementState(paddle.STOPPED);
                    break;
            }
            return true;
        }//onTouchEvent()
    }//end of the FlagGameView class

    /**
     * The function that returns a list of European countries.
     *
     * @return  list of countries
     */
    public ArrayList<Country> createCountryList() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("Albania", R.drawable.flag_albania));
        countries.add(new Country("Austria", R.drawable.flag_austria));
        countries.add(new Country("Belgium", R.drawable.flag_belgium));
        countries.add(new Country("Belarus", R.drawable.flag_belarus));
        countries.add(new Country("Bosnia and Herzegovina", R.drawable.flag_bosnia));
        countries.add(new Country("Bulgaria", R.drawable.flag_bulgaria));
        countries.add(new Country("Croatia", R.drawable.flag_croatia));
        countries.add(new Country("Montenegro", R.drawable.flag_montenegro));
        countries.add(new Country("Czech Republic", R.drawable.flag_czech_republic));

        countries.add(new Country("Denmark", R.drawable.flag_denmark));
        countries.add(new Country("Estonia", R.drawable.flag_estonia));
        countries.add(new Country("Finland", R.drawable.flag_finland));
        countries.add(new Country("France", R.drawable.flag_france));
        countries.add(new Country("Greece", R.drawable.flag_greece));
        countries.add(new Country("Spain", R.drawable.flag_spain));
        countries.add(new Country("Netherlands", R.drawable.flag_netherlands));
        countries.add(new Country("Ireland", R.drawable.flag_ireland));
        countries.add(new Country("Iceland", R.drawable.flag_iceland));
        countries.add(new Country("Lithuania", R.drawable.flag_lithuania));

        countries.add(new Country("Latvia", R.drawable.flag_latvia));
        countries.add(new Country("Macedonia", R.drawable.flag_macedonia));
        countries.add(new Country("Moldova", R.drawable.flag_moldova));
        countries.add(new Country("Germany", R.drawable.flag_germany));
        countries.add(new Country("Norway", R.drawable.flag_norway));
        countries.add(new Country("Poland", R.drawable.flag_poland));
        countries.add(new Country("Portugal", R.drawable.flag_portugal));
        countries.add(new Country("Russia", R.drawable.flag_russia));
        countries.add(new Country("Romania", R.drawable.flag_romania));
        countries.add(new Country("Serbia", R.drawable.flag_serbia));

        countries.add(new Country("Slovakia", R.drawable.flag_slovakia));
        countries.add(new Country("Slovenia", R.drawable.flag_slovenia));
        countries.add(new Country("Switzerland", R.drawable.flag_switzerland));
        countries.add(new Country("Sweden", R.drawable.flag_sweden));
        countries.add(new Country("Turkey", R.drawable.flag_turkey));
        countries.add(new Country("Ukraine", R.drawable.flag_ukraine));
        countries.add(new Country("Hungary", R.drawable.flag_hungary));
        countries.add(new Country("United Kingdom", R.drawable.flag_united_kingdom));
        countries.add(new Country("Italy", R.drawable.flag_italy));

        return countries;
    }//createCountryList()

    /**
     * The function that returns random country from the list of countries.
     *
     * @return {@link Country} object
     */
    private Country randomCountry() {
        int max = countries.size();
        int randomIndex = (int) (Math.random() * max);

        return countries.get(randomIndex);
    }//randomCountry()
}//end of the FlagGameActivity class
