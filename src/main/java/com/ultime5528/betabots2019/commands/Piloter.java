/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.robot.Constants;
import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Piloter extends CommandBase {

  private BasePilotableOkto basePilotable;
  private Joystick joystick;

  public Piloter(BasePilotableOkto basePilotable, Joystick joystick) {
    this.basePilotable = basePilotable;
    this.joystick = joystick;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Vitesse
    Translation2d robotSpeed = basePilotable.getSpeed();
    Translation2d joystickPos = new Translation2d(joystick.getX() * Constants.Drive.MAX_SPEED_METRES_PAR_SEC,
        -joystick.getY() * Constants.Drive.MAX_SPEED_METRES_PAR_SEC);

    Translation2d diff = joystickPos.minus(robotSpeed);

    Translation2d speed;
    if (diff.getNorm() <= Constants.Drive.PILOTER_DIFF_THRESHOLD) {
      speed = joystickPos;
    } else {
      speed = robotSpeed.plus(diff.times(Constants.Drive.PILOTER_DIFF_THRESHOLD / diff.getNorm()));
    }

    // Omega
    double omegaJoystick = joystick.getZ() * Constants.Drive.MAX_TURN_RAD_PAR_SEC;
    double omegaRobot = basePilotable.getRotationSpeed(); // TODO peut-être remplacer par getVitesseGyro() converti en
                                                          // rad/s.

    double omegaDiff = omegaJoystick - omegaRobot;

    double omega;
    if(Math.abs(omegaDiff) <= Constants.Drive.PILOTER_DIFF_THRESHOLD){
      omega = omegaJoystick;
    } else {
      omega = omegaRobot + Math.signum(omegaDiff) * Constants.Drive.PILOTER_DIFF_THRESHOLD;
    }

    basePilotable.oktoDrive(speed.getX(), speed.getY(), omega);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();
  }

}
