package sim;



import World.World;


public  class Simulator {
	
	public enum SimulatorStatus {

		  RUNNING, STOPPED, PAUSED
		}

    private volatile SimulatorStatus status;  // Simulation status (RUNNING or STOPPED)
    private final World world;           // The simulation world object

    public Simulator() {
        this.world = new World();
        this.status = SimulatorStatus.STOPPED;
    }

    // Starts the simulation using a SwingWorker.
     
    private Thread simulationThread;

    public void startSimulation() {
        if (simulationThread != null && simulationThread.isAlive()) {
            System.out.println("Simulation is already running.");
            return;
        }

        status = SimulatorStatus.RUNNING;

        simulationThread = new Thread(() -> {
            try {
                while (status != SimulatorStatus.STOPPED && !world.isDead()) {
                    if (status == SimulatorStatus.PAUSED) {
                        Thread.sleep(100); // Small delay to avoid busy-waiting
                        continue;
                    }

                    updateSimulation(); // Perform simulation logic (update world state)

                 

                    // Delay for the simulation loop
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Simulation thread interrupted.");
            } finally {
                // Ensure finalization after the thread ends
                System.out.println("Simulation finished or paused.");
                status = SimulatorStatus.STOPPED;
            }
        });

        simulationThread.start(); // Start the simulation thread
        System.out.println("Simulation started.");
    }


    // Stop simulation
    public void stopSimulation() {
        status = SimulatorStatus.STOPPED;
        if (simulationThread != null) {
        	simulationThread.interrupt();;  // Stop the SwingWorker task
        }
        System.out.println("Simulation stopped.");
    }
    
    // Check if running
    public boolean isSimulationRunning() {
        return status == SimulatorStatus.RUNNING;
    }

   
    // Update word
    protected void updateSimulation() {
        world.update();  // Update the world state
        System.out.println("World updated.");
    }

    
   
    
    public void pauseSimulation() {
        status = SimulatorStatus.PAUSED;
    }
    
    public void resumeSimulation() {
        if(status == SimulatorStatus.PAUSED ) status = SimulatorStatus.RUNNING;
    }
}
