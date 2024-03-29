// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class LimitSwitchFunction extends Command {
  private Shooter shooter;
  /** Creates a new LimitSwitchFunction. */
  public LimitSwitchFunction(Shooter m_shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = m_shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (shooter.blackLimitSwitch == true) {
      shooter.setLaunchWheel(0);
      shooter.setFeedWheel(0);
    }
    else {
      
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
