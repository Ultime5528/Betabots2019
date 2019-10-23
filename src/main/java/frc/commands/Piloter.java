/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.BasePilotable;

public class Piloter extends CommandBase {

  private BasePilotable basePilotable;
  private Joystick joystick;

  public Piloter(BasePilotable basePilotable, Joystick joystick) {
    this.basePilotable = basePilotable;
    this.joystick = joystick;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    basePilotable.oktoDrive(joystick.getX(), joystick.getY(), joystick.getZ());
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
