package com.ultime5528.betabots2019.commands;

import com.ultime5528.betabots2019.subsystems.BasePilotableOkto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutonomeGauche extends SequentialCommandGroup {
    public AutonomeGauche(BasePilotableOkto basePilotable) {
        super(
            new Avancer(basePilotable, new Translation2d(-5,0)),
            new Tourner(basePilotable, 45.0)
        );
    }
}