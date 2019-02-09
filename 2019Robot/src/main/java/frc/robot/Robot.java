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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.Talon;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private Spark m_frontLeft =  new Spark(1);
  private Spark m_rearLeft = new Spark(0);
	private SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
	private Spark m_frontRight = new Spark(3);
	private Spark m_rearRight = new Spark(2);
  private SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
	private DifferentialDrive m_robotdrive = new DifferentialDrive(m_left, m_right);
  //private Talon sideDrive = new Talon(7);
  private Joystick controller = new Joystick(0);
  private Joystick controller2 = new Joystick(1);
  private final Timer m_timer = new Timer();
  private boolean isCarrying;
  private Encoder elevatorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
  private Spark l_elevator = new Spark(5);
  private Spark r_elevator = new Spark(4);
  private int targetHeight = 1;
  
   
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() { 
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }
   * This function is called periodically during autonomous.
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotdrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_robotdrive.stopMotor(); // stop robot
    }
  }
*/


  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    elevatorEncoder.reset();

  }



  /**
   * This function is called periodically during teleoperated mode.
   */




  @Override
  public void teleopPeriodic() {

    int carryOffset;// isCarrying input
    int elevatorSpeed; 
    int whenClose;


    //Basic drive
    m_robotdrive.arcadeDrive(controller.getY()*-1, controller.getX());

    /** ///Side drive
    if (controller.getRawButton(7)) { // Left trigger pressed, go left
      sideDrive.set(.5);
    }else if (controller.getRawButton(8)) { // Right trigger pressed, go right
      sideDrive.set(-.5);
    }else {
      sideDrive.set(0);
    }
*/
    System.out.println(elevatorEncoder.get());
    
    //Elevator Stuff
    if(controller2.getRawButtonPressed(0)){ // X pressed, toggle isCarrying
      isCarrying = !isCarrying;
    }


    if(isCarrying){
      carryOffset = 2;//IN INCHES****
    }
    else{
      carryOffset = 0;
    }

    if(sampleEncoder.get() > ((targetHeight - .5) * 65.4) ||
      (sampleEncoder.get() < ((targetHeight + .5) * 65.4){
      whenClose = .25;
    }
    else{
      whenClose = 1;
    }

    if (controller.getRawButton(9) || controller.getRawButton(10)){
      if(controller.getRawButton(9)){//UP
        l_elevator.set(.75);
        r_elevator.set(.75);
      }else if(controller.getRawButton(10)){//DOWN
        l_elevator.set(-.50);
        r_elevator.set(-.50);
      }else{
        l_elevator.set(0);
        r_elevator.set(0);
      }
    }else{
      if(controller2.getRawButtonPressed(1)){
        targetHeight = .5 + carryOffset; //IN INCHES****
      }
      if(controller2.getRawButtonPressed(2)){//IN INCHES****
        targetHeight = 20 + carryOffset;
      }
      if(controller2.getRawButtonPressed(3)){//IN INCHES****
        targetHeight = 30 + carryOffset;
      }

      if(sampleEncoder.get() > ((targetHeight * 65.4){//Convert to counts
        elevatorSpeed = (-.5 * whenClose);
      }else if(sampleEncoder.get() < (targetHeight * 65.4)){//Convert to counts
        elevatorSpeed = (.75 * whenClose);
      }else{
        elevatorSpeed = 0;
      }
      
      l_elevator.set(elevatorSpeed);
      r_elevator.set(elevatorSpeed);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
