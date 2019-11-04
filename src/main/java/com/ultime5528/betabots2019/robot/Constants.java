package com.ultime5528.betabots2019.robot;

import com.ultime5528.betabots2019.maths.Vector2d;

/**
 * Constants
 */
public class Constants {

    public static class Ports { 
    }


    public static class Drive {
        public static final double ORIGINE_ROTATION_Y = -305.75 - 228.6;

        public static final Vector2d ROUES_POSITIONS_NORD = new Vector2d(0, ORIGINE_ROTATION_Y + 250);
        public static final Vector2d ROUES_POSITIONS_SUD = new Vector2d(0, ORIGINE_ROTATION_Y - 250);
        public static final Vector2d ROUES_POSITIONS_EST = new Vector2d(250, ORIGINE_ROTATION_Y);
        public static final Vector2d ROUES_POSITIONS_OUEST = new Vector2d(-250, ORIGINE_ROTATION_Y);

        public static final double TURN_FACTOR = 1;
    }
}