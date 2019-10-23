package frc.robot;

import frc.maths.Vector2d;

/**
 * Constants
 */
public class Constants {

    public static class Ports { 
    }


    public static class Drive {
        public static final double ORIGINE_ROTATION_Y = 305.75 + 228.6;

        //N-S-E-O
        public static final Vector2d[] ROUES_POSITIONS = new Vector2d[] {
            new Vector2d(0, ORIGINE_ROTATION_Y + 250),
            new Vector2d(0, ORIGINE_ROTATION_Y - 250),
            new Vector2d(250, -ORIGINE_ROTATION_Y),
            new Vector2d(-250, -ORIGINE_ROTATION_Y)
        };

        public static final double TURN_FACTOR = 1;
    }
}