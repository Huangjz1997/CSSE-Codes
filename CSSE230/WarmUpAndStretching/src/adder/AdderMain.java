package adder;

// Import classes needed for appearance
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// import container for all of the buttons.

/**
 * A simple calculator that only does addition of non-negative numbers.
 * 
 * DONE
 * 
 * @author Claude Anderson and Yilun Wu. Created Oct 12, 2011.
 */
public class AdderMain {

	private static long sum = 0;
	static boolean isPlus = false; // Is last click is '+' True if it is.

	/**
	 * Set up the AdderGUI to respond to button clicks.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		final int WIDTH = 300, HEIGHT = 450;
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("Adder");

		// Create the text field that displays numbers.
		final JTextField display = new JTextField("0", 20);
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setFont(new Font("Helvetica", Font.BOLD, 24));

		// Make a panel to hold the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

		Font f = new Font("Helvetica", Font.BOLD, 48);

		// Create the top nine buttons (1-9) and add them to the panel.
		// label each button with a string representing the button's value.
		ArrayList<JButton> buttonList = new ArrayList<JButton>();

		// Create and add the bottom three buttons.
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				JButton b = new JButton(7 - 3 * row + col + "");
				b.setFont(f);
				buttonList.add(b);
				buttonPanel.add(b);
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (AdderMain.isPlus) {
							// if last click is Plus, that means we should clear
							// the display
							display.setText(b.getText());
							AdderMain.isPlus = false;
						} else {
							// Just add this digit to display
							// If display is just '0' we need to have sth to do
							if (display.getText().equals("0"))
								display.setText(b.getText());
							else
								display.setText(display.getText() + b.getText());
						}
					}

				});
			}
		}
		String[] remainingButtonLabels = { "C", "0", "+" };
		for (String s : remainingButtonLabels) {
			JButton b = new JButton(s);
			b.setFont(f);
			buttonList.add(b);
			buttonPanel.add(b);

			if (s.equals("C"))
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// clear it to the original status
						isPlus = false;
						AdderMain.sum = 0;
						display.setText("0");
					}

				});
			if (s.equals("0"))
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// looks like the digits before
						if (AdderMain.isPlus) {
							display.setText(b.getText());
							AdderMain.isPlus = false;
						} else {
							if (display.getText().equals("0"))
								display.setText(b.getText());
							else
								display.setText(display.getText() + b.getText());
						}
					}

				});
			if (s.equals("+"))
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (!AdderMain.isPlus) {
							// Add the display and sum
							AdderMain.sum += Integer.parseInt(display.getText());
							display.setText(AdderMain.sum + "");
						}
						AdderMain.isPlus = true;
					}

				});
		}
		// Add both components to the frame.
		frame.add(buttonPanel);
		frame.add(display, BorderLayout.NORTH);
		frame.add(buttonPanel);

		// calculator":
		// Most of the additional code (but not all) can be written below this
		// point)
		// 1. Pressing C should set both the displayed value and the sum to 0.
		// 2. Pressing + adds the current displayed value to the sum and
		// displays the new sum.
		// 3. Pressing a number key works like on a real calculator.
		// The value of the number represented by the sequence of key presses is
		// displayed on the screen.

		/*
		 * Example of a sequence of button presses and what should be displayed
		 * after each:
		 * 
		 * 0 (initial display before any buttons are pressed) 2 2 3 23 + 23 5 5
		 * 6 56 + 79 0 0 0 0 1 1 8 18 C 0 3 3 + 3 2 2 + 5
		 * 
		 */

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
