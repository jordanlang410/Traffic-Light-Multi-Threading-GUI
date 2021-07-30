/* File Name: Cars.java
 * Author: Lang, Jordan
 * Project Name: Project 2 CMSC335
 * Date: 06/28/2021
 * Purpose/Description: This class is used to track the cars position, speed, and the 
 * traffic light color when necessary for the car to stop.  The x position and the traffic
 * light color are retrieved from TrafficLightGUI.java and used to output the proper
 * information.
*/
package Project3Cmsc335;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.JLabel;

public class Cars implements Runnable {

	JLabel carInfoLabel;
	JLabel interSectionInfo;

	private double xPosition;
	private double yPosition = 0;
	private double carSpeed;
	private double time;
	private Color lightColor;
	private String threadName;

	Thread thread;

	Cars(String threadName, JLabel carInfo, JLabel interSectionInfo) {
		thread = new Thread();
		this.carInfoLabel = carInfo;
		this.interSectionInfo = interSectionInfo;
		this.threadName = threadName;
	}

	synchronized void checkLightColor() {

		time = time + .5;
		carSpeed = 2 * 12;
		// xPosition = carSpeed * time;

		if (threadName == "car1") {
			xPosition = carSpeed * time;
		}

		else if (threadName == "car2") {
			xPosition = carSpeed * time + 400; // add 400 to start car at a different location
		}

		else if (threadName == "car3") {
			xPosition = carSpeed * time + 600; // add 600 to start car at a different location
		}

		// use if else statement to find when cars are at light 1 and change speed to 0
		if (threadName == "car1" && lightColor == Color.red && ((xPosition >= 1000 && xPosition <= 1200))) {
			carSpeed = 0;

		}

		else if (threadName == "car2" && lightColor == Color.red && ((xPosition >= 1000 && xPosition <= 1200))) {
			carSpeed = 0;

		}

		else if (threadName == "car3" && lightColor == Color.red && ((xPosition >= 1000 && xPosition <= 1200))) {
			carSpeed = 0;
		}

		// use if else statement to find when cars are at light 2 and change speed to 0
		else if (threadName == "car1" && lightColor == Color.red && ((xPosition >= 2200 && xPosition <= 2400))) {
			carSpeed = 0;
		}

		else if (threadName == "car2" && lightColor == Color.red && ((xPosition >= 2200 && xPosition <= 2400))) {
			carSpeed = 0;
		}

		else if (threadName == "car3" && lightColor == Color.red && ((xPosition >= 2200 && xPosition <= 2400))) {
			carSpeed = 0;
		}

		// use if else statement to find when cars are at light 3 and change speed to 0
		else if (threadName == "car1" && lightColor == Color.red && ((xPosition >= 3400 && xPosition <= 3600))) {
			carSpeed = 0;
		}

		else if (threadName == "car2" && lightColor == Color.red && ((xPosition >= 3400 && xPosition <= 3600))) {
			carSpeed = 0;
		}

		else if (threadName == "car3" && lightColor == Color.red && ((xPosition >= 3400 && xPosition <= 3600))) {
			carSpeed = 0;
		}

		else {
			// Assume car is traveling at 24 MPH
			carSpeed = 24;
		}

		carInfoLabel.setText(
				String.format(threadName + ": x = %.2f, y = %.2f, speed = %.2f mph", xPosition, yPosition, carSpeed));
	}

	synchronized Color getLightClr(Color lightColor) {
		this.lightColor = lightColor;
		return this.lightColor;
	}

	@Override
	public void run() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	synchronized double getXPosition() {
		return this.xPosition;
	}

	// check where each car is and return if it is at an intersection
	synchronized void checkCarLocation() {

		if ((xPosition >= 1000 && xPosition <= 1200)) {
			interSectionInfo.setText("-This car is at Intersection 1");
		}

		else if ((xPosition >= 2200 && xPosition <= 2400)) {
			interSectionInfo.setText("-This car is at Intersection 2");

		}

		else if ((xPosition >= 3400 && xPosition <= 3600)) {
			interSectionInfo.setText("-This car is at Intersection 3");

		}

		// space out the GUI
		else {
			interSectionInfo.setText("                                                               ");
		}

	}

}
