package com.ultime5528.betabots2019.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * Constants
 */
public class Constants {

    public static class Ports {
        // CAN
        public static final int MOTEUR_NORD = 1;
        public static final int MOTEUR_SUD = 2;
        public static final int MOTEUR_EST = 3;
        public static final int MOTEUR_OUEST = 4;

        // PWM
        public static final int MOTEUR_EJECTEUR = 0;

    }

    public static class Drive {
        public static final Translation2d CENTRE_ROTATION = new Translation2d(0, 0.466); //0.302 + 0.164);

        public static final double MIN_DEADZONE = 0.2;

        public static final double MAX_SPEED_METRES_PAR_SEC = 2.5; // Mesuré: 2.5 m/s
        public static final double MAX_ACCEL_SPEED_RAD_PAR_SEC2 = 13.5; // Mesuré: 13.5 m/s^2

        public static final double MAX_TURN_RAD_PAR_SEC = 3.3; // Mesuré: 10.4 rad/s
        public static final double MAX_ACCEL_TURN_RAD_PAR_SEC2 = 45.4; // Mesuré: 45.4 rad/s^2

        public static double P = 1e-5; //5e-5;
        public static double I = 1e-5; //1e-6;
        public static double D = 0; //0.0;
        public static double FF = 0.38;

        public static final double WHEEL_DIAMETER = 4 * 0.0254; // 4 po -> m
        public static final double GEARBOX_RATIO = 1 / 10.75;
        public static final double POSITION_CONVERSION_FACTOR = GEARBOX_RATIO * WHEEL_DIAMETER * Math.PI;
        public static final double VELOCITY_CONVERSION_FACTOR = POSITION_CONVERSION_FACTOR / 60; // RPM -­> m/s

        public static final Translation2d ROUES_POSITIONS_NORD = new Translation2d(0, 0.255);
        public static final Translation2d ROUES_POSITIONS_SUD = new Translation2d(0, -0.255);
        public static final Translation2d ROUES_POSITIONS_EST = new Translation2d(0.250, 0);
        public static final Translation2d ROUES_POSITIONS_OUEST = new Translation2d(-0.250, 0); // TODO checker la
                                                                                                // translation

        public static final double TOURNER_ANGLE_THRESHOLD = 0.06;

        public static final double AVANCER_DIST_THRESHOLD = 0.01;

        public static final double PILOTER_DIFF_THRESHOLD = 10;

    }
    public static class Ejecteur{

        public static final double VITESSE_EJECTION = -0.2;
        public static final double VITESSE_REVENIR = 0.2;

        public static final double FORCE_MAINTIEN = 0.0;

        public static final double TEMPS_POUSSER = 0.5;
        public static final double TEMPS_ATTENTE = 1.0;
        public static final double TEMPS_REVENIR = 1;
        
    }
}
