/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.robot.Constants;
import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;
import com.ultime5528.betabots2019.subsystems.Ejecteur;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Avancer extends CommandBase {
  private BasePilotableOkto basePilotable;

  private TrapezoidProfile profile;

  private final TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(
      0.75,
      6.0);

  private TrapezoidProfile.State goalState;
  private TrapezoidProfile.State current = new TrapezoidProfile.State();

  private Translation2d goalPosition;

  public Avancer(BasePilotableOkto basePilotable, Translation2d goalPosition) {
    this.basePilotable = basePilotable;

    this.goalPosition = goalPosition;
    
    
    addRequirements(basePilotable);
  }
  
  @Override
  public void initialize() {
    basePilotable.resetPose();
    basePilotable.resetGyro();
    double distToGo = basePilotable.getTranslation().getDistance(goalPosition);
    this.goalState = new TrapezoidProfile.State(distToGo, 0);
  }

  @Override
  public void execute() {

    Translation2d diff = goalPosition.minus(basePilotable.getTranslation());
    System.out.println(diff.getNorm());

    current.position = goalState.position - diff.getNorm();
    current.velocity = basePilotable.getSpeed().getNorm();

    profile = new TrapezoidProfile(constraints, goalState, current);

    TrapezoidProfile.State setpoint = profile.calculate(TimedRobot.kDefaultPeriod);

    Translation2d newSpeed = diff.times(setpoint.velocity / diff.getNorm());

    basePilotable.oktoDrive(newSpeed.getX(), newSpeed.getY(), 0.05 * basePilotable.getRotation().getDegrees()); // TODO kP * angle pour la rotation
  }

  @Override
  public boolean isFinished() {
    return basePilotable.getTranslation().getDistance(this.goalPosition) <= Constants.Drive.AVANCER_DIST_THRESHOLD;
  }

  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();
  }
}
