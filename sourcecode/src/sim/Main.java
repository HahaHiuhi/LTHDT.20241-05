package sim;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.*;

public class Main {
    static private Timer timer;
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
        controlPanel.setLayout(new GridLayout(9, 2, 5, 5));

        // Labels and text fields for P, H, C and plants
        JLabel labelP = new JLabel("Plant:");
        JTextField fieldP = new JTextField("0");
        JLabel labelH = new JLabel("Herbivore:");
        JTextField fieldH = new JTextField("0");
        JLabel labelC = new JLabel("Carnivore:");
        JTextField fieldC = new JTextField("0");
        JLabel currentPlanLabel = new JLabel("Current Plan: Custom");
        labelP.setHorizontalAlignment(JTextField.CENTER);
        labelH.setHorizontalAlignment(JTextField.CENTER);
        labelC.setHorizontalAlignment(JTextField.CENTER);

        // Control buttons
        JButton startButton = new JButton("Start / Stop Simulation");
        JButton pauseButton = new JButton("Pause Simulation");
        JButton resumeButton = new JButton("Resume Simulation");
        JButton helpButton = new JButton("Help");
        JButton quitButton = new JButton("Quit");
        JButton hideButton = new JButton("Show / Hide Options");
        JButton choosePlanButton = new JButton("Choose plan ...");

        // Add components to the control panel
        controlPanel.add(labelP);
        controlPanel.add(fieldP);
        controlPanel.add(labelH);
        controlPanel.add(fieldH);
        controlPanel.add(labelC);
        controlPanel.add(fieldC);
        controlPanel.add(hideButton);
        controlPanel.add(startButton);
        controlPanel.add(choosePlanButton);
        controlPanel.add(currentPlanLabel);  // Add it to your panel or layout
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(quitButton);
        controlPanel.add(helpButton);

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
                if(timer != null && timer.isRunning()){
                    timer.stop();
                    myWorld.clearWorld();
                    System.gc();
                    statsArea.append("Simulation stoped\n");
                    return;
                }else if(timer != null && !timer.isRunning()){
                    int plants = Integer.parseInt(fieldP.getText());
                    int herbivores = Integer.parseInt(fieldH.getText());
                    int carnivores = Integer.parseInt(fieldC.getText());
                    myWorld.clearWorld();
                    System.gc();
                    myWorld.spawnOrganisms(plants, herbivores, carnivores);
                    myController.startSimulation();
                    timer.restart();
                    return;
                }
                int plants = Integer.parseInt(fieldP.getText());
                int herbivores = Integer.parseInt(fieldH.getText());
                int carnivores = Integer.parseInt(fieldC.getText());
                myWorld.clearWorld();
                myWorld.spawnOrganisms(plants, herbivores, carnivores);
                myController.startSimulation();
                statsArea.append("Simulation started...\n");

                // Timer to update world stats and view
                timer = new Timer(500, new ActionListener() {
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
                                                   "Energy visualization: 10%\n" +
                                                   "Energy efficiency: 3%\n");
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
                JOptionPane.showMessageDialog(frame, "Help:\nStart - Start the simulation\nPause - Pause the simulation\nResume - Resume the simulation\nQuit - Quit the application");
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

        // Hide button event listener
        hideButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isShowing = startButton.isVisible();
                isShowing = !isShowing;
                startButton.setVisible(isShowing);
                pauseButton.setVisible(isShowing);
                resumeButton.setVisible(isShowing);
                helpButton.setVisible(isShowing);
                choosePlanButton.setVisible(isShowing);
                currentPlanLabel.setVisible(isShowing);
            }
        });

        choosePlanButton.addActionListener(new ActionListener(){
            int choose = 0;
            @Override
            public void actionPerformed(ActionEvent e){
                Vector<Vector<String>> plans = new Vector<>();

                Vector<String> row1 = new Vector<>();
                row1.add("100");
                row1.add("10");
                row1.add("1");
                row1.add("Balanced");
                Vector<String> row2 = new Vector<>();
                row2.add("0");
                row2.add("100");
                row2.add("1");
                row2.add("No plant");
                Vector<String> row3 = new Vector<>();
                row3.add("100");
                row3.add("10");
                row3.add("0");
                row3.add("No carnivore");
                plans.add(row1);
                plans.add(row2);
                plans.add(row3);
                fieldP.setText(plans.get(choose).get(0));
                fieldH.setText(plans.get(choose).get(1));
                fieldC.setText(plans.get(choose).get(2));
                currentPlanLabel.setText("Current plan: " + plans.get(choose).get(3));
                choose = (choose + 1) % 3;
            }
        });

        fieldP.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(!fieldP.getText().isEmpty()){currentPlanLabel.setText("Current plan: Custom");}
            }
        });
        fieldH.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(!fieldH.getText().isEmpty()){currentPlanLabel.setText("Current plan: Custom");}
            }
        });
        fieldC.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(!fieldC.getText().isEmpty()){currentPlanLabel.setText("Current plan: Custom");}
            }
        });
    }
}
