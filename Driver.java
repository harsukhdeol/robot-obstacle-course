package legos;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Driver {
	

	static EV3LargeRegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.D);
	static EV3LargeRegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.A);


			
			public static void drive(float distance) {
				float rotations = (float) (distance/(5.5 * Math.PI));
				int angle = (int) (rotations * 2 * Math.PI);
				
				left.rotate(angle, true);
				right.rotate(angle);
				
				left.stop(true);
				right.stop();
				
			}

		


	public static void main(String[] args) {
		// TODO Auto-generated method stub
drive (100);
	}

}