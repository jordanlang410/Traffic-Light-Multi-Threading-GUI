/* File Name: TrafficLightGUI.java
 * Author: Lang, Jordan
 * Project Name: Project 2 CMSC335
 * Date: 06/28/2021
 * Purpose/Description: This class creates the GUI interface which allows the user to 
 * see the 3 cars positions and speed. It also displays the current light color via
 * the traffic light gui so the user knows what color the intersection light is.
 * It allows the user to start or stop the program. It creates 3 car threads and
 *  3 traffic light threads which run independently of each other.
*/
package Project3Cmsc335;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TrafficLightGUI extends JFrame implements ActionListener {

	private JButton startButton, pauseButton, stopButton, resumeButton, addCarButton;
	private JLabel car1Label, car2Label, car3Label, timeLabel;
	private JLabel car1IntLabel, car2IntLabel, car3IntLabel;
	private JTextField jtf;
	private Cars car1, car2, car3;
	private boolean stopRunning = false;
	private boolean threadPaused = false;
	private Thread mainThread, car1Thread, car2Thread, car3Thread;
	private Thread light1Thread, light2Thread, light3Thread, clockThread;

	// Create 3 intersection/light objects.....start all the lights as green and run on same cycle
	TrafficLightPanel light1 = new TrafficLightPanel("Light 1", TrafficLightColor.GREEN);
	TrafficLightPanel light2 = new TrafficLightPanel("Light 2", TrafficLightColor.GREEN);
	TrafficLightPanel light3 = new TrafficLightPanel("Light 3", TrafficLightColor.GREEN);

	public TrafficLightGUI() {

		super("Traffic Lights GUI");

		setSize(990, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.startButton = new JButton("Start");
		this.pauseButton = new JButton("Pause");
		this.stopButton = new JButton("Stop");
		this.resumeButton = new JButton("Resume");
		this.addCarButton = new JButton("Add Car");

		this.car1Label = new JLabel();
		this.car1IntLabel = new JLabel();
		this.car2IntLabel = new JLabel();
		this.car3IntLabel = new JLabel();
		this.car2Label = new JLabel();
		this.car3Label = new JLabel();
		this.timeLabel = new JLabel();

		this.jtf = new JTextField();

		JPanel mainPanel = new JPanel();
		add(mainPanel);

		mainPanel.add(northPanel(), BorderLayout.NORTH);
		mainPanel.add(centerPanel(), BorderLayout.CENTER);
		mainPanel.add(southPanel(), BorderLayout.SOUTH);

	}

	// create JPanel upper layout
	private JPanel northPanel() {

		JPanel northPanel = new JPanel();

		northPanel.add(car1Label);
		northPanel.add(car1IntLabel);
		northPanel.add(car2Label);
		northPanel.add(car2IntLabel);

		return northPanel;

	}

	// create JPanel center layout
	private JPanel centerPanel() {
		JPanel centerPanel = new JPanel();

		centerPanel.add(car3Label);
		centerPanel.add(car3IntLabel);

		return centerPanel;
	}

	// create JPanel center layout
	private JPanel southPanel() {
		JPanel southPanel = new JPanel();

		light1.setPreferredSize(new Dimension(30, 60));
		light2.setPreferredSize(new Dimension(30, 60));
		light3.setPreferredSize(new Dimension(30, 60));

		southPanel.add(light1);
		southPanel.add(light2);
		southPanel.add(light3);

		southPanel.add(startButton);
		southPanel.add(pauseButton);
		southPanel.add(stopButton);
		southPanel.add(resumeButton);
		southPanel.add(addCarButton);
		southPanel.add(timeLabel);
		southPanel.add(jtf);

		startButton.addActionListener(this);
		pauseButton.addActionListener(this);
		stopButton.addActionListener(this);
		resumeButton.addActionListener(this);
		addCarButton.addActionListener(this);

		return southPanel;
	}

	// Create actions based on the button the user clicks
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == startButton) {

			// Create light threads
			light1Thread = new Thread(light1);
			light2Thread = new Thread(light2);
			light3Thread = new Thread(light3);

			// Start the light threads
			light1Thread.start();
			System.out.println("Starting light 1 thread.");

			light2Thread.start();
			System.out.println("Starting light 2 thread.");

			light3Thread.start();
			System.out.println("Starting light 3 thread.");

			// Create car threads
			car1Thread = new Thread(car1);
			car2Thread = new Thread(car2);
			car3Thread = new Thread(car3);

			// Start the car threads
			car1Thread.start();
			System.out.println("Starting car 1 thread.");

			car2Thread.start();
			System.out.println("Starting car 2 thread.");

			car3Thread.start();
			System.out.println("Starting car 3 thread.");

			// use Cars constructor to track info and output to GUI
			car1 = new Cars("car1", car1Label, car1IntLabel);
			car2 = new Cars("car2", car2Label, car2IntLabel);
			car3 = new Cars("car3", car3Label, car3IntLabel);

			// start and create the time thread
			Time clock1 = new Time(jtf);
			clockThread = new Thread(clock1);
			clockThread.start();
			System.out.println("The time thread has started.");

			mainThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (!stopRunning) {

						light1.setColors();
						light2.setColors();
						light3.setColors();

						// Use the methods from Cars.java to pull the x position and light color
						// Use these to track if the car should be stopped or not
						car1.getXPosition();
						car1.getLightClr(light1.getCurrentColor());

						car2.getXPosition();
						car2.getLightClr(light2.getCurrentColor());

						car3.getXPosition();
						car3.getLightClr(light3.getCurrentColor());

						// slow the x value down
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						car1.checkCarLocation();
						car2.checkCarLocation();
						car3.checkCarLocation();

						car1.checkLightColor();
						car2.checkLightColor();
						car3.checkLightColor();

					}
				}

			});

			mainThread.start();
		}

		else if (e.getSource() == stopButton) {

			// call the interrupt method to stop all the threads
			interruptThreads();
		}

		// This is supposed to pause the application, but unfortunately I can't
		// find a way to keep it from crashing/not responding to keep me from being able to resume
		else if (e.getSource() == pauseButton) {

			System.out.println("suspend the car and light threads");

			synchronized (mainThread) {
				try {

					mainThread.wait();
					car1Thread.wait();
					car2Thread.wait();
					car3Thread.wait();
					light1Thread.wait();
					light2Thread.wait();
					light3Thread.wait(); 
					clockThread.wait();

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

		}

		// This is supposed to notify the waiting threads to resume, but because I //
		// cannot seem to get the pause button to work meaning this is not used.
		if (e.getSource() == resumeButton) {

			if (threadPaused = true) {

				System.out.println("Resume the car and light threads");

				mainThread.notify();
				car1Thread.notify();
				car2Thread.notify();
				car3Thread.notify();
				light1Thread.notify();
				light2Thread.notify();
				light3Thread.notify();

			}
		}
		
		// Should add cars, but was unable to do so
		if (e.getSource() == addCarButton) {
			
			addCar();
			
			//JLabel label = new JLabel("car" + count);	
		}
	}

	private void addCar() {
		
		Cars car4;
		Thread car4Thread;
		car4Thread = new Thread(car1);
		car4Thread.start();
		JLabel car4Label;
		JLabel car4IntLabel;
		
	}

	// Method to interrupt, cancel, and stop all threads
	public void interruptThreads() {
		System.out.println("The application has been stopped.");
		mainThread.interrupt();
		car1Thread.interrupt();
		car2Thread.interrupt();
		car3Thread.interrupt();
		light1Thread.interrupt();
		light2Thread.interrupt();
		light3Thread.interrupt();
		clockThread.interrupt();
		light1.cancel();
		light2.cancel();
		light3.cancel();
		stopRunning = true;

	}

	public static void main(String[] args) {
		TrafficLightGUI display = new TrafficLightGUI();
		display.setVisible(true);
	}

}