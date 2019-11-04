/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Twist2d;

public class OktoDriveOdometry {
  private final OktoDriveKinematics m_kinematics;
  private Pose2d m_poseMeters;
  private double m_prevTimeSeconds = -1;

  private Rotation2d m_previousAngle;

  public OktoDriveOdometry(OktoDriveKinematics kinematics, Pose2d initialPoseMeters) {
    m_kinematics = kinematics;
    m_poseMeters = initialPoseMeters;
    m_previousAngle = initialPoseMeters.getRotation();
  }

  public OktoDriveOdometry(OktoDriveKinematics kinematics) {
    this(kinematics, new Pose2d());
  }

  public void resetPosition(Pose2d poseMeters) {
    m_poseMeters = poseMeters;
    m_previousAngle = poseMeters.getRotation();
  }

  public Pose2d getPoseMeters() {
    return m_poseMeters;
  }

  public Pose2d updateWithTime(double currentTimeSeconds, Rotation2d angle,
                               OktoDriveWheelSpeeds wheelSpeeds) {
    double period = m_prevTimeSeconds >= 0 ? currentTimeSeconds - m_prevTimeSeconds : 0.0;
    m_prevTimeSeconds = currentTimeSeconds;

    var chassisState = m_kinematics.toChassisSpeeds(wheelSpeeds);
    var newPose = m_poseMeters.exp(
        new Twist2d(chassisState.vxMetersPerSecond * period,
            chassisState.vyMetersPerSecond * period,
            angle.minus(m_previousAngle).getRadians()));

    m_previousAngle = angle;
    m_poseMeters = new Pose2d(newPose.getTranslation(), angle);
    return m_poseMeters;
  }

  public Pose2d update(Rotation2d angle,
                       OktoDriveWheelSpeeds wheelSpeeds) {
    return updateWithTime(Timer.getFPGATimestamp(), angle,
        wheelSpeeds);
  }
}