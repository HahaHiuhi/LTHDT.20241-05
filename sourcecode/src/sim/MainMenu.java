package sim;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import World.World;

public class MainMenu {

    public MainMenu() {
        // Create the JFrame for the main window
        JFrame frame = new JFrame("Ecosystem Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10)); // Add padding between components

        // Create the control panel (buttons and inputs)
        ControlPanel controlPanel = new ControlPanel();

        // Panel for the menu buttons (start and quit)
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS)); // Vertical button layout
        menu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around buttons

        // Add buttons for starting and quitting the simulation
        JButton startButton = createButton("Start Simulation");
        JButton quitButton = createButton("Quit");

        menu.add(startButton);
        menu.add(Box.createVerticalStrut(15)); // Add space between buttons
        menu.add(quitButton);

        // Add components to the JFrame
        frame.add(menu, BorderLayout.WEST); // Add the menu (buttons) to the left
        frame.add(controlPanel, BorderLayout.EAST); // Add the control panel to the right

        frame.pack(); // Pack to fit the components
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true); // Make the frame visible

        // Start button event listener
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int plants = Integer.parseInt(ControlPanel.fieldP.getText());
                int herbivores = Integer.parseInt(ControlPanel.fieldH.getText());
                int carnivores = Integer.parseInt(ControlPanel.fieldC.getText());
            	if (plants + herbivores + carnivores > World.CAP) {
					JOptionPane.showMessageDialog(null, "Total number of organisms exceeds the limit of " + World.CAP,
							"ILLEGAL ORGANISM NUMBER", JOptionPane.ERROR_MESSAGE);
					return;
				}
            	SimulationScreen.setBirthDeath(ControlPanel.birthDeathModifier);
                frame.dispose(); // Close the main menu frame
                new SimulationScreen(); // Launch the simulation screen
               
                
            }
        });

        // Quit button event listener
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to quit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0); // Exit the application
                }
            }
        });
    }

    //Create Button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        button.setPreferredSize(new Dimension(150, 40)); // Set preferred size
        return button;
    }

    public static void main(String[] args) {
        // Launch the main menu
        new MainMenu();
    }
}
