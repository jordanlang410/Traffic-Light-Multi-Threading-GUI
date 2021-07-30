/* File Name: Time.java
 * Author: Lang, Jordan
 * Project Name: Project 2 CMSC335
 * Date: 06/28/2021
 * Purpose/Description: This class assists in the implementation of a time thread
 *  to allow for the current date and time to print to the GUI.  
 *  This is done in the TrafficLightGUI class.
*/
package Project3Cmsc335;

import java.util.Date;
import javax.swing.JTextField;

public class Time implements Runnable {

	JTextField jtf;

	public Time(JTextField jtf) {
		this.jtf = jtf;
	}

	// use Java.util.Date to output the current date and time in a JTextField
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			Date date = new Date();
			jtf.setText(date.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
