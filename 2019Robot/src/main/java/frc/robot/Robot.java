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
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
  private Joystick controller = new Joystick(0);
  private Joystick controller2 = new Joystick(1);
  private final Timer m_timer = new Timer();
  private boolean isCarrying = false;
  private boolean wannaGoSlower = false;
  private boolean isAutoCenter = false;
  private Encoder elevatorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
  private Encoder dinoEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
  private PWMTalonSRX l_elevator = new PWMTalonSRX(5);
  private PWMTalonSRX r_elevator = new PWMTalonSRX(4);
  private PWMTalonSRX sideDrive = new PWMTalonSRX(6);
  private PWMTalonSRX dinoArms = new PWMTalonSRX(7);
  private double standardLevels = 1;
  private double rotationValue = 1;
  private double targetHeight = 1;
  private AnalogInput ai = new AnalogInput(0);

  
   
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() { 
  }

  
   // This function is run once each time the robot enters autonomous mode.
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }
   // This function is called periodically during autonomous.
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
    elevatorEncoder.reset();
    dinoEncoder.reset();
    targetHeight = .5;

  }



  /**
   * This function is called periodically during teleoperated mode.
   */




  @Override
  public void teleopPeriodic() {

    double carryOffset;// isCarrying input
    double elevatorSpeed; 
    double whenClose;
    double distanceToTravel = elevatorEncoder.get() - targetHeight * 65.4;
    double driveSpeed;
    double dinoSpeed = 0;
    int pressedButton = 0;
    int pressedButton2 = 0;
    int dinoPosition = 0;
    int dinoP = 0;
    
    SmartDashboard.putBoolean("DB/LED 0", isCarrying);
    SmartDashboard.putBoolean("DB/LED 1", wannaGoSlower);
    SmartDashboard.putBoolean("DB/LED 2", isAutoCenter);

    //half Speed thing
    if(controller.getRawButtonPressed(1)){ // 1 pressed, toggle driveSpeed
      wannaGoSlower = !wannaGoSlower;
    }
    if(wannaGoSlower){
      driveSpeed = .75;
    }else{
      driveSpeed = 1;
    }



    //Basic drive
    m_robotdrive.arcadeDrive(controller.getY()*-1 * driveSpeed, controller.getX() * driveSpeed);

    //Side drive
    if(controller.getRawButtonPressed(3)){
      isAutoCenter = !isAutoCenter;
    }
    if(isAutoCenter){
      //auto side drive
      if(ai.getValue() > 1.75 && ai.getValue() < 3.2){
        sideDrive.set(.4);
      }else if(ai.getValue < 1.65 && ai.getValue() > .1){
        sideDrive.set(-.4);
      }else{
        sideDrive.set(0);
      }
    }else{
      if (controller.getRawButton(7)) { // Left trigger pressed, go left
        sideDrive.set(.7);
      }else if (controller.getRawButton(8)) { // Right trigger pressed, go right
        sideDrive.set(-.7);
      }else {
        sideDrive.set(0);
      }
    }
    

    
    //Elevator Stuff
    if(controller2.getRawButtonPressed(1)){ // X pressed, toggle isCarrying
      isCarrying = !isCarrying;
      pressedButton = 1;
    }
    if(isCarrying){
      carryOffset = 4.75;//IN INCHES****
    }else{
      carryOffset = 0;
    }


    if (Math.abs(distanceToTravel) < 200 /*encoder units*/){
      whenClose = .25;
    }else{
      whenClose = 1;
    }

    if (controller.getRawButton(9) || controller.getRawButton(10)){
      if(controller.getRawButton(9)){//UP
        l_elevator.set(.8);
        r_elevator.set(.8);
      }else if(controller.getRawButton(10)){//DOWN
        l_elevator.set(-.5);
        r_elevator.set(-.5);
      }else{
        l_elevator.set(0);
        r_elevator.set(0);
      }
    }else{

      

      if(controller2.getRawButtonPressed(4)){
        standardLevels = .5; //IN INCHES****
        pressedButton = 4;
      }
      if(controller.getRawButtonPressed(2)){//IN INCHES****
        standardLevels = 11;
        pressedButton = 21;
      }
      if(controller2.getRawButtonPressed(3)){//IN INCHES****
        standardLevels = 28;
        pressedButton = 3;
      }
      if(controller2.getRawButtonPressed(2)){//IN INCHES****
        standardLevels = 55;
        pressedButton = 22;
      }
      

      if(pressedButton != 0){
        targetHeight = standardLevels + carryOffset;
      }

      if(elevatorEncoder.get() > ((targetHeight + .15) * 65.4)){//Convert to counts
        elevatorSpeed = (-.8 * whenClose);
      }else if(elevatorEncoder.get() < ((targetHeight - .15) * 65.4)){//Convert to counts
        elevatorSpeed = (1 * whenClose);
      }else{
        elevatorSpeed = 0.10;
      }
      
      l_elevator.set(elevatorSpeed);
      r_elevator.set(elevatorSpeed);
    }

 // homing program
 if(controller2.getRawButtonPressed(10)){
  m_timer.reset();
  m_timer.start();

  if(m_timer.get() < 5.0){
    l_elevator.set(-.20);
    r_elevator.set(-.20);   
  }else if(m_timer.get() < 5.1){
    elevatorEncoder.reset();
  }
 }

    //Dino Arms
    if(controller2.getRawButton(5)){//UP
      dinoSpeed = -.4;
    }else if(controller2.getRawButton(7)){//DOWN
      dinoSpeed = .3;
    }else if(controller2.getRawButton(9)){
    
      if(controller2.getRawButtonPressed(9)){
      pressedButton2 = 1;
      dinoP = 1;
      dinoPosition = 155;
      }
   
      if(pressedButton2 != 0){
        rotationValue = dinoPosition;
      }
      
      if(dinoEncoder.get() > (rotationValue + 5)){
        dinoSpeed = -.4;
      }else if(dinoEncoder.get() < (rotationValue)){
        dinoSpeed = .3;
      }
      
    }else{
      dinoSpeed = -.1;
    }
    
  dinoArms.set(dinoSpeed);  
  System.out.println(dinoEncoder.get());



  }
 
 
 
 
  /**
   * This function is 
   * called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

