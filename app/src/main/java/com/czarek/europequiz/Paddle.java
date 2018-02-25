package com.czarek.europequiz;

import android.graphics.RectF;

/**
 * {@link Paddle} represents platform used in {@link FlagGameActivity}
 *
 * @author Czarek Pietrzak
 */
class Paddle {
    /**
     * An object that stores four platform parameters.
     */
    private RectF rect;

    /**
     * The platform length in pixels.
     */
    float length;

    /**
     * The platform height in pixels.
     */
    private float height;

    /**
     * The horizontal position of the platform in pixels counting from the left.
     */
    private float x;

    /**
     * The vertical position of the platform in pixels counting from the top.
     */
    private float y;

    /**
     * The speed of the platform in pixels per second.
     */
    private float paddleSpeed;

    // Possible platform states:

    /**
     * The state in which the platform is not moving.
     */
    final int STOPPED = 0;

    /**
     * The state in which the platform is moving to the left.
     */
    final int LEFT = 1;

    /**
     * The state in which the platform is moving to the right.
     */
    final int RIGHT = 2;

    /**
     * The platform's state of motion.
     */
    private int paddleMoving = STOPPED;

    /**
     * Creates a Paddle object.
     *
     * @param screenX device screen width
     * @param screenY device screen height
     */
    Paddle(int screenX, int screenY) {
        //Set width and height of the paddle
        length = screenX / 5;
        height = screenY / 30;

        //The initial position of the paddle
        x = screenX / 2 - length / 2;
        y = screenY - 100;

        //Save the paddle parameters in {@link RectF} object
        rect = new RectF(x, y, x + length, y + height);

        //Set speed of the paddle
        paddleSpeed = screenX;
    }

    /**
     * Gets an object that stores four platform parameters.
     *
     * @return an object that stores four platform parameters
     */
    RectF getRect() {
        return rect;
    }//getRect()

    /**
     * Sets the state of motion platform.
     *
     * @param state one of the states of motion platform: STOPPED, LEFT, RIGHT
     */
    void setMovementState(int state) {
        paddleMoving = state;
    }//setMovementState()

    /**
     * Changes the position of the platform, depending on the platform state.
     * The function called in the update () method in {@link FlagGameActivity}
     */
    void update(long fps, int screenWidth) {
        if (paddleMoving == LEFT && x >= 0) {
            x = x - paddleSpeed / fps;
        }

        if (paddleMoving == RIGHT && x <= screenWidth - this.length) {
            x = x + paddleSpeed / fps;
        }

        rect.left = x;
        rect.right = x + length;
    }//update()
}//end of the Paddle class
