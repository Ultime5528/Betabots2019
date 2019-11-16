package com.ultime5528.betabots2019.robot;

import com.ultime5528.betabots2019.commands.Piloter;
import com.ultime5528.betabots2019.commands.Tourner;
import com.ultime5528.betabots2019.commands.test.AvancerVitesseMax;
import com.ultime5528.betabots2019.commands.test.TournerVitesseMax;
import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * RobotContainer
 */
public class RobotContainer {

    private final Joystick joystick = new Joystick(0);

    private final BasePilotableOkto basePilotable = new BasePilotableOkto();

    public RobotContainer() {
        configureBindings();
    
        basePilotable.setDefaultCommand(new Piloter(basePilotable, joystick));
        
    }

    public void configureBindings(){
        // TODO Erreur de compilation
        // new JoystickButton(joystick, 1).whenPressed(new Tourner(basePilotable, 90));
        new JoystickButton(joystick, 2).toggleWhenPressed(new AvancerVitesseMax(basePilotable).withTimeout(5));
        new JoystickButton(joystick, 3).toggleWhenPressed(new TournerVitesseMax(basePilotable).withTimeout(5));
    }

    
}