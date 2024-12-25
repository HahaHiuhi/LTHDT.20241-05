package sim;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import World.World;


public  class Simulator {
	
	public enum SimulatorStatus {

		  RUNNING, STOPPED, PAUSED
		}

    private volatile SimulatorStatus status;  // Simulation status (RUNNING or STOPPED)
    private final World world;           // The simulation world object
    private final WorldRenderer view;    // Renderer for the simulation
    private SwingWorker<Void, Void> simulationWorker;  // SwingWorker to handle background task

    public Simulator(World world, WorldRenderer view) {
        this.world = world;
        this.view = view;
        this.status = SimulatorStatus.STOPPED;
    }

    /**
     * Starts the simulation using a SwingWorker.
     */
    public void startSimulation() {
        if (simulationWorker != null && !simulationWorker.isDone()) {
            System.out.println("Simulation is already running.");
            return;
        }

        status = SimulatorStatus.RUNNING;

        simulationWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
  
                while (status != SimulatorStatus.STOPPED && !world.isDead()) {
                	if(status == SimulatorStatus.PAUSED) continue;
                    updateSimulation();  // Perform simulation logic (update world state)
                    SwingUtilities.invokeLater(() -> renderSimulation());  // Render the world on the EDT

                    try {
                        Thread.sleep(250);  // Control update frequency (simulating frame rate)
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Simulation thread interrupted.");
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                // Perform any finalization after the simulation ends
                System.out.println("Simulation finished or paused.");
                status = SimulatorStatus.STOPPED;
            }
        };

        simulationWorker.execute();  // Start the background task
        System.out.println("Simulation started.");
    }

    /**
     * Stops the simulation safely.
     */
    public void stopSimulation() {
        status = SimulatorStatus.STOPPED;
        if (simulationWorker != null) {
            simulationWorker.cancel(true);  // Stop the SwingWorker task
        }
        System.out.println("Simulation stopped.");
    }

    /**
     * Checks if the simulation is currently running.
     */
    public boolean isSimulationRunning() {
        return status == SimulatorStatus.RUNNING;
    }

    /**
     * Updates the simulation world.
     */
    protected void updateSimulation() {
        world.update();  // Update the world state
        System.out.println("World updated.");
    }

    /**
     * Renders the simulation state.
     */
    protected void renderSimulation() {
        view.repaint();  // Repaint the view on the Event Dispatch Thread (EDT)
        System.out.println("World rendered.");
    }
    
    public void pauseSimulation() {
        status = SimulatorStatus.PAUSED;
    }
    
    public void resumeSimulation() {
        if(status == SimulatorStatus.PAUSED ) status = SimulatorStatus.RUNNING;
    }
}
