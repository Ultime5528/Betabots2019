package com.ultime5528.betabots2019.robot;

import com.ultime5528.betabots2019.commands.Maintien;
import com.ultime5528.betabots2019.commands.Piloter;
import com.ultime5528.betabots2019.commands.Pousser;
import com.ultime5528.betabots2019.commands.Ejecter;
import com.ultime5528.betabots2019.commands.test.AvancerVitesseMax;
import com.ultime5528.betabots2019.commands.test.TournerVitesseMax;
import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;
import com.ultime5528.betabots2019.subsystems.Ejecteur;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * RobotContainer
 */
public class RobotContainer {

    private final XboxController controller = new XboxController(0);
    private final Joystick levier = new Joystick(1);

    private final BasePilotableOkto basePilotable = new BasePilotableOkto();
    private final Ejecteur ejecteur = new Ejecteur();


    public RobotContainer() {
        configureBindings();
    
        basePilotable.setDefaultCommand(new Piloter(basePilotable, controller));
        
        
        
    }

    public void configureBindings(){
        // TODO Erreur de compilation
        // new JoystickButton(joystick, 1).whenPressed(new Tourner(basePilotable, 90));
        //new JoystickButton(controller, 2).toggleWhenPressed(new AvancerVitesseMax(basePilotable).withTimeout(5));
        //new JoystickButton(controller, 3).toggleWhenPressed(new TournerVitesseMax(basePilotable).withTimeout(5));
        new JoystickButton(levier, 1).toggleWhenPressed(new Pousser(ejecteur));
    }

    
}