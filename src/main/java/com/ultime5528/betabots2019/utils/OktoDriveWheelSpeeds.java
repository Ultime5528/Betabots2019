/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.utils;

import java.util.stream.DoubleStream;

public class OktoDriveWheelSpeeds {
  public double frontLeftMetersPerSecond;

  public double frontRightMetersPerSecond;

  public double rearLeftMetersPerSecond;

  public double rearRightMetersPerSecond;

  public OktoDriveWheelSpeeds() {
  }

  public OktoDriveWheelSpeeds(double frontLeftMetersPerSecond,
                                 double frontRightMetersPerSecond,
                                 double rearLeftMetersPerSecond,
                                 double rearRightMetersPerSecond) {
    this.frontLeftMetersPerSecond = frontLeftMetersPerSecond;
    this.frontRightMetersPerSecond = frontRightMetersPerSecond;
    this.rearLeftMetersPerSecond = rearLeftMetersPerSecond;
    this.rearRightMetersPerSecond = rearRightMetersPerSecond;
  }

  public void normalize(double attainableMaxSpeedMetersPerSecond) {
    double realMaxSpeed = DoubleStream.of(frontLeftMetersPerSecond,
        frontRightMetersPerSecond, rearLeftMetersPerSecond, rearRightMetersPerSecond)
        .max().getAsDouble();

    if (realMaxSpeed > attainableMaxSpeedMetersPerSecond) {
      frontLeftMetersPerSecond = frontLeftMetersPerSecond / realMaxSpeed
          * attainableMaxSpeedMetersPerSecond;
      frontRightMetersPerSecond = frontRightMetersPerSecond / realMaxSpeed
          * attainableMaxSpeedMetersPerSecond;
      rearLeftMetersPerSecond = rearLeftMetersPerSecond / realMaxSpeed
          * attainableMaxSpeedMetersPerSecond;
      rearRightMetersPerSecond = rearRightMetersPerSecond / realMaxSpeed
          * attainableMaxSpeedMetersPerSecond;
    }
  }
}