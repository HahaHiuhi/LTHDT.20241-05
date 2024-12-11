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

    // Constructor
    public WorldController(World world, WorldView view) {
        this.world = world;
        this.view = view;
        this.timer = new Timer(); // Initialize the timer
        isRunning = false;
    }

    // Start the simulation
    public void startSimulation() {
        stopSimulation(); // Stop any running timers

        // Spawn organisms (10 carnivores in this example, you can adjust this as needed)
        
            this.world.spawnOrganisms(50, 30, 5); // Example: 10 plants, 5 herbivores, 10 carnivores
        

        // Schedule regular ticks
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (world.isDead()) {
                    stopSimulation(); // Stop when the world is dead
                    System.out.println("World is dead, simulation stopped.");
                } else if(!isUpdating) { 
                    isUpdating = true; // Indicate that an update is in progress
                    world.update();    // Update the world
                    view.repaint();; // Update the graphical view (uncomment this line if needed)
                    isUpdating = false; // Indicate that the update has finished
                }
            }
        }, 0, tickDuration); // First tick runs immediately, then repeats every `tickDuration` milliseconds

        System.out.println("Simulation started with tick duration: " + tickDuration + " ms");
    }

    // Stop the simulation
    public void stopSimulation() {
        if (timer != null) {
            timer.cancel(); // Cancel the current timer
            System.out.println("Simulation stopped.");
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
