package rocket;
//imports
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Battery;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMIRemoteRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.SampleProvider;

public class RemoteDriver {

//declares all robot relevant variables- motors, sensors

	RemoteEV3 ev3;
	RMIRegulatedMotor left;
	RMIRegulatedMotor right;
	EV3GyroSensor gyro ;
	static float[] gyroSample; 
	static SampleProvider angle;
	
	EV3ColorSensor cs ;
	static float[] colorSample;
	static SampleProvider color;
	
	EV3UltrasonicSensor us ;
	float[] ultraSample;
	SampleProvider distance;
	
	 final float CMTOANGLE = (float) (360/(5.5*Math.PI)); //use constant in driver class
	// float maxSpeed = Battery.getVoltage()*100;

	 // creates an object RemoteDriver with left and right motor port inputs, color, gyro and ultra sensor inputs
	/**
	 * 
	 * @param ip	IP of the robot
	 * @param leftPort	left motor port "A"
	 * @param rightPort    right motor port (ex. “D”)
	 * @param gyroPort    gyrosensor port (ex “S3”)
	 * @param ultraPort	ultrasensor port "S2"
	 * @param colorPort	colorsensor port (ex “S1”)
	 * @throws java.rmi.RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public RemoteDriver(String ip, String leftPort, String rightPort,String gyroPort,String ultraPort,String colorPort) 	throws java.rmi.RemoteException, MalformedURLException, NotBoundException{
	 ev3 = new RemoteEV3(ip);
		left= ev3.createRegulatedMotor(leftPort, 'L');
		right= ev3.createRegulatedMotor(rightPort, 'L');
		
		Port p = ev3.getPort(ultraPort);
		us = new EV3UltrasonicSensor(p);
		 distance = us.getDistanceMode(); //gets distance measured by ultrasensor and stores 
		ultraSample = new float[distance.sampleSize()] ;
		
		Port g = ev3.getPort(gyroPort);
		 gyro = new EV3GyroSensor(g);
		angle = gyro.getAngleMode();
		gyroSample = new float[angle.sampleSize()] ;
		
		Port c = ev3.getPort(colorPort);
		 cs = new EV3ColorSensor(c);
		 color = cs.getRedMode();
		colorSample = new float[color.sampleSize()] ;
		}

// sensor data methods
	public static float measureAngle(){
		angle.fetchSample(gyroSample,0);
		//System.out.println(gyroSample[0]);
		return gyroSample[0];
	}
	
	
	public static float measureColor(){
		color.fetchSample(colorSample,0);
		System.out.println(colorSample[0]);
		return colorSample[0];
	}
	
	public float measureDistance(){
		distance.fetchSample(ultraSample,0);
	//	System.out.println(ultraSample[0]);
		return ultraSample[0];
	}

//start of driving and turning classes
/**
	 * 
	 * @param distance	distance the robot drives
	*/
	public void drive(float distance) throws RemoteException{
		left.setSpeed(180);
		right.setSpeed(180);
		float angle= distance*CMTOANGLE;
		left.rotate((int) angle, true);
		right.rotate((int) angle);
		left.stop(true);
		right.stop(false);
	}
	//drives robot forward one rotation
	public void foreward() throws RemoteException{
		left.forward();
		right.forward();
	}
	//pointTurn= moves one wheel to turn robot
/**
	 * 
	 * @param angle	angle of rotation
	*/
	public void pointTurnRight(float angle) throws RemoteException{
	//	left.rotate((int) (Math.toRadians(angle)*11.88*CMTOANGLE));
		gyro.reset();
		left.setSpeed(180);
		while (Math.abs(measureAngle())<(angle-5)){
			left.forward();
		}
		left.stop(false);
	}
	/**
	 * 
	 * @param angle	angle of rotation
	*/
	public void pointTurnLeft(float angle) throws RemoteException{
		//right.rotate((int) (Math.toRadians(angle)*11.88*CMTOANGLE));
		gyro.reset();
		right.setSpeed(180);
		while (Math.abs(measureAngle())<(angle - 5)){
			right.forward();
		}
		right.stop(false);
	}
	//spin= one wheel moves forward while the other moves back to turn the robot
/**
	 * 
	 * @param angle	angle of rotation
	*/
	public void spinRight (float angle) throws RemoteException{
		gyro.reset();
		left.setSpeed(180);
		right.setSpeed(180);
		while (Math.abs(measureAngle())<(angle - 5)){
			left.forward();
			right.backward();
		}
		right.stop(true);
		left.stop(false);
		}
	/**
	 * 
	 * @param angle	angle of rotation
	*/
	public void spinLeft (float angle) throws RemoteException{
		gyro.reset();
		left.setSpeed(180);
		right.setSpeed(180);
		while (Math.abs(measureAngle())<(angle - 5)){
			right.forward();
			left.backward();
		}
		right.stop(true);
		left.stop(false);
		}
		
	//smoothTurn= both wheels move forward to drive the robot in an arc to turn
/**
	 * 
	 * @param angle	angle of rotation
	* @param radius	radius of arc 
	*/
	public void smoothTurnLeft (float angle, float radius) throws RemoteException{
		
	double InnerDistance; //distance of the inner arc
	double OuterDistance; //diatance of outer arc
	float rad= (float) Math.toRadians(angle);
	
	gyro.reset();
	OuterDistance= (rad*radius);
	InnerDistance= (rad*(radius-15));
		
	right.setSpeed((int) (180*(OuterDistance/InnerDistance))); //since time has to be the same, the outer motor has to be faster than the inner
	left.setSpeed(180);
	while (Math.abs(measureAngle())<(angle)){
		left.forward();
		right.forward();
	}
	right.stop(true);
	left.stop(false);
		}
	//same as smoothTurnLeft except the left motor travels faster than the right
	public void smoothTurnRight (float angle, float radius) throws RemoteException{
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
		left.stop(false);
	
	}
	
	public void scan() throws RemoteException{
		// turns 90 deg left and records distances of objects
		while(measureAngle()<90){
			left.setSpeed(100);
			left.backward();
		
			System.out.println(measureDistance());
			}
		left.stop(false);
	}
	
		public void close() throws RemoteException{
		gyro.close();
		cs.close();
		us.close();
		/*left.stop(true);
		right.stop(true);*/
		left.close();
		right.close();
		
	}
		public void depart(){
			//TODO code to drive to nearest star
		}
	
	public static void main(String[] args) {
		
		try {
			RemoteDriver robot = new RemoteDriver(GUI.IP,GUI.setup[0], GUI.setup[1], GUI.setup[2], GUI.setup[3], GUI.setup[4]);
			robot.close();
			
			System.out.println("Connected");
		} catch (RemoteException e) {
			System.out.println("remote");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("url");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("notbound");
			e.printStackTrace();
		}
		
	
		//	System.out.println("Error");
			
			
	}}
            
            	

		

	




