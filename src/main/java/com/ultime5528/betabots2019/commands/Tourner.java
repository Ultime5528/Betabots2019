/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.subsystems.BasePilotable;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Tourner extends CommandBase {
  private BasePilotable basePilotable;

  public Tourner(BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
  }
}
