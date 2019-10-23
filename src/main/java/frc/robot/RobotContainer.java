package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.commands.Piloter;
import frc.subsystems.BasePilotable;

/**
 * RobotContainer
 */
public class RobotContainer {

    private final Joystick joystick = new Joystick(0);

    private final BasePilotable basePilotable = new BasePilotable();

    public RobotContainer() {
        configureBindings();
    
        basePilotable.setDefaultCommand(new Piloter(basePilotable, joystick));
    }

    public void configureBindings(){
        
    }

}