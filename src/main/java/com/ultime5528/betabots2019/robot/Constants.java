package com.ultime5528.betabots2019.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * Constants
 */
public class Constants {

    public static class Ports { 
    }


    public static class Drive {
        public static final Translation2d CENTRE_ROTATION = new Translation2d(0, 0.30575 + 0.2286);

        public static final Translation2d ROUES_POSITIONS_NORD = new Translation2d(0, 250);
        public static final Translation2d ROUES_POSITIONS_SUD = new Translation2d(0, -250);
        public static final Translation2d ROUES_POSITIONS_EST = new Translation2d(250, 0);
        public static final Translation2d ROUES_POSITIONS_OUEST = new Translation2d(-250, 0);

        public static final double MAX_SPEED_METRES_PAR_SEC = 2;
        public static final double MAX_TURN_RAD_PAR_SEC = Math.PI / 2;

        public static final double MAX_ACCEL_TURN_RAD_PAR_SEC2 = 0;

        public static final double ANGLE_THRESHOLD = 0.06;
    }
}