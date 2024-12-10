package sim;

import java.awt.BorderLayout;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        // Create a new world with 30x30 grid
        World myWorld = new World();
        
        // Create the view to render the world
        WorldView myView = new WorldView(myWorld);

        // Create a controller to manage the simulation
        WorldController myController = new WorldController(myWorld, myView);

        // Set up the frame
        JFrame frame = new JFrame("Organism Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout()); // Use BorderLayout for flexibility
        frame.getContentPane().add(myView, BorderLayout.CENTER); // Add view in the center
        frame.pack();
        frame.setVisible(true);

        // Start the simulation
        myController.startSimulation();
    }
}