/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.utils;


import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class ChassisSpeeds {
  public double vxMetersPerSecond;

  public double vyMetersPerSecond;

  public double omegaRadiansPerSecond;

  public ChassisSpeeds() {
  }

  public ChassisSpeeds(double vxMetersPerSecond, double vyMetersPerSecond,
                       double omegaRadiansPerSecond) {
    this.vxMetersPerSecond = vxMetersPerSecond;
    this.vyMetersPerSecond = vyMetersPerSecond;
    this.omegaRadiansPerSecond = omegaRadiansPerSecond;
  }

  public static ChassisSpeeds fromFieldRelativeSpeeds(
      double vxMetersPerSecond, double vyMetersPerSecond,
      double omegaRadiansPerSecond, Rotation2d robotAngle) {
    return new ChassisSpeeds(
        vxMetersPerSecond * robotAngle.getCos() + vyMetersPerSecond * robotAngle.getSin(),
        -vxMetersPerSecond * robotAngle.getSin() + vyMetersPerSecond * robotAngle.getCos(),
        omegaRadiansPerSecond
    );
  }
}