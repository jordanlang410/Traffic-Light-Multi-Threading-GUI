/* File Name: TrafficLightPanel.java
 * Author: Lang, Jordan
 * Project Name: Project 2 CMSC335
 * Date: 06/28/2021
 * Purpose/Description: This class is used to create the traffic lights which are used
 * in TrafficLightGUI.  It also assists in the implementation of the traffic light threads
 * through getting, setting, and changing the colors.
*/
package Project3Cmsc335;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

enum TrafficLightColor {
	RED, GREEN, YELLOW
}

public class TrafficLightPanel extends JComponent implements Runnable {

	private TrafficLightColor currentLightColor;
	private boolean stopRunning = false;
	private boolean lightChange = false;
	private String threadName;

	Color green = Color.green;
	Color yellow = Color.gray;
	Color red = Color.red;

	// Constructor to call in TrafficLightGUI class
	public TrafficLightPanel(String threadName, TrafficLightColor currentLightColor) {
		
		this.threadName = threadName;
		this.currentLightColor = currentLightColor;
	}
	
	public void run() {

		 Thread.currentThread().setName(this.threadName);

		while (!stopRunning) {
			try {
				// use a switch to decide how long each light should stay a color
				switch (currentLightColor) {

				case GREEN:
					System.out.println(threadName + " will be Green for 10 seconds.");
					Thread.sleep(10000);
					break;

				case YELLOW:
					System.out.println(threadName + " will be Yellow for 2 seconds.");
					Thread.sleep(2000);
					break;

				case RED:
					System.out.println(threadName + " will be Red for 10 seconds.");
					Thread.sleep(7000);
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getNextColor(); // after each loop, change the color (outside the switch statement)
		}
	}

	// Change the colors of the stop lights
	synchronized void getNextColor() {

		switch (currentLightColor) {
		// if the light is red, change the color to green
		case RED:
			currentLightColor = TrafficLightColor.GREEN;
			break;

		// if the light is yellow, change the color to red
		case YELLOW:
			currentLightColor = TrafficLightColor.RED;
			break;

		// if the light is green, change the color to yellow
		case GREEN:
			currentLightColor = TrafficLightColor.YELLOW;
			break;
		}

		lightChange = true;
		notify();
	}

	// set the colors based on what they have changed to (gray the others which are not active)
	synchronized void setColors() {

		switch (currentLightColor) {

		case RED:
			red = Color.red;
			yellow = Color.gray;
			green = Color.gray;
			break;

		case YELLOW:
			yellow = Color.blue; // make blue so not to blend in with the background
			red = Color.gray;
			green = Color.gray;
			break;

		case GREEN:
			green = Color.green;
			yellow = Color.gray;
			red = Color.gray;
			break;

		}
		repaint(); // make sure to call repaint method to repaint the colors
		lightChange = false;
	}

	// get the current light color. Use this method to track the light color for the cars
	synchronized Color getCurrentColor() {

		switch (currentLightColor) {

		case RED:
			return Color.red;

		case YELLOW:
			return Color.blue;

		case GREEN:
			return Color.green;

		}
		return null;
	}

	synchronized void cancel() {
		stopRunning = true;
	}

	// paint the lights
	synchronized public void paintComponent(Graphics g) {

		g.setColor(Color.yellow);
		g.fillRect(0, 0, 150, 250);

		g.setColor(Color.black);
		g.drawRect(0, 0, 150, 250);

		g.setColor(green);
		g.fillOval(8, 4, 15, 15);

		g.setColor(yellow);
		g.fillOval(8, 24, 15, 15);

		g.setColor(red);
		g.fillOval(8, 44, 15, 15);

	}

}
