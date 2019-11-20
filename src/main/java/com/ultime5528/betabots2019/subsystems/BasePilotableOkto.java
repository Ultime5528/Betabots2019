/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.betabots2019.subsystems;

import java.util.Arrays;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ultime5528.betabots2019.robot.Constants;
import com.ultime5528.betabots2019.utils.ChassisSpeeds;
import com.ultime5528.betabots2019.utils.OktoDriveKinematics;
import com.ultime5528.betabots2019.utils.OktoDriveOdometry;
import com.ultime5528.betabots2019.utils.OktoDriveWheelSpeeds;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BasePilotableOkto extends SubsystemBase {

  private CANSparkMax moteurNord, moteurSud, moteurEst, moteurOuest;
  private CANEncoder encodeurNord, encodeurSud, encodeurEst, encodeurOuest;
  private CANPIDController moteurNordPID, moteurSudPID, moteurEstPID, moteurOuestPID;

  private ADIS16448_IMU gyro;

  private OktoDriveKinematics kinematics;
  private OktoDriveOdometry odometry;

  private Pose2d pose;

  private ChassisSpeeds robotSpeed = new ChassisSpeeds();

  public BasePilotableOkto() {

    SmartDashboard.putNumber("P", Constants.Drive.P);
    SmartDashboard.putNumber("I", Constants.Drive.I);
    SmartDashboard.putNumber("D", Constants.Drive.D);

    moteurNord = new CANSparkMax(1, MotorType.kBrushless);
    moteurSud = new CANSparkMax(2, MotorType.kBrushless);
    moteurEst = new CANSparkMax(3, MotorType.kBrushless);
    moteurOuest = new CANSparkMax(4, MotorType.kBrushless);

    for (CANSparkMax moteur : Arrays.asList(moteurNord, moteurSud, moteurEst, moteurOuest)){
      configureSparkMax(moteur);
    }

    moteurEst.setInverted(true);
    moteurSud.setInverted(true);

    encodeurNord = moteurNord.getEncoder();
    encodeurSud = moteurSud.getEncoder();
    encodeurEst = moteurEst.getEncoder();
    encodeurOuest = moteurOuest.getEncoder();

    setIdleMode(IdleMode.kBrake);

    encodeurNord.setPositionConversionFactor(Constants.Drive.POSITION_CONVERSION_FACTOR);
    encodeurNord.setVelocityConversionFactor(Constants.Drive.VELOCITY_CONVERSION_FACTOR);
    encodeurSud.setPositionConversionFactor(Constants.Drive.POSITION_CONVERSION_FACTOR);
    encodeurSud.setVelocityConversionFactor(Constants.Drive.VELOCITY_CONVERSION_FACTOR);
    encodeurEst.setPositionConversionFactor(Constants.Drive.POSITION_CONVERSION_FACTOR);
    encodeurEst.setVelocityConversionFactor(Constants.Drive.VELOCITY_CONVERSION_FACTOR);
    encodeurOuest.setPositionConversionFactor(Constants.Drive.POSITION_CONVERSION_FACTOR);
    encodeurOuest.setVelocityConversionFactor(Constants.Drive.VELOCITY_CONVERSION_FACTOR);

    moteurNordPID = moteurNord.getPIDController();
    moteurSudPID = moteurSud.getPIDController();
    moteurEstPID = moteurEst.getPIDController();
    moteurOuestPID = moteurOuest.getPIDController();

    moteurNordPID.setP(Constants.Drive.P);
    moteurNordPID.setI(Constants.Drive.I);
    moteurNordPID.setD(Constants.Drive.D);
    moteurNordPID.setFF(Constants.Drive.FF);

    moteurOuestPID.setP(Constants.Drive.P);
    moteurOuestPID.setI(Constants.Drive.I);
    moteurOuestPID.setD(Constants.Drive.D);
    moteurOuestPID.setFF(Constants.Drive.FF);

    moteurSudPID.setP(Constants.Drive.P);
    moteurSudPID.setI(Constants.Drive.I);
    moteurSudPID.setD(Constants.Drive.D);
    moteurSudPID.setFF(Constants.Drive.FF);

    moteurEstPID.setP(Constants.Drive.P);
    moteurEstPID.setI(Constants.Drive.I);
    moteurEstPID.setD(Constants.Drive.D);
    moteurEstPID.setFF(Constants.Drive.FF);

    gyro = new ADIS16448_IMU();
    gyro.calibrate();
    addChild("Gyro", gyro);
    // TODO changer gyro + calibrer

    kinematics = new OktoDriveKinematics(Constants.Drive.ROUES_POSITIONS_NORD, Constants.Drive.ROUES_POSITIONS_SUD,
        Constants.Drive.ROUES_POSITIONS_EST, Constants.Drive.ROUES_POSITIONS_OUEST);

    odometry = new OktoDriveOdometry(kinematics);
  }

  /**
   * Fais tourner les moteurs de cette base pilotable en suivant le mouvement
   * passé en paramètre.
   * 
   * @param forwardSpeed Vitesse en m/s vers l'avant (valeur postive) ou vers
   *                     l'arrière (valeur négative).
   * @param sideSpeed    Vitesse en m/s vers la droite (valeur postive) ou vers la
   *                     gauche (valeur négative).
   * @param turn         Vitesse de tour en rad/s autour du centre de rotation
   *                     défini dans {@link Constants} vers la droite (valeur
   *                     positive) ou vers la gauche (valeur négative).
   */
  public void oktoDrive(double forwardSpeed, double sideSpeed, double turn) {
    System.out.println(forwardSpeed + " " + sideSpeed + " " + turn);
    OktoDriveWheelSpeeds speeds = kinematics.toWheelSpeeds(new ChassisSpeeds(forwardSpeed, sideSpeed, turn),
        Constants.Drive.CENTRE_ROTATION);

    speeds.normalize(Constants.Drive.MAX_SPEED_METRES_PAR_SEC);

    System.out.println(speeds);

    SmartDashboard.putNumber("moteurNord reference", speeds.northMetersPerSecond);
    SmartDashboard.putNumber("moteurSud reference", speeds.southMetersPerSecond);
    SmartDashboard.putNumber("moteurEst reference", speeds.eastMetersPerSecond);
    SmartDashboard.putNumber("moteurOuest reference", speeds.westMetersPerSecond);

    moteurNordPID.setReference(speeds.northMetersPerSecond, ControlType.kVelocity);
    moteurSudPID.setReference(speeds.southMetersPerSecond, ControlType.kVelocity);
    moteurEstPID.setReference(speeds.eastMetersPerSecond, ControlType.kVelocity);
    moteurOuestPID.setReference(speeds.westMetersPerSecond, ControlType.kVelocity);
    
  }

  @Override
  public void periodic() {
    OktoDriveWheelSpeeds wheelSpeeds = new OktoDriveWheelSpeeds(encodeurNord.getVelocity(), encodeurSud.getVelocity(),
        encodeurEst.getVelocity(), encodeurOuest.getVelocity());

    robotSpeed = kinematics.toChassisSpeeds(wheelSpeeds);

    var angle = Rotation2d.fromDegrees(-getAngle());

    // PID
    double new_p = SmartDashboard.getNumber("P", 0);
    if(Constants.Drive.P != new_p){
      Constants.Drive.P = new_p;
      moteurNordPID.setP(Constants.Drive.P);
      moteurSudPID.setP(Constants.Drive.P);
      moteurEstPID.setP(Constants.Drive.P);
      moteurOuestPID.setP(Constants.Drive.P);
    }

    double new_i = SmartDashboard.getNumber("I", 0);
    if(Constants.Drive.I != new_i){
      Constants.Drive.I = new_i;
      moteurNordPID.setI(Constants.Drive.I);
      moteurSudPID.setI(Constants.Drive.I);
      moteurEstPID.setI(Constants.Drive.I);
      moteurOuestPID.setI(Constants.Drive.I);
    }

    double new_d = SmartDashboard.getNumber("D", 0);
    if(Constants.Drive.D != new_d){
      Constants.Drive.D = new_d;
      moteurNordPID.setD(Constants.Drive.D);
      moteurSudPID.setD(Constants.Drive.D);
      moteurEstPID.setD(Constants.Drive.D);
      moteurOuestPID.setD(Constants.Drive.D);
    }

    // Update the pose
    pose = odometry.update(angle, wheelSpeeds);

    SmartDashboard.putNumber("moteurNord output", moteurNord.get());
    SmartDashboard.putNumber("moteurNord vitesse", encodeurNord.getVelocity());
    SmartDashboard.putNumber("moteurNord position", encodeurNord.getPosition());

    SmartDashboard.putNumber("moteurSud output", moteurSud.get());
    SmartDashboard.putNumber("moteurSud vitesse", encodeurSud.getVelocity());
    SmartDashboard.putNumber("moteurSud position", encodeurSud.getPosition());

    SmartDashboard.putNumber("moteurEst output", moteurEst.get());
    SmartDashboard.putNumber("moteurEst vitesse", encodeurEst.getVelocity());
    SmartDashboard.putNumber("moteurEst position", encodeurEst.getPosition());

    SmartDashboard.putNumber("moteurOuest output", moteurOuest.get());
    SmartDashboard.putNumber("moteurOuest vitesse", encodeurOuest.getVelocity());
    SmartDashboard.putNumber("moteurOuest position", encodeurOuest.getPosition());

    SmartDashboard.putNumber("Odometry rotation speed", Math.toDegrees(getRotationSpeed()));
    SmartDashboard.putNumber("Gyro rotation speed", getVitesseGyro());
  }

  private void configureSparkMax(CANSparkMax moteur){
    moteur.enableVoltageCompensation(12.0);
    moteur.setClosedLoopRampRate(1);
    // moteur.setOpenLoopRampRate(10);
  }

  public void setIdleMode(IdleMode mode){
    moteurNord.setIdleMode(mode);
    moteurSud.setIdleMode(mode);
    moteurEst.setIdleMode(mode);
    moteurOuest.setIdleMode(mode);
  }

  public Translation2d getSpeed() {
    return new Translation2d(robotSpeed.vxMetersPerSecond, robotSpeed.vyMetersPerSecond);
  }

  public double getRotationSpeed() {
    return robotSpeed.omegaRadiansPerSecond; // TODO comparer avec getVitesseGyro()
  }

  public double getAngle() {
    return gyro.getAngleZ();
  }

  /**
   * 
   * @return la vitesse de rotation du robot dans le sens horaire, en °/s.
   */
  public double getVitesseGyro() {
    return gyro.getRateZ();
  }

  public Translation2d getTranslation() {
    return pose.getTranslation();
  }

  public Rotation2d getRotation() {
    return pose.getRotation();
  }

  public double getVitesseNord(){
    return encodeurNord.getVelocity();
  }

  public double getVitesseSud(){
    return encodeurSud.getVelocity();
  }

  public double getVitesseEst(){
    return encodeurEst.getVelocity();
  }

  public double getVitesseOuest(){
    return encodeurOuest.getVelocity();
  }

  public void setMoteurs(double nord, double sud, double est, double ouest) {
    moteurNord.set(nord);
    moteurSud.set(sud);
    moteurEst.set(est);
    moteurOuest.set(ouest);
  }

  public void stop() {
    moteurNord.set(0);
    moteurSud.set(0);
    moteurEst.set(0);
    moteurOuest.set(0);
  }
}
