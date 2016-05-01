// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4914.WindsorBot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4914.WindsorBot.Robot;
import org.usfirst.frc4914.WindsorBot.subsystems.Forklift;


public class OperateLift extends Command {

	private boolean FLiftBelowMinLimit = true;
	private boolean FLiftWithin54InchLimit = true;
	private boolean FLiftAbove54InchLimit = false;
	
	private boolean FLiftWithinMaxLimit = true;
	private boolean FLiftAboveMaxLimit = false;
	
	private boolean hookWithinMaxLimit = true;
	private boolean hookBelowMinLimit = false;
	private boolean hookAboveMaxLimit = false;

	private static final double forkliftMinSetpoint = Forklift.forkliftMinSetpoint;
	private static final double forklift54Setpoint = Forklift.forklift54Setpoint;
	private static final double forkliftMaxSetpoint = Forklift.forkliftMaxSetpoint;
	private static final double hookMinSetpoint = Forklift.hookMinSetpoint;
	private static final double hookMaxSetpoint = Forklift.hookMaxSetpoint;

    public OperateLift() {
    	requires(Robot.forklift);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    protected void initialize() {
    	FLiftBelowMinLimit = true;
    	FLiftWithin54InchLimit = true;
    	FLiftAbove54InchLimit = false;

    	FLiftWithinMaxLimit = true;
    	FLiftAboveMaxLimit = false;

    	hookWithinMaxLimit = true;
    	hookBelowMinLimit = false;
    	hookAboveMaxLimit = false;
    }

    protected void execute() {
    	refreshValues();
    	
    	// forklift operation
    	if (Robot.forklift.locked) {
        	if (FLiftWithin54InchLimit) { operateForklift(0); }
        	else if (FLiftBelowMinLimit) { operateForklift(1); }
        	else if (FLiftAbove54InchLimit) { operateForklift(-1); }
    	}
    	else if (!Robot.forklift.locked) {
    		if (FLiftWithinMaxLimit) { operateForklift(0); }
    		else if (FLiftBelowMinLimit) { operateForklift(1); }
    		else if (FLiftAboveMaxLimit) { operateForklift(-1); }
    	}
    	
    	// hook operation
    	if (hookWithinMaxLimit) { operateHook(0); }
    	else if (hookBelowMinLimit) { operateHook(1); }
    	else if (hookAboveMaxLimit) { operateHook(-1); }
    	
    	// prints QE values to smartdashboard
    	showQEValues();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.forklift.stop();
    }

    protected void interrupted() {
    	end();
    }
    
    /******* CUSTOM METHODS *******/
    public void refreshValues() {
    	
    	FLiftBelowMinLimit = Robot.forklift.getFLiftQEValue() <= forkliftMinSetpoint;	// [ -inf , 0 ]
    	FLiftWithin54InchLimit = Robot.forklift.getFLiftQEValue() < forklift54Setpoint &&
    							 Robot.forklift.getFLiftQEValue() > forkliftMinSetpoint; // ( 0 , 54 )
    	FLiftAbove54InchLimit = Robot.forklift.getFLiftQEValue() >= forklift54Setpoint; // [ 54 , inf ]
    	
    	FLiftWithinMaxLimit = Robot.forklift.getFLiftQEValue() < forkliftMaxSetpoint &&
    						  Robot.forklift.getFLiftQEValue() > forkliftMinSetpoint;	// ( 0 , max )
    	FLiftAboveMaxLimit = Robot.forklift.getFLiftQEValue() >= forkliftMaxSetpoint;	// [ max , inf ]

    	hookBelowMinLimit = Robot.forklift.getHookQEValue() <= hookMinSetpoint;			// [ -inf , min ]
    	hookWithinMaxLimit = Robot.forklift.getHookQEValue() < hookMaxSetpoint &&
    						 Robot.forklift.getHookQEValue() > hookMinSetpoint;			// ( 0 , max )
    	hookAboveMaxLimit = Robot.forklift.getHookQEValue() >= hookMaxSetpoint;			// [ max , inf ]
    	
    }
    
    public void operateForklift(double restriction) {
    	if (restriction == 0) {
    		Robot.forklift.setFLift(Robot.oi.codriverLJ());
    	}
    	else if (restriction < 0 && Robot.oi.codriverLJ() < 0) {
    		Robot.forklift.setFLift(Robot.oi.codriverLJ());
    	}
    	else if (restriction > 0 && Robot.oi.codriverLJ() > 0) {
    		Robot.forklift.setFLift(Robot.oi.codriverLJ());
    	}
    }
    
    public void operateHook (double restriction) {
    	if (restriction == 0) {
    		Robot.forklift.setHook(Robot.oi.codriverRJ());
    	}
    	else if (restriction < 0 && Robot.oi.codriverRJ() < 0) {
    		Robot.forklift.setHook(Robot.oi.codriverRJ());
    	}
    	else if (restriction > 0 && Robot.oi.codriverRJ() > 0) {
    		Robot.forklift.setHook(Robot.oi.codriverRJ());
    	}
    }

    public void showQEValues() {
    	SmartDashboard.putString("Quadrature Encoder Values",
    			"Forklift QE: " + Robot.forklift.getFLiftQEValue() +
    			"\nHook QE: " + Robot.forklift.getHookQEValue());
    }
}
