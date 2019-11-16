package com.ultime5528.betabots2019.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * Constants
 */
public class Constants {

    public static class Ports { 
    }


    public static class Drive {
        public static final Translation2d CENTRE_ROTATION = new Translation2d(0, 0.302 + 0.164);

        public static final double P = 5e-5;
        public static final double I = 1e-6;
        public static final double D = 0.0;
        public static final double FF = 1;

        public static final double WHEEL_DIAMETER = 4 * 0.0254; //4 po -> m
        public static final double GEARBOX_RATIO = 1 / 10.75;
        public static final double ENCODER_CONVERSION_FACTOR = GEARBOX_RATIO * WHEEL_DIAMETER * Math.PI;
        
        public static final Translation2d ROUES_POSITIONS_NORD = new Translation2d(0, 0.255);
        public static final Translation2d ROUES_POSITIONS_SUD = new Translation2d(0, -0.255);
        public static final Translation2d ROUES_POSITIONS_EST = new Translation2d(0.250, 0);
        public static final Translation2d ROUES_POSITIONS_OUEST = new Translation2d(-0.250, 0); // TODO checker la translation

        public static final double MAX_SPEED_METRES_PAR_SEC = 2;
        public static final double MAX_ACCEL_SPEED_RAD_PAR_SEC2 = 0; // TODO set constant

        public static final double MAX_TURN_RAD_PAR_SEC = Math.PI / 2;
        public static final double MAX_ACCEL_TURN_RAD_PAR_SEC2 = 0; // TODO set constant

        public static final double TOURNER_ANGLE_THRESHOLD = 0.06;

        public static final double AVANCER_DIST_THRESHOLD = 0.01;

        public static final double PILOTER_DIFF_THRESHOLD = 0.05;

    }
}
