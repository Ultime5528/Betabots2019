/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.maths.MathUtils;
import com.ultime5528.betabots2019.robot.Constants;
import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Piloter extends CommandBase {

  private BasePilotableOkto basePilotable;
  private XboxController controller;

  public Piloter(BasePilotableOkto basePilotable, XboxController controller) {
    this.basePilotable = basePilotable;
    this.controller = controller;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Vitesse
    Translation2d robotSpeed = basePilotable.getSpeed();
    double joystickX = MathUtils.deadzone(controller.getX(Hand.kLeft), Constants.Drive.MIN_DEADZONE);
    double joystickY = -MathUtils.deadzone(controller.getY(Hand.kLeft), Constants.Drive.MIN_DEADZONE);
        
    Translation2d joystickPos = new Translation2d(joystickX * Constants.Drive.MAX_SPEED_METRES_PAR_SEC,
      joystickY * Constants.Drive.MAX_SPEED_METRES_PAR_SEC);

    Translation2d diff = joystickPos.minus(robotSpeed);

    Translation2d speed;
    if (diff.getNorm() <= Constants.Drive.PILOTER_DIFF_THRESHOLD) {
      speed = joystickPos;
    } else {
      speed = robotSpeed.plus(diff.times(Constants.Drive.PILOTER_DIFF_THRESHOLD / diff.getNorm()));
    }

    // Omega
    double joystickZ = MathUtils.deadzone(controller.getX(Hand.kRight), Constants.Drive.MIN_DEADZONE);
    double omegaJoystick = joystickZ * Constants.Drive.MAX_TURN_RAD_PAR_SEC;
    double omegaRobot = basePilotable.getRotationSpeed(); // TODO peut-être remplacer par getVitesseGyro() converti en
                                                          // rad/s.

    System.out.println(joystickX + ":" + joystickY + ":" + joystickZ);

    double omegaDiff = omegaJoystick - omegaRobot;

    double omega;
    if(Math.abs(omegaDiff) <= Constants.Drive.PILOTER_DIFF_THRESHOLD){ // TODO Ajouter un threshold spécifique pour l'angle
      omega = omegaJoystick;
    } else {
      omega = omegaRobot + Math.signum(omegaDiff) * Constants.Drive.PILOTER_DIFF_THRESHOLD;
    }

    //System.out.println(speed.getX() + ", " + speed.getY() + ", " + omega);
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
