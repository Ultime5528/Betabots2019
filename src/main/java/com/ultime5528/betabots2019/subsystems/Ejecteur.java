/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ultime5528.betabots2019.robot.Constants;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Ejecteur extends SubsystemBase {
  private VictorSP moteurEjecteur;

  public Ejecteur() {
    moteurEjecteur = new VictorSP(Constants.Ports.MOTEUR_EJECTEUR);
    addChild("Moteur", moteurEjecteur);
  }

  @Override
  public void periodic() {
  }

  public void pousser() {

    moteurEjecteur.set(Constants.Ejecteur.VITESSE_EJECTION);

  }

  public void revenir() {

    moteurEjecteur.set(Constants.Ejecteur.VITESSE_REVENIR);
  }

  public void maintien() {

    moteurEjecteur.set(Constants.Ejecteur.FORCE_MAINTIEN);
  }

  public void stop() {
    moteurEjecteur.set(0.0);
  }

}
