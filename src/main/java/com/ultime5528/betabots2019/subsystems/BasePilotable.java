/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ultime5528.betabots2019.robot.Constants;
import com.ultime5528.betabots2019.utils.ChassisSpeeds;
import com.ultime5528.betabots2019.utils.OktoDriveKinematics;
import com.ultime5528.betabots2019.utils.OktoDriveWheelSpeeds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class BasePilotable extends SubsystemBase {

  public CANSparkMax moteurNord, moteurSud, moteurEst, moteurOuest;
  public CANPIDController moteurNordPID, moteurSudPID, moteurEstPID, moteurOuestPID;

  public OktoDriveKinematics kinematics;

  public BasePilotable() {
    moteurNord = new CANSparkMax(0, MotorType.kBrushless);
    moteurSud = new CANSparkMax(1, MotorType.kBrushless);
    moteurEst = new CANSparkMax(2, MotorType.kBrushless);
    moteurOuest = new CANSparkMax(3, MotorType.kBrushless);

    moteurNordPID = moteurNord.getPIDController();
    moteurSudPID = moteurSud.getPIDController();
    moteurEstPID = moteurEst.getPIDController();
    moteurOuestPID = moteurOuest.getPIDController();

    kinematics = new OktoDriveKinematics(Constants.Drive.ROUES_POSITIONS_NORD, Constants.Drive.ROUES_POSITIONS_SUD,
        Constants.Drive.ROUES_POSITIONS_EST, Constants.Drive.ROUES_POSITIONS_OUEST);
  }

  public void oktoDrive(double x, double y, double turn) {
    // double rotation = turn * Constants.Drive.TURN_FACTOR;

    // Vector2d vecteurVitesse = new Vector2d(x, y);

    // double vitesseN = vecteurVitesse.getX() +
    // Constants.Drive.ROUES_POSITIONS_NORD.getY() * rotation;
    // double vitesseS = vecteurVitesse.getX() +
    // Constants.Drive.ROUES_POSITIONS_SUD.getY() * rotation;
    // double vitesseE = vecteurVitesse.getY() +
    // Constants.Drive.ROUES_POSITIONS_EST.getX() * rotation;
    // double vitesseO = vecteurVitesse.getY() +
    // Constants.Drive.ROUES_POSITIONS_OUEST.getX() * rotation;

    // double largest = MathUtils.absArgmax(vitesseN, vitesseS, vitesseE, vitesseO);

    // if(largest > 1){
    // vitesseN /= largest;
    // vitesseS /= largest;
    // vitesseE /= largest;
    // vitesseO /= largest;
    // }

    // moteurNord.set(vitesseN);
    // moteurSud.set(vitesseS);
    // moteurEst.set(vitesseE);
    // moteurOuest.set(vitesseO);

    OktoDriveWheelSpeeds speeds = kinematics.toWheelSpeeds(new ChassisSpeeds(x, y, turn), Constants.Drive.CENTRE_ROTATION);
    
    moteurNordPID.setReference(speeds.northMetersPerSecond, ControlType.kVelocity);
    moteurSudPID.setReference(speeds.southMetersPerSecond, ControlType.kVelocity);
    moteurEstPID.setReference(speeds.eastMetersPerSecond, ControlType.kVelocity);
    moteurOuestPID.setReference(speeds.westMetersPerSecond, ControlType.kVelocity);
  }

  public void stop() {
    moteurNord.set(0);
    moteurSud.set(0);
    moteurEst.set(0);
    moteurOuest.set(0);
  }
}