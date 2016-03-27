// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1073.ApolloSimpleBot.subsystems;

import org.usfirst.frc1073.ApolloSimpleBot.RobotMap;
import org.usfirst.frc1073.ApolloSimpleBot.commands.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon leftMotor1 = RobotMap.driveTrainleftMotor1;
    private final CANTalon leftMotor2 = RobotMap.driveTrainleftMotor2;
    private final CANTalon rightMotor1 = RobotMap.driveTrainrightMotor1;
    private final CANTalon rightMotor2 = RobotMap.driveTrainrightMotor2;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    boolean invertLeft = true;
    boolean invertRight = false;
    
    boolean invertLeftEncoder = false;
    boolean invertRightEncoder = false;
    
    public enum profiles {
    	SPEED,
    	POSITIONAL,
    	BASIC
    }
    
    private profiles current;
    
    private static final double MOTOR_TOP_RPM = 1169; // This is the top speed of the robot in RPM
    
    private static final double RAMP_RATE = 0.0005; // in Volts/Second. This means if it is 12V/1sec it will reach full speed in 1 second
    
    private static final double GEAR_RATIO = 11.0 / 8.0;
    private static final double WHEELE_RADIUS = 8.0; // in inches
    private static final double FINAL_RADIUS = WHEELE_RADIUS / GEAR_RATIO; // radius after gear ratio (in inches)
    
    public DriveTrain() {
    	
    	current = profiles.SPEED;
    	
    	setupMotors();
    	setupEncoders();
    	setupPID();
    }
    
    private void setupPID() {
    	leftMotor2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	rightMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	
    	leftMotor2.configNominalOutputVoltage(+0f, -0f);
    	rightMotor1.configNominalOutputVoltage(+0f, -0f);
    	
    	leftMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
    	rightMotor1.configPeakOutputVoltage(+12.0f, -12.0f);
    	
    	setPID(0.69, 0.009, 6.9, 0.36341);
    }
    
    private void setupMotors() {
    	leftMotor1.setInverted(invertLeft);
    	leftMotor2.setInverted(invertLeft);
    	rightMotor1.setInverted(invertRight);
    	rightMotor1.setInverted(invertRight);
    	
    	leftMotor1.changeControlMode(CANTalon.TalonControlMode.Follower);
    	leftMotor1.set(leftMotor2.getDeviceID());
    	rightMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    	rightMotor2.set(rightMotor1.getDeviceID());
    	
    	leftMotor2.setVoltageRampRate(RAMP_RATE);
    	rightMotor1.setVoltageRampRate(RAMP_RATE);
    }
    
    private void setupEncoders() {
    	
    	leftMotor2.reverseSensor(invertLeftEncoder);
    	rightMotor1.reverseSensor(invertRightEncoder);
    	
    	leftMotor2.configEncoderCodesPerRev(360);
    	rightMotor1.configEncoderCodesPerRev(360);
    }
    
    public void setPID(double p, double i, double d, double f) {
    	leftMotor2.setPID(p, i, d, f, 0, RAMP_RATE, 0);
    	rightMotor1.setPID(p, i, d, f, 0, RAMP_RATE, 0);
    }
    
    public CANTalon.TalonControlMode getState() {
    	return leftMotor2.getControlMode();
    }
    
    public void setBasic() {
    	
    	current = profiles.BASIC;
    	
    	leftMotor2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	rightMotor1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }
    
    public void setSpeedMode() {
    	
    	current = profiles.SPEED;
    	
    	leftMotor2.setProfile(profiles.SPEED.ordinal());
    	rightMotor1.setProfile(profiles.SPEED.ordinal());
    	
    	leftMotor2.changeControlMode(CANTalon.TalonControlMode.Speed);
    	rightMotor1.changeControlMode(CANTalon.TalonControlMode.Speed);
    }
    
    public void setPositional() {
    	
    	current = profiles.POSITIONAL;
    	
    	leftMotor2.setProfile(profiles.POSITIONAL.ordinal());
    	rightMotor1.setProfile(profiles.POSITIONAL.ordinal());
    	
    	leftMotor2.changeControlMode(CANTalon.TalonControlMode.Position);
    	rightMotor1.changeControlMode(CANTalon.TalonControlMode.Position);
    }
    
    public double getLeftRawSpeed() {
    	return leftMotor2.getSpeed();
    }
    
    public double getLeftSpeedFps() {
    	return getLeftRawSpeed() * ((FINAL_RADIUS * 2 * Math.PI) / 720.0);
    }
    
    public double getLeftRawDistance() {
    	return leftMotor2.getPosition();
    }
    
    public double getLeftDistanceInches() {
    	return getLeftRawDistance() * (FINAL_RADIUS * 2 * Math.PI);
    }
    
    public double getRightRawSpeed() {
    	return rightMotor1.getSpeed();
    }
    
    public double getRightSpeedFps() {
    	return getRightRawSpeed() * ((FINAL_RADIUS * 2 * Math.PI) / 720.0);
    }
    
    public double getRightRawDistance() {
    	return rightMotor1.getPosition();
    }
    
    public double getRightDistanceInches() {
    	return getRightRawDistance() * (FINAL_RADIUS * 2 * Math.PI);
    }
    
    public profiles getCurrentProfile() {
    	return current;
    }
    
    public void setProfile(profiles newProfile) {
    	current = newProfile;
    }
    
    public void moveBasic(double left, double right) {
    	
    	if(getState() != CANTalon.TalonControlMode.PercentVbus) setBasic();
    	
    	leftMotor2.set(left);
    	rightMotor1.set(right);
    }
    
    public void movePIDSpeed(double leftSpeed, double rightSpeed) {
    	
    	if(getState() != CANTalon.TalonControlMode.Speed) setSpeedMode();
    	
    	leftMotor2.set(leftSpeed * (MOTOR_TOP_RPM));
    	rightMotor1.set(rightSpeed * MOTOR_TOP_RPM);
    	
    }
    
    public void movePIDPositional(double leftDistanceInches, double rightDistanceInches) {
    	
    	if(getState() != CANTalon.TalonControlMode.Position) setPositional();
    	
    	leftDistanceInches /= (FINAL_RADIUS * 2 * Math.PI);
    	rightDistanceInches /= (FINAL_RADIUS * 2 * Math.PI);
    	
    	double travelDistanceLeft = getLeftDistanceInches() + leftDistanceInches;
    	double travelDistanceRight = getRightDistanceInches() + rightDistanceInches;
    	
    	leftMotor2.set(travelDistanceLeft);
    	rightMotor1.set(travelDistanceRight);
    }
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Drive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}

