package sim;

import java.util.Timer;
import java.util.TimerTask;

public class WorldController {
    private World world;         // The ecosystem object
    private WorldView view;      // The graphical view
    private Timer timer;         // Timer to control ticks
    protected volatile boolean isRunning;

    private static final int DEFAULT_TICK_DURATION = 500; // 500 milliseconds (2 seconds)
    private int tickDuration = DEFAULT_TICK_DURATION;     // Can be adjusted by the user
    private boolean isUpdating = false; // Flag to track if the world update is in progress
    private volatile boolean isPaused = false; // Flag to track if the simulation is paused

    // Constructor
    public WorldController(World world, WorldView view) {
        this.world = world;
        this.view = view;
        this.timer = new Timer(); // Initialize the timer
        isRunning = false;
    }

    // Start the simulation
    public void startSimulation() {
        world.restartSimulation();
        world.setState(1);
        stopSimulation(); // Stop any running timers

        // Spawn organisms (10 carnivores in this example, you can adjust this as needed)
        
            this.world.spawnOrganisms(20, 70, 5); // Example: 10 plants, 5 herbivores, 10 carnivores
        

        // Schedule regular ticks
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (world.isDead()) {
                    stopSimulation(); // Stop when the world is dead
                    System.out.println("World is dead, simulation stopped.");
                } else if(!isUpdating && !isPaused) { 
                    isUpdating = true; // Indicate that an update is in progress
                    world.update();    // Update the world
                    view.repaint();; // Update the graphical view (uncomment this line if needed)
                    isUpdating = false; // Indicate that the update has finished
                } else if (isPaused) {
                    System.out.println("Simulation is paused.");
                }
            }
        }, 0, tickDuration); // First tick runs immediately, then repeats every `tickDuration` milliseconds

        System.out.println("Simulation started with tick duration: " + tickDuration + " ms");
    }

    public void startSimulation(int numPlant, int numHerbivore, int numCarnivore) {
        world.restartSimulation();
        world.setState(1);
        stopSimulation(); // Stop any running timers

        // Spawn organisms (10 carnivores in this example, you can adjust this as needed)
        
            this.world.spawnOrganisms(numPlant, numHerbivore, numCarnivore); // Example: 10 plants, 5 herbivores, 10 carnivores
        

        // Schedule regular ticks
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (world.isDead()) {
                    stopSimulation(); // Stop when the world is dead
                    System.out.println("World is dead, simulation stopped.");
                } else if(!isUpdating && !isPaused) { 
                    isUpdating = true; // Indicate that an update is in progress
                    world.update();    // Update the world
                    view.repaint();; // Update the graphical view (uncomment this line if needed)
                    isUpdating = false; // Indicate that the update has finished
                } else if (isPaused) {
                    System.out.println("Simulation is paused.");
                }
            }
        }, 0, tickDuration); // First tick runs immediately, then repeats every `tickDuration` milliseconds

        System.out.println("Simulation started with tick duration: " + tickDuration + " ms");
    }

    // Stop the simulation
    public void stopSimulation() {
        if (timer != null) {
            timer.cancel(); // Cancel the current timer
            timer = null;
            System.out.println("Simulation stopped.");
        }
    }

    // Pause the simulation (stop the TimerTask from executing)
    public void pauseSimulation() {
        isPaused = true;
        System.out.println("Simulation paused.");
    }

    // Resume the simulation (resume TimerTask execution)
    public void resumeSimulation() {
        if (isPaused) {
            isPaused = false;
            System.out.println("Simulation resumed.");
        }
    }

    // Calculate and print stats about the world
    public void calculateStats() {
        int numOrganisms = world.getOrganisms().size(); // Get the number of organisms
        System.out.println("Number of organisms: " + numOrganisms);
        // Add other statistics like total energy, organisms by type, etc.
    }

    // Adjust the tick duration
    public void setTickDuration(int newTickDuration) {
        if (newTickDuration <= 0) {
            throw new IllegalArgumentException("Tick duration must be greater than 0.");
        }
        this.tickDuration = newTickDuration;
        System.out.println("Tick duration set to: " + newTickDuration + " ms");
        startSimulation(); // Restart simulation with the new tick duration
    }
}