/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.maths.MathUtils;
import frc.maths.Vector2d;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class BasePilotable extends SubsystemBase {

  public CANSparkMax moteurNord, moteurSud, moteurEst, moteurOuest;

  public BasePilotable() {
    moteurNord = new CANSparkMax(0, MotorType.kBrushless);
    moteurSud = new CANSparkMax(1, MotorType.kBrushless);
    moteurEst = new CANSparkMax(2, MotorType.kBrushless);
    moteurOuest = new CANSparkMax(3, MotorType.kBrushless);
  }

  public void oktoDrive(double x, double y, double turn) {
    double w = turn * Constants.Drive.TURN_FACTOR * Math.PI;

    Vector2d V = new Vector2d(x, y);

    double vitesseN = V.getY() + Constants.Drive.ROUES_POSITIONS[0].getX() * w;
    double vitesseS = V.getY() + Constants.Drive.ROUES_POSITIONS[1].getX() * w;
    double vitesseE = V.getX() + Constants.Drive.ROUES_POSITIONS[2].getY() * w;
    double vitesseO = V.getX() + Constants.Drive.ROUES_POSITIONS[3].getY() * w;

    double largest = MathUtils.absArgmax(vitesseN, vitesseS, vitesseE, vitesseO);

    vitesseN /= largest;
    vitesseS /= largest;
    vitesseE /= largest;
    vitesseO /= largest;

    moteurNord.set(vitesseN);
    moteurSud.set(vitesseS);
    moteurEst.set(vitesseE);
    moteurOuest.set(vitesseO);
  }

  public void stop(){
    moteurNord.set(0);
    moteurSud.set(0);
    moteurEst.set(0);
    moteurOuest.set(0);
  }
}
