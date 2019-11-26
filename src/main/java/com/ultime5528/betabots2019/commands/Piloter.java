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
import com.ultime5528.betabots2019.subsystems.Ejecteur;

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
    double vitesse = Constants.Drive.MAX_SPEED_METRES_PAR_SEC;
    double vitesseTurn = Constants.Drive.MAX_TURN_RAD_PAR_SEC;

    if (controller.getTriggerAxis(Hand.kRight) > Constants.Drive.THRESHOLD_GACHETTE){
      vitesse *= Constants.Drive.FACTEUR_VITESSE_1;
      vitesseTurn *= Constants.Drive.FACTEUR_VITESSE_1;
    } else if (controller.getTriggerAxis(Hand.kLeft) > Constants.Drive.THRESHOLD_GACHETTE){
      vitesse *= Constants.Drive.FACTEUR_VITESSE_2;
      vitesseTurn *= Constants.Drive.FACTEUR_VITESSE_2;
    } else {
      vitesse *= Constants.Drive.FACTEUR_VITESSE_DEFAULT;
      vitesseTurn *= Constants.Drive.FACTEUR_VITESSE_DEFAULT;
    }

    double joystickX = MathUtils.deadzone(controller.getX(Hand.kLeft), Constants.Drive.MIN_DEADZONE) * vitesse;
    double joystickY = -MathUtils.deadzone(controller.getY(Hand.kLeft), Constants.Drive.MIN_DEADZONE) * vitesse;
    double joystickZ = -MathUtils.deadzone(controller.getX(Hand.kRight), Constants.Drive.MIN_DEADZONE) * vitesseTurn;

    basePilotable.oktoDrive(joystickX, joystickY, joystickZ);
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
