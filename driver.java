
package rocket;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.Battery;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
public class Driver {
//attributes
	
	EV3LargeRegulatedMotor left;
	 EV3LargeRegulatedMotor right;
	EV3MediumRegulatedMotor arm;
	EV3ColorSensor colorSensor;
	EV3TouchSensor touchSensor;
	
	
//constants	
 final float CMTOANGLE = (float) (360/(5.5*Math.PI));
 float maxSpeed = Battery.getVoltage()*100;
//constructors
	public Driver (Port leftPort, Port rightPort, Port armPort){
		left= new EV3LargeRegulatedMotor(leftPort);
		right= new EV3LargeRegulatedMotor(rightPort);
		arm= new EV3MediumRegulatedMotor(armPort);
	}
	public static EV3GyroSensor gyro= new EV3GyroSensor(SensorPort.S2);
	public static SampleProvider angle = gyro.getAngleMode();
	public static float[] gyroSample = new float[angle.sampleSize()] ;
	
	
	static EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(SensorPort.S3);
	public static SampleProvider distance = ultraSensor.getDistanceMode();
	public static float[] ultraSample = new float[distance.sampleSize()] ;
	
	
	public static float measureAngle(){
		angle.fetchSample(gyroSample,0);
		System.out.println(gyroSample[0]);
		return gyroSample[0];
	}
	
	
//methods	
	public static float measureDistance(){
		distance.fetchSample(ultraSample,0);
		System.out.println(ultraSample[0]);
		return ultraSample[0];
	}
	public void greenLight() {
		Button.LEDPattern(1);
	}
	public void redLight() {
		Button.LEDPattern(2);
	}
	
	public void drive(float distance){
		left.setSpeed(180);
		right.setSpeed(180);
		float angle= distance*CMTOANGLE;
		left.rotate((int) angle, true);
		right.rotate((int) angle);
		left.stop(true);
		right.stop();
	}
	
	public void foreward(){
		left.forward();
		right.forward();
	}
	
	public void pointTurnRight(float angle){
	//	left.rotate((int) (Math.toRadians(angle)*11.88*CMTOANGLE));
		gyro.reset();
		left.setSpeed(180);
		while (Math.abs(measureAngle())<(angle-5)){
			left.forward();
		}
		left.stop();
	}

	public void pointTurnLeft(float angle){
		//right.rotate((int) (Math.toRadians(angle)*11.88*CMTOANGLE));
		gyro.reset();
		right.setSpeed(180);
		while (Math.abs(measureAngle())<(angle - 5)){
			right.forward();
		}
		right.stop();
	}
	
	public void spinRight (float angle){
	/*	left.rotate((int) (Math.toRadians(angle)*5.88*CMTOANGLE), true);
		right.rotate(-(int)(Math.toRadians(angle)*5.88*CMTOANGLE));
		left.stop();
		right.stop();*/
		gyro.reset();
		left.setSpeed(180);
		right.setSpeed(180);
		while (Math.abs(measureAngle())<(angle - 5)){
			left.forward();
			right.backward();
		}
		right.stop(true);
		left.stop();
		}
		
	public void spinLeft (float angle){
		/*right.rotate((int) (Math.toRadians(angle)*5.9*CMTOANGLE), true);
		left.rotate(-(int)(Math.toRadians(angle)*5.9*CMTOANGLE));
		left.stop();
		right.stop();*/
		gyro.reset();
		left.setSpeed(180);
		right.setSpeed(180);
		while (Math.abs(measureAngle())<(angle - 5)){
			right.forward();
			left.backward();
		}
		right.stop(true);
		left.stop();
		}
		
	
	public void smoothTurnLeft (float angle, float radius){
		
	double InnerDistance;
	double OuterDistance;
	float rad= (float) Math.toRadians(angle);
	
	gyro.reset();
	OuterDistance= (rad*radius);
	InnerDistance= (rad*(radius-15));
	
	
	
	right.setSpeed((int) (180*(OuterDistance/InnerDistance)));
	left.setSpeed(180);
	while (Math.abs(measureAngle())<(angle)){
		left.forward();
		right.forward();
	}
	right.stop(true);
	left.stop();
		/*right.rotate((int) (Math.toRadians(angle)*15*CMTOANGLE), true);
		left.rotate((int) (Math.toRadians(angle)*3*CMTOANGLE));
		left.stop();
		right.stop();*/
	}
	public void smoothTurnRight (float angle, float radius){
		double InnerDistance;
		double OuterDistance;
		float rad= (float) Math.toRadians(angle);
		
		gyro.reset();
		OuterDistance= (rad*radius);
		InnerDistance= (rad*(radius-15));
		left.setSpeed((int)(180*(OuterDistance/InnerDistance)));
		right.setSpeed(180);
		while (Math.abs(measureAngle())<(angle)){
			right.forward();
			left.forward();
		}
	
		right.stop(true);
		left.stop();
		/*right.setSpeed(80);
		left.rotate((int) (Math.toRadians(angle)*15*CMTOANGLE), true);
		right.rotate((int) (Math.toRadians(angle)*6*CMTOANGLE));
		left.stop();
		right.stop();*/
	}
	
	public void moveArm(int angle){
		arm.rotate(angle);
	}
	public void controlSpeed(float speed){
		left.setSpeed(speed);
		right.setSpeed(speed);
	}
	public void displayInfo(String text){
LCD.drawString(text, 10, 10);
	}
	public void controlAcceleration(int acceleration){
		left.setAcceleration(acceleration);
		right.setAcceleration(acceleration);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	Driver driver= new Driver(MotorPort.D, MotorPort.A, MotorPort.B);
//	driver.pointTurnRight(360);
	//driver.drive(-100);
	//driver.drive(-100);
 //driver.smoothTurnLeft(180,20);
	//driver.smoothTurnRight(180,15);
	//driver.displayInfo("memes");
	//driver.spinRight(360);
	//driver.drive(80);
	while(driver.measureDistance()> 0.2){
		driver.foreward();
	}
 
	}

}





