package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutonomeDroite extends SequentialCommandGroup {
    public AutonomeDroite(BasePilotableOkto basePilotable) {
        super(
            new Avancer(basePilotable, new Translation2d(-2.2, 0)),
            new WaitCommand(1),
            new Tourner(basePilotable, -135.0),
            new WaitCommand(1),
            new Avancer(basePilotable, new Translation2d(0, 0.5))
            // new Avancer(basePilotable, new Translation2d(0, 1)),
            // new Avancer(basePilotable, new Translation2d(-1, 0))
        );
    }
}