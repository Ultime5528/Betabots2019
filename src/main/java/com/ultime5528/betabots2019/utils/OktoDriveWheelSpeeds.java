/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.utils;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

public class OktoDriveWheelSpeeds {
    public double northMetersPerSecond;

    public double southMetersPerSecond;

    public double eastMetersPerSecond;

    public double westMetersPerSecond;

    public OktoDriveWheelSpeeds() {
    }

    public OktoDriveWheelSpeeds(double northMetersPerSecond, double southMetersPerSecond, double eastMetersPerSecond,
            double westMetersPerSecond) {
        this.northMetersPerSecond = northMetersPerSecond;
        this.southMetersPerSecond = southMetersPerSecond;
        this.eastMetersPerSecond = eastMetersPerSecond;
        this.westMetersPerSecond = westMetersPerSecond;
    }

    public void normalize(double attainableMaxSpeedMetersPerSecond) {
        double realMaxSpeed = DoubleStream
                .of(northMetersPerSecond, southMetersPerSecond, eastMetersPerSecond, westMetersPerSecond)
                .map(Math::abs)
                .max()
                .getAsDouble();

        if (realMaxSpeed > attainableMaxSpeedMetersPerSecond) {
            northMetersPerSecond = northMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            southMetersPerSecond = southMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            eastMetersPerSecond = eastMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            westMetersPerSecond = westMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
        }
    }

    @Override
    public String toString() {
        return "OktoDriveWheelSpeeds[North=" + northMetersPerSecond + ",South=" + southMetersPerSecond + ",East="
                + eastMetersPerSecond + ",West=" + westMetersPerSecond + "]";
    }
}