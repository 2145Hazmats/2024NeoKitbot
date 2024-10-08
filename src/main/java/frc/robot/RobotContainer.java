// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.IsPROLicensedValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.commands.PrepareLaunchSlow;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.LimelightSubsystem;
//import frc.robot.subsystems.Pnumatics;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveForDistance;
import frc.robot.subsystems.Shooter;

// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/*
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  // private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  // private final CANLauncher m_launcher = new CANLauncher();
  private final Shooter m_shooter = new Shooter();
  //private final Pnumatics m_pnumatics = new Pnumatics();
  private final LimelightSubsystem m_LimelightSubsystem = new LimelightSubsystem();
  // private final PIDLauncher m_pid = new PIDLauncher();
  // private final CANLauncher m_launcher = new CANLauncher();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /*
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () ->
                m_drivetrain.arcadeDrive(
                    m_driverController.getLeftY(), m_driverController.getRightX()),
            m_drivetrain));

    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    /*m_driverController
        .a()
        .whileTrue(
            new PrepareLaunch(m_shooter, 1)
                .withTimeout(ShooterConstants.kShooterTimeDelay)
                .andThen(new LaunchNote(m_shooter))
                .handleInterrupt(() -> m_shooter.stop()));*/

  
     m_driverController
    .y()
    .whileTrue(
        new RunCommand(
            () ->
                m_drivetrain.FaceTowardSubwoofer(m_LimelightSubsystem.getXcoordinate(),m_LimelightSubsystem.getYcoordinate())));
     /*  m_driverController 
      .y()      
        .whileTrue(
            new PrepareLaunch(m_shooter, .37)
                .withTimeout(ShooterConstants.kShooterTimeDelay)
                .andThen(new LaunchNote(m_shooter))
                .handleInterrupt(() -> m_shooter.stop()));*/

    m_driverController
    .x()
    .whileTrue(m_shooter.readyAmpCommand().withTimeout(ShooterConstants.kShooterTimeDelay)
    .andThen(m_shooter.playAmpCommand())
    );

  
    m_driverController
    .a()
    .whileTrue(
        new RunCommand(
            () ->
                m_drivetrain.FaceStraight(
                    //m_LimelightSubsystem.getTargetRotation())));
                m_drivetrain.getGyro()))); //This is a rotation conversion rate from random stuff to degrees
   /* m_driverController
        .b()
        .whileTrue(
            new PrepareLaunchSlow(m_shooter)
                .withTimeout(ShooterConstants.kShooterTimeDelay)
                .alongWith(new DriveForDistance(m_drivetrain, .17))
                .withTimeout(ShooterConstants.kShooterTimeDelay)
                .andThen(new LaunchNote(m_shooter)).withTimeout(5)
                //.andThen(new DriveForDistance(m_drivetrain, -.17))
                .handleInterrupt(() -> m_shooter.stop()));
*/
   
    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
   
   
    m_driverController.leftBumper().whileTrue(m_shooter.intakeCommand().until(()->(m_shooter.isBlackSwitchPressed()))); //might work
    // Pnumatic Commands
    //m_driverController.povUp().onTrue(m_pnumatics.ShootPistonCommand());
    //m_driverController.povDown().onTrue(m_pnumatics.SuckPistonCommand());
    //m_driverController.povLeft().toggleOnTrue(m_pnumatics.CompressCommand());
    // m_driverController.povRight().onTrue(m_pnumatics.StopCompressCommand());

    // m_driverController.b().onTrue(Autos.exampleAuto(m_drivetrain));
    m_shooter.setDefaultCommand(m_shooter.stopCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return Autos.ShootAndDrive(m_drivetrain, m_shooter);
     return new DriveForDistance(m_drivetrain, -8);
    //return null;
  }
}
