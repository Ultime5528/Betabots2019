package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;
import com.ultime5528.betabots2019.subsystems.Ejecteur;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutonomeDroite extends SequentialCommandGroup {
    public AutonomeDroite(BasePilotableOkto basePilotable, Ejecteur ejecteur) {
        super(
            new Avancer(basePilotable, new Translation2d(-2.3, 0)),
            new WaitCommand(1),
            new Tourner(basePilotable, -66),
            new WaitCommand(1),
            new Avancer(basePilotable, new Translation2d(0, 0.21)),
            new WaitCommand(1),
            new Ejecter(ejecteur),
            new WaitCommand(1),
            new Avancer(basePilotable, new Translation2d(0, -2.3))
        );
    }
}