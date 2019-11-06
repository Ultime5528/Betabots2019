/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ultime5528.betabots2019.robot.Constants;
import com.ultime5528.betabots2019.utils.ChassisSpeeds;
import com.ultime5528.betabots2019.utils.OktoDriveKinematics;
import com.ultime5528.betabots2019.utils.OktoDriveOdometry;
import com.ultime5528.betabots2019.utils.OktoDriveWheelSpeeds;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BasePilotableOkto extends SubsystemBase {

  private CANSparkMax moteurNord, moteurSud, moteurEst, moteurOuest;
  private CANEncoder encodeurNord, encodeurSud, encodeurEst, encodeurOuest;
  private CANPIDController moteurNordPID, moteurSudPID, moteurEstPID, moteurOuestPID;

  private ADXRS450_Gyro gyro;

  private OktoDriveKinematics kinematics;
  private OktoDriveOdometry odometry;

  private Pose2d pose;

  public BasePilotableOkto() {
    moteurNord = new CANSparkMax(1, MotorType.kBrushless);
    moteurSud = new CANSparkMax(2, MotorType.kBrushless);
    moteurEst = new CANSparkMax(3, MotorType.kBrushless);
    moteurOuest = new CANSparkMax(4, MotorType.kBrushless);

    encodeurNord = moteurNord.getEncoder();
    encodeurSud = moteurSud.getEncoder();
    encodeurEst = moteurEst.getEncoder();
    encodeurOuest = moteurOuest.getEncoder();

    moteurNordPID = moteurNord.getPIDController();
    moteurSudPID = moteurSud.getPIDController();
    moteurEstPID = moteurEst.getPIDController();
    moteurOuestPID = moteurOuest.getPIDController();

    gyro = new ADXRS450_Gyro();
    // TODO changer gyro + calibrer

    kinematics = new OktoDriveKinematics(Constants.Drive.ROUES_POSITIONS_NORD, Constants.Drive.ROUES_POSITIONS_SUD,
        Constants.Drive.ROUES_POSITIONS_EST, Constants.Drive.ROUES_POSITIONS_OUEST);

    odometry = new OktoDriveOdometry(kinematics);
  }

  /**
   * Fais tourner les moteurs de cette base pilotable en suivant le mouvement
   * passé en paramètre.
   * 
   * @param forwardSpeed Vitesse en m/s vers l'avant (valeur postive) ou vers
   *                     l'arrière (valeur négative).
   * @param sideSpeed    Vitesse en m/s vers la droite (valeur postive) ou vers la
   *                     gauche (valeur négative).
   * @param turn         Vitesse de tour en rad/s autour du centre de rotation
   *                     défini dans {@link Constants} vers la droite (valeur
   *                     positive) ou vers la gauche (valeur négative).
   */
  public void oktoDrive(double forwardSpeed, double sideSpeed, double turn) {
    OktoDriveWheelSpeeds speeds = kinematics.toWheelSpeeds(new ChassisSpeeds(forwardSpeed, sideSpeed, turn),
        Constants.Drive.CENTRE_ROTATION);

    moteurNordPID.setReference(speeds.northMetersPerSecond, ControlType.kVelocity);
    moteurSudPID.setReference(speeds.southMetersPerSecond, ControlType.kVelocity);
    moteurEstPID.setReference(speeds.eastMetersPerSecond, ControlType.kVelocity);
    moteurOuestPID.setReference(speeds.westMetersPerSecond, ControlType.kVelocity);
  }

  @Override
  public void periodic() {
    OktoDriveWheelSpeeds wheelSpeeds = new OktoDriveWheelSpeeds(encodeurNord.getVelocity(), encodeurSud.getVelocity(),
    encodeurEst.getVelocity(), encodeurOuest.getVelocity());

    var angle = Rotation2d.fromDegrees(-getAngle());

    // Update the pose
    pose = odometry.update(angle, wheelSpeeds);
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public double getVitesseGyro() {
    return gyro.getRate();
  }

  public Translation2d getTranslation() {
    return pose.getTranslation();
  }

  public Rotation2d getRotation() {
    return pose.getRotation();
  }

  public void stop() {
    moteurNord.set(0);
    moteurSud.set(0);
    moteurEst.set(0);
    moteurOuest.set(0);
  }
}
