// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Added imports
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;  //used for follow() method
import com.ctre.phoenix.motorcontrol.ControlMode;  //black VEX pro motor controler
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;  //black VEX pro motor controler
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;





/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //added
 

  //below are constants.  Benifit: the constant may show up in more than one place, you only have to change one.
  //                               your code has words and not just numbers
  private static final int leftfrontID = 2;
  private static final int leftbackID = 1;
  private static final int rightfrontID = 11;
  private static final int rightbackID = 12;
  private static final int feedID = 5;
  private static final int lauchTopID = 6;
  private static final int launchBottomID = 7;
  private static final int rotateLeftID = 8;
  private static final int rotateRightID = 9;

  //motor controlers
  private final WPI_VictorSPX m_leftfront = new CANSparkMax(leftfrontID);
  private final WPI_VictorSPX m_leftback = new CANSparkMax(leftbackID);
  private final WPI_VictorSPX m_righfront = new CANSparkMax(rightfrontID);
  private final WPI_VictorSPX m_rightback = new CANSparkMax(rightbackID);
  private final CANSparkMax m_feed = new CANSparkMax(feedID);
  private final CANSparkMax m_launchTop = new CANSparkMax(lauchTopID);
  private final CANSparkMax m_launchBottom = new CANSparkMax(launchBottomID);
  private final CANSparkMax m_rotateLeft = new CANSparkMax(rotateLeftID);
  private final CANSparkMax m_rotateRight = new CANSparkMax(rotateRightID);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftfront, m_rightfront);
  private final Joystick driver = new Joystick(0);
  private final XboxController operator = new XboxController(1);



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //invert motor
    m_leftfront.setInverted(true);

    //CAN follow method
    m_leftback.follow(m_leftfront);
    m_rightback.follow(m_rightfront);
    m_launchBottom.follow(m_launchTop);
    m_rotateRight.follow(m_rotateLeft);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        m_leftfront.set(0.5);
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {


  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
    m_robotDrive.arcadeDrive(-driver.getY(), -driver.getX());
//Rotate arm
    double rotateSpeed = operator.getRawAxis(3); //Get the manual lift speed
    if(rotateSpeed > 0) { //If the manual speed is negative..._??negative??
      rotateSpeed *= 0.5; //Limit the rotate forward speed
       m_rotateLeft.set(rotateForwardSpeed);
    }  else {  //Else...
      rotateSpeed *= 0; //Limit the down speed_?? it was 0.1??_Oh...joystick down(-) 
      m_rotateLeft.set(rotateForwardSpeed);
    }

  if(rotateSpeed < 0) { //If the manual speed is negative..._??negative??
    rotateForwardSpeed *= -0.1; //Limit the rotate forward speed
     m_rotateLeft.set(rotateForwardSpeed);
    }  else {  //Else...
    rotateSpeed *= 0; //Limit the down speed_?? it was 0.1??_Oh...joystick down(-) 
    m_rotateLeft.set(rotateSpeed);
    }


//intake
    double launchSpeed = operator.getRawAxis(1);
    m_launchTop.set(launchSpeed)
  

  public void SerialCommandGroup { //sequential and parallel command groups
      command 1.wait(0.5),
      Command 2
  
  m_feed.set(0.2)
  }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
