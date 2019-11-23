/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by southC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.utils;

import org.ejml.simple.SimpleMatrix;

import edu.wpi.first.wpilibj.geometry.Translation2d;

public class OktoDriveKinematics {
  private SimpleMatrix m_inverseKinematics;
  private final SimpleMatrix m_forwardKinematics;

  private final Translation2d m_northWheelMeters;
  private final Translation2d m_southWheelMeters;
  private final Translation2d m_eastWheelMeters;
  private final Translation2d m_westWheelMeters;

  private Translation2d m_prevCoR = new Translation2d();

  public OktoDriveKinematics(Translation2d northWheelMeters, Translation2d southWheelMeters,
      Translation2d eastWheelMeters, Translation2d westWheelMeters) {
    m_northWheelMeters = northWheelMeters;
    m_southWheelMeters = southWheelMeters;
    m_eastWheelMeters = eastWheelMeters;
    m_westWheelMeters = westWheelMeters;

    m_inverseKinematics = new SimpleMatrix(4, 3);

    setInverseKinematics(northWheelMeters, southWheelMeters, eastWheelMeters, westWheelMeters);
    m_forwardKinematics = m_inverseKinematics.pseudoInverse();
  }

  public OktoDriveWheelSpeeds toWheelSpeeds(ChassisSpeeds chassisSpeeds, Translation2d centerOfRotationMeters) {
    // We have a new center of rotation. We need to compute the matrix again.
    if (!centerOfRotationMeters.equals(m_prevCoR)) {
      var north = m_northWheelMeters.minus(centerOfRotationMeters);
      var south = m_southWheelMeters.minus(centerOfRotationMeters);
      var east = m_eastWheelMeters.minus(centerOfRotationMeters);
      var west = m_westWheelMeters.minus(centerOfRotationMeters);

      setInverseKinematics(north, south, east, west);
      m_prevCoR = centerOfRotationMeters;
    }

    var chassisSpeedsVector = new SimpleMatrix(3, 1);
    chassisSpeedsVector.setColumn(0, 0, chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond,
        chassisSpeeds.omegaRadiansPerSecond);

    var wheelsMatrix = m_inverseKinematics.mult(chassisSpeedsVector);
    return new OktoDriveWheelSpeeds(wheelsMatrix.get(0, 0), wheelsMatrix.get(1, 0), wheelsMatrix.get(2, 0),
        wheelsMatrix.get(3, 0));
  }

  public OktoDriveWheelSpeeds toWheelSpeeds(ChassisSpeeds chassisSpeeds) {
    return toWheelSpeeds(chassisSpeeds, new Translation2d());
  }

  public ChassisSpeeds toChassisSpeeds(OktoDriveWheelSpeeds wheelSpeeds) {
    var wheelSpeedsMatrix = new SimpleMatrix(4, 1);
    wheelSpeedsMatrix.setColumn(0, 0, wheelSpeeds.northMetersPerSecond, wheelSpeeds.southMetersPerSecond,
        wheelSpeeds.eastMetersPerSecond, wheelSpeeds.westMetersPerSecond);
    var chassisSpeedsVector = m_forwardKinematics.mult(wheelSpeedsMatrix);

    return new ChassisSpeeds(chassisSpeedsVector.get(0, 0), chassisSpeedsVector.get(1, 0),
        chassisSpeedsVector.get(2, 0));
  }

  private void setInverseKinematics(Translation2d north, Translation2d south, Translation2d east, Translation2d west) {
    m_inverseKinematics.setRow(0, 0, 1, 0, north.getY());
    m_inverseKinematics.setRow(1, 0, 1, 0, south.getY());
    m_inverseKinematics.setRow(2, 0, 0, 1, -east.getX());
    m_inverseKinematics.setRow(3, 0, 0, 1, -west.getX());
  }
}