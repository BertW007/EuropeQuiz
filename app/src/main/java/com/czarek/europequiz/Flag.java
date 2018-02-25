package com.czarek.europequiz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * {@link Flag} represents the flag that appears in {@link FlagGameActivity}
 *
 * @author Czarek Pietrzak
 */
public class Flag {

    /**
     * The horizontal position of the flag, counting from the left.
     */
    private float x;

    /**
     * The vertical position of the flag, counting from the top.
     */
    private float y;

    /**
     * The width of the output image of the flag.
     */
    private float width;

    /**
     * The height of the output image of the flag.
     */
    private float height;

    /**
     * The bitmap of the processed graphic resource.
     */
    private Bitmap flagBitmap;

    /**
     * Speed of falling of the flag.
     */
    private float flagSpeed;

    /**
     * Creates a Flag object.
     *
     * @param drawableResource ID of graphic source - country flag
     * @param screenWidth      width of the screen to calculate the horizontal position of the flag
     * @param context          context - the activity where the Flag object is created, the parameter needed to process the graphic source
     */
    public Flag(int drawableResource, int screenWidth, int index, Context context) {
        //Setting the width and height of the graphic output
        width = 100;
        height = 100;

        //Converting a Drawable source to Bitmap
        flagBitmap = BitmapFactory.decodeResource(context.getResources(), drawableResource);
        flagBitmap = Bitmap.createScaledBitmap(flagBitmap, (int) width, (int) height, true);

        //Setting the initial position of the object
        x = index * (screenWidth / 3) + 30;
        y = (float) -(Math.random() * 600 + height);

        //Setting the speed of the flag falling
        flagSpeed = 350;
    }

    /**
     * Gets the bitmap of the processed graphic resource.
     *
     * @return image of a flag in the form of a bitmap
     */
    public Bitmap getFlagBitmap() {
        return flagBitmap;
    }//getFlagBitmap()

    /**
     * Gets horizontal position of the flag.
     *
     * @return horizontal position of the flag
     */
    public float getX() {
        return x;
    }//getX()

    /**
     * Gets vertical position of the flag.
     *
     * @return vertical position of the flag
     */
    public float getY() {
        return y;
    }//getY()

    /**
     * Changes the position of the flag.
     * The function called in update() method in {@link FlagGameActivity}
     */
    public void update(long fps) {
        y = y + flagSpeed / fps;
    }//update()
}//end of the Flag class
