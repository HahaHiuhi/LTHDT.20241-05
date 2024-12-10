package sim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class World {
	private final int DEAD = 0, HEALTHY = 1;
	
    public final int WIDTH = 30;
    public final int HEIGHT = 30;
    private List<Organism> organisms;
    private int [][] worldGrid;
    private int state;

    
    public World() {

        this.worldGrid = new int [WIDTH][HEIGHT];  
        this.organisms = new ArrayList<>();
        this.state = HEALTHY; 
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void setOrganisms(List<Organism> organisms) {
        this.organisms = organisms;
    }

    public boolean isDead() {
        return state == DEAD;
    }

    public void setState(int state) {
        this.state = state;
    }

   
    public void initializeOrganisms(List<Organism> initialOrganisms) {
        this.organisms.clear();
        this.organisms.addAll(initialOrganisms);
    }

   
    public void restartSimulation() {
        this.state = 0; // Reset state
        this.organisms.clear(); // Clear all organisms
    }

    
    public void update() {
        if (organisms.isEmpty()) {
            this.state = DEAD;
            return;
        }

        System.out.println("World IS ALIVE with " + organisms.size() + " organisms");

        // Use an iterator to safely remove organisms while iterating
        Iterator<Organism> iterator = organisms.iterator();
        while (iterator.hasNext()) {
            Organism o = iterator.next();
            if (o.isDead()) {
                System.out.println(o + " is dead");
                iterator.remove(); // Safe removal during iteration
            }
        }

        // Update remaining organisms
        for (Organism o : organisms) {
            o.update();
        }
    }


  
    public void addOrganism(Organism organism) {
        this.organisms.add(organism);
        System.out.println(organism + " added");
    }

  
    public void removeOrganism(Organism organism) {
    	System.out.println(organism + " removed");
        this.organisms.remove(organism);
        
    }

  
    public Organism findNearest(int x, int y, int type) {
        Organism nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Organism organism : organisms) {
        	double distance;
            if (organism instanceof Plant && type == 1) {
           
                distance = Math.sqrt(Math.pow(x - organism.posX, 2) + Math.pow(y - organism.posY, 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = organism;
                }
            }
            else if (organism instanceof Herbivore && type == 2) {
         
            	 distance = Math.sqrt(Math.pow(x - organism.posX, 2) + Math.pow(y - organism.posY, 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = organism;
                }
            else if (organism instanceof Carnivore && type == 3) {
            	 distance = Math.sqrt(Math.pow(x - organism.posX, 2) + Math.pow(y - organism.posY, 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = organism;
                }
                }
        }
        }

        return nearest;
    }

   
 
    public void spawnOrganisms(int numPlant, int numHerbivore, int numCarnivore) {
        Random rand = new Random();

        // Spawn plants
        for (int i = 0; i < numPlant; i++) {
            int plantX = rand.nextInt(WIDTH);
            int plantY = rand.nextInt(HEIGHT);
            Plant newPlant = new Plant(100, plantX, plantY, this);  // Initial energy of 100 for the plant
            addOrganism(newPlant);
        }

        // Spawn herbivores
        for (int i = 0; i < numHerbivore; i++) {
            int herbivoreX = rand.nextInt(WIDTH);
            int herbivoreY = rand.nextInt(HEIGHT);
            Herbivore newHerbivore = new Herbivore(herbivoreX, herbivoreY, 100, this);  // Initial energy of 100 for herbivores
            addOrganism(newHerbivore);
        }

        // Spawn carnivores
        for (int i = 0; i < numCarnivore; i++) {
            int carnivoreX = rand.nextInt(WIDTH);
            int carnivoreY = rand.nextInt(HEIGHT);
            Carnivore newCarnivore = new Carnivore(carnivoreX, carnivoreY, 100, this);  // Initial energy of 100 for carnivores
            addOrganism(newCarnivore);
        }
    }

}
