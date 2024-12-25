package sim;


import java.util.Timer;
import java.util.TimerTask;

public class Simulator {
    private World world;         // The ecosystem world object
    private WorldRenderer view;      // The graphical view
    private Timer timer;         // Timer to control ticks
    protected volatile boolean isRunning;

    private static final int DEFAULT_TICK_DURATION = 500; // 500 milliseconds (0.5 seconds)
    private int tickDuration = DEFAULT_TICK_DURATION;     // Can be adjusted by the user
    private boolean isUpdating = false; // Flag to track if the world update is in progress
	private boolean isPaused;

    // Constructor
    public Simulator(World world, WorldRenderer view) {
        this.world = world;
        this.view = view;
        this.timer = new Timer(); // Initialize the timer
        isRunning = false;
    }

    // Start the simulation
    public void startSimulation() {
        endSimulation(); // Stop any running timers
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (world.isDead()) {
                    endSimulation(); // Stop when the world is dead
                    System.out.println("World is dead, simulation stopped.");
                } else if(!isUpdating && !isPaused) { 
                    isUpdating = true; // Indicate that an update is in progress
                    world.update();    // Update the world
                    view.repaint(); // Update the graphical view (uncomment this line if needed)
                    isUpdating = false; // Indicate that the update has finished
                }
            }
        }, 0, tickDuration); // First tick runs immediately, then repeats every `tickDuration` milliseconds

        System.out.println("Simulation started with tick duration: " + tickDuration + " ms");
    }

    // Stop the simulation
    public void endSimulation() {
        if (timer != null) {
            timer.cancel(); // Cancel the current timer
            System.out.println("Simulation ended.");
        }
    }

    // Calculate and print stats about the world
    public double calculateStats() {
    	double H = 0, temp = 0;
        for(int i = 0; i<2; ++i ) {
        	if(world.c[i+1] != 0) { H = H + (double)(world.c[i]/world.c[i+1]); temp = temp + 1; 
        }}
        if (temp > 0) H = H/temp;
        return H;
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

}
