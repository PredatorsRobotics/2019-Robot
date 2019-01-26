/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software %- may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private Spark m_frontLeft = new Spark(0);
	private Spark m_rearLeft = new Spark(1);
	private SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
	private Spark m_frontRight = new Spark(2);
	private Spark m_rearRight = new Spark(3);
  private SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
	private DifferentialDrive m_robotdrive = new DifferentialDrive(m_left, m_right);
  private Spark sideDrive = new Spark(4);
  private Joystick controller = new Joystick(0);
  private final Timer m_timer = new Timer();
  
   
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotdrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_robotdrive.stopMotor(); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }



  /**
   * This function is called periodically during teleoperated mode.
   */




  @Override
  public void teleopPeriodic() {

    //Basic drive
    m_robotdrive.arcadeDrive(controller.getY()*-1, controller.getX()*-1);


    //Side drive


    if (controller.getRawButton(7)) { // Left trigger pressed, go left

      sideDrive.set(-.5);
    }


    else if (controller.getRawButton(8)) { // Right trigger pressed, go right

      sideDrive.set(.5);
    }

    else {
      sideDrive.set(0);
    }

    //Elevator
    

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
