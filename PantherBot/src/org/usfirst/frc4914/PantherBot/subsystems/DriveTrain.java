// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4914.PantherBot.subsystems;

import org.usfirst.frc4914.PantherBot.Robot;
import org.usfirst.frc4914.PantherBot.RobotMap;
import org.usfirst.frc4914.PantherBot.commands.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController rightDrive = RobotMap.driveTrainRightDrive;
    private final SpeedController leftDrive = RobotMap.driveTrainLeftDrive;
    private final RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
    private final AnalogInput ultra = RobotMap.driveTrainUltra;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final ADXRS450_Gyro gyro = RobotMap.gyro;
    // private final PIDController driveControl = RobotMap.driveControl;


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Drive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    /*
     * DRIVE CONSTANTS
     */
 	public boolean isTankDrive = true;	// if drive is tank drive
 	public boolean isInvertedDrive = false;	// if controls are inverted or not
 	public final double speedMultiplier = 0.85;	// speed limit multiplier
 	public double lastUltraReading;
 	
 	// BEGIN CUSTOM CODE
     
 	/**
 	 * Sets speed for left victor drive at a specified speed. Speed will be toned 
 	 * down by the specified speed multiplier.
 	 * @param speed the speed to set the left victor to
 	 */
     public void setLeftVictor(double speed) {
     	leftDrive.set(speed * speedMultiplier);
     }
     
     /**
  	 * Sets speed for right victor drive at a specified speed. Speed will be toned 
  	 * down by the specified speed multiplier. *This victor is inverted
  	 * @param speed the speed to set the right victor to
  	 */
     public void setRightVictor(double speed) {
     	rightDrive.set(-speed * speedMultiplier);
     }
     
     /**
      * Drives the robot straight at a specified speed.
      * @param speed speed to drive robot straight
      */
     public void driveStraight(double speed) {
     	setLeftVictor(speed);
     	setRightVictor(speed);
     }
     
     /**
      * Stops all victors involved in the drivetrain.
      */
     public void stop() {
     	driveStraight(0);
     }
     
     /**
      * Returns angular bearing of the gyrometer.
      * @return angular bearing of the gyrometer
      */
     public double getGyroBearing() {
    	 return gyro.getAngle();
     }
     
     public void resetGyro() {
    	 gyro.reset();
     }
     
     /**
      * Returns average voltage of the ultrasonic sensor.
      * @return average voltage of the ultrasonic sensor
      */
     public double getUltraDistance() {
    	 if (!(lastUltraReading < 0.17 && lastUltraReading > 0.16)) {
    		 lastUltraReading = ultra.getAverageVoltage();
    	 }
    	 return lastUltraReading;
     }
     
     /**
      * Drives robot to a specifed setpoint using PID 
      * @param setpoint
      */
     public void driveUntilSetpoint(double setpoint) {    	 
    	 // limits output range percentage by speedMultiplier
    	 // driveControl.setOutputRange(0, 100 * speedMultiplier);
    	 
    	 // sets the setpoint of ultrasonic sensor
    	 // driveControl.setSetpoint(setpoint);
    	 
    	 // enables PIDs
    	 // driveControl.enable();
    	 
    	 // frees PIDs
    	 // driveControl.free();
     }
     
     public void rotateCW(double angle) {
    	gyro.reset();
      	
      	while (getGyroBearing() < angle) {
	      	setLeftVictor(1);
	      	setRightVictor(-1);
      	}
      	
      	stop();
      	
      	stop();
     }
     
    public void rotateCCW(double angle) {
    	gyro.reset();
      	
      	while (getGyroBearing() > angle || getGyroBearing() == 0) {
	      	setLeftVictor(-1);
	      	setRightVictor(1);
      	}
      	
      	stop();
    }
    
    // experimental
    public void driveStraightWithGyro(double speed) {
    	robotDrive.drive(1, -gyro.getAngle() * 0.03);
    }
     
    // END CUSTOM CODE
}

