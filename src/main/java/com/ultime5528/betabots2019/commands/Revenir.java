/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.robot.Constants;
import com.ultime5528.betabots2019.subsystems.Ejecteur;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Revenir extends CommandBase {
  private final Ejecteur ejecteur;
  private final Timer timer;

  public Revenir(Ejecteur ejecteur){
    this.ejecteur = ejecteur;
    timer = new Timer();
    addRequirements(ejecteur);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    ejecteur.revenir();
  }

  @Override
  public boolean isFinished() {
   return timer.get() >= Constants.Ejecteur.TEMPS_REVENIR;
  }

  @Override
  public void end(boolean interrupted) {
    ejecteur.stop();
  }
}
