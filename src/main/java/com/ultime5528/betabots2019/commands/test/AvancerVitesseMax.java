/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.commands.test;

import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AvancerVitesseMax extends CommandBase {

  private BasePilotableOkto basePilotable;

  private double lastVitesse = 0;
  private double lastTimestamp = 0;

  public AvancerVitesseMax(BasePilotableOkto basePilotable) {
    this.basePilotable = basePilotable;

    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    basePilotable.setMoteurs(0, 0, 1, 1);

    double vitesse = (basePilotable.getVitesseEst()+ basePilotable.getVitesseOuest())/2;
    SmartDashboard.putNumber("vitesse", vitesse);

    double timestamp  = Timer.getFPGATimestamp();
    SmartDashboard.putNumber("acceleration", (vitesse - lastVitesse)/(timestamp - lastTimestamp));

    lastVitesse = vitesse;
    lastTimestamp = timestamp;
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
