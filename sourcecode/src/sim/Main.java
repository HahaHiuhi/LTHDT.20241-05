package sim;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create the world simulation
        World myWorld = new World();
        
        // Create the view to render the world (30x30 grid)
        WorldRenderer myView = new WorldRenderer(myWorld);
        
        // Create the controller for the simulation
        Simulator myController = new Simulator(myWorld, myView);

        // Create the main JFrame for the simulation
        JFrame frame = new JFrame("Organism Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Control panel for input and simulation controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10, 1, 5, 5));

        // Labels and text fields for P, H, and C
        JLabel labelP = new JLabel("Plant:");
        JTextField fieldP = new JTextField("0");
        JLabel labelH = new JLabel("Herbivore:");
        JTextField fieldH = new JTextField("0");
        JLabel labelC = new JLabel("Carnivore:");
        JTextField fieldC = new JTextField("0");

        fieldP.setEditable(true);
        fieldH.setEditable(true);
        fieldC.setEditable(true);

        // Control buttons
        JButton startButton = new JButton("Start Simulation");
        JButton pauseButton = new JButton("Pause Simulation");
        JButton resumeButton = new JButton("Resume Simulation");
        JButton helpButton = new JButton("Help");
        JButton quitButton = new JButton("Quit");

        // Add components to the control panel
        controlPanel.add(labelP);
        controlPanel.add(fieldP);
        controlPanel.add(labelH);
        controlPanel.add(fieldH);
        controlPanel.add(labelC);
        controlPanel.add(fieldC);
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(helpButton);
        controlPanel.add(quitButton);

        // Panel for displaying stats
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setBorder(BorderFactory.createTitledBorder("Stats"));

        // Set preferred size for the stats area to ensure it takes more space
        statsArea.setPreferredSize(new Dimension(300, 0)); // Adjust width as needed (300px for example)

        // Add components to the JFrame
        frame.add(controlPanel, BorderLayout.WEST);
        frame.add(myView, BorderLayout.CENTER); // Add the view (30x30 grid) here
        frame.add(statsArea, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);

        // Start button event listener
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int plants = Integer.parseInt(fieldP.getText());
                int herbivores = Integer.parseInt(fieldH.getText());
                int carnivores = Integer.parseInt(fieldC.getText());
                myWorld.clearWorld();
                myWorld.spawnOrganisms(plants, herbivores, carnivores);
                myController.startSimulation();
                statsArea.append("Simulation started...\n");

                // Timer to update world stats and view
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Update the world view and stats
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                myView.repaint(); // Trigger repaint to update the view
                                statsArea.setText("Plants: " + myWorld.getPlantsCount() + "\n" +
                                                   "Herbivores: " + myWorld.getHerbivoresCount() + "\n" +
                                                   "Carnivores: " + myWorld.getCarnivoresCount() + "\n" +
         
                                                   "Energy efficiency: " + myController.calculateStats() +"%\n");
                            }
                        });
                    }
                });
                timer.start();
            }
        });

        // Pause button event listener
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myController.pauseSimulation();
                statsArea.append("Simulation paused...\n");
            }
        });

        // Resume button event listener
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myController.resumeSimulation();
                statsArea.append("Simulation resumed...\n");
            }
        });

        // Help button event listener
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Help:\nStart - Start the simulation\nPause - Pause the simulation\nResume - Resume the simulation\nQuit - Quit the application\nPlants get energy from sunlight\nHerbivores consume plants\nCarnivores consume herbivores\nThe predators only get 10% energy of their preys");
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
}
