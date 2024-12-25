package sim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Organism.Carnivore;
import Organism.Herbivore;
import Organism.Plant;
import World.World;

public class SimulationScreen {
     World myWorld ;
 
     WorldRenderer myView;
     
     Simulator myController;

	 private Timer updateTimer;
	
	 
     public SimulationScreen() {
    	    // Create the world simulation
    	 JFrame frame = new JFrame("Ecosystem Simulator");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setLayout(new BorderLayout());
         World myWorld = new World();
         
         // Create the view to render the world (30x30 grid)
         WorldRenderer myView = new WorldRenderer();
         
         // Panel for displaying stats
         JTextArea statsArea = new JTextArea();
         statsArea.setEditable(false);
         statsArea.setBorder(BorderFactory.createTitledBorder("Stats"));
         statsArea.setPreferredSize(new Dimension(300, 0)); // Adjust width as needed (300px for example)
         
         // Create the controller for the simulation
         Simulator myController = new Simulator(myWorld, myView);
    	 JButton pauseButton = new JButton("Pause Simulation");
         JButton resumeButton = new JButton("Resume Simulation");
         JButton quitButton = new JButton("Quit");
         
         JPanel stopResume = new JPanel();
         stopResume.add(resumeButton);
         stopResume.add(pauseButton);
         stopResume.add(quitButton);
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
         frame.add(stopResume, BorderLayout.WEST);
         frame.add(myView, BorderLayout.CENTER); // Add the view (30x30 grid) here
         frame.add(statsArea, BorderLayout.EAST);
         
         frame.pack();
         frame.setVisible(true);
         
         if (updateTimer != null) {
             updateTimer.stop();
             updateTimer = null; // Nullify to avoid potential reuse
         }

         try {
             // Parse input values from text fields
             int plants = Integer.parseInt( ControlPanel.fieldP.getText());
             int herbivores = Integer.parseInt( ControlPanel.fieldH.getText());
             int carnivores = Integer.parseInt( ControlPanel.fieldC.getText());

             // Clear the current world and spawn organisms
             myWorld.clearWorld();
             myWorld.spawnOrganisms(plants, herbivores, carnivores);

             // Start the simulation (e.g., by invoking a controller method)
             myController.startSimulation();
             statsArea.append("Simulation started...\n");

             // Initialize the timer for periodic updates
             updateTimer = new Timer(500, new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     // Run the update in the EDT for thread-safety
                     SwingUtilities.invokeLater(() -> {
                         myView.repaint(); // Safely update the UI view
                         statsArea.setText(  // Safely update the stats in the UI
                             "Plants: " + Plant.count+ "\n" +
                             "Herbivores: " + Herbivore.count + "\n" +
                             "Carnivores: " + Carnivore.count + "\n" +
                             "Energy from Sun to Plants: " + Plant.energyGet+ "\n" +
                             "Energy from Plants to Herbivores: " + Herbivore.energyGet + "\n" +
                             "Energy from Herbivores Carnivores: " + Carnivore.energyGet + "\n" +
                             "Plant birth rate: " + (double)100*Plant.birth/Plant.total + "%\n"+
                             "Herbivore birth rate: " + String.format("%.2f", (double)100 * Herbivore.birth / Herbivore.total) + "%\n" +
                             "Carnivore birth rate: " + String.format("%.2f", (double)100 * Carnivore.birth / Carnivore.total) + "%\n" +
                             "Plant death rate: " + String.format("%.2f", (double)100 * Plant.death / Plant.total) + "%\n" +
                             "Herbivore death rate: " + String.format("%.2f", (double)100 * Herbivore.death / Herbivore.total) + "%\n" +
                             "Carnivore death rate: " + String.format("%.2f", (double)100 * Carnivore.death / Carnivore.total) + "%\n"

                         );
                     });
                 }
             });
             updateTimer.start(); // Start the timer to begin updates

         } catch (NumberFormatException ex) {
             // Handle invalid number input gracefully
             JOptionPane.showMessageDialog(
                 null,
                 "Please enter valid numbers for plants, herbivores, and carnivores.",
                 "Input Error",
                 JOptionPane.ERROR_MESSAGE
             );
         }
     
     // Quit button event listener
         quitButton.addActionListener(new ActionListener() {
        	    @Override
        	    public void actionPerformed(ActionEvent e) {
        	        int result = JOptionPane.showConfirmDialog(frame,
        	                "Are you sure you want to quit?",
        	                "Confirm Exit",
        	                JOptionPane.YES_NO_OPTION);

        	        if (result == JOptionPane.YES_OPTION) {
        	        	myController.stopSimulation();
        	            frame.dispose(); // Close the current simulation screen
        	            new MainMenu(); // Open the main menu
        	        }
        	    }
        	});

     }
     
     protected static void setBirthDeath(int x) {
    	 World.birthDeath = x;
     }
     
     public static void main(String[] args) {
         new SimulationScreen();
     } 
 }