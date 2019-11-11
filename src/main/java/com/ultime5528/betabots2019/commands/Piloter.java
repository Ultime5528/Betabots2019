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
    basePilotable.oktoDrive(joystick.getX() * Constants.Drive.MAX_SPEED_METRES_PAR_SEC,
        -joystick.getY() * Constants.Drive.MAX_SPEED_METRES_PAR_SEC,
        joystick.getZ() * Constants.Drive.MAX_TURN_RAD_PAR_SEC);
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
