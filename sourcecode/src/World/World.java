package World;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Organism.Carnivore;
import Organism.Herbivore;
import Organism.Organism;
import Organism.Plant;

public class World {
	public final static int DEAD = 0, HEALTHY = 1;
	public final static int WIDTH = 30, HEIGHT = 30;
	public final static int CAP = 700;
	private static List<Organism> organisms;
	private int state;
 
    
	public World() {
	     
		organisms = new ArrayList<>();
		this.state = HEALTHY;
	}  

    public static Organism getOrganismAt(int x, int y, int type) {
    	for (Organism organism : organisms) {
    		if(organism.getPosX() == x && organism.getPosY() == y && organism.type == type && !organism.isDead())
    			return organism;
    	}
		return null;
    }
    
	public static List<Organism> getOrganisms() {
		return organisms;
	}


	public boolean isDead() {
		return state == DEAD;
	}




	

	public  void update() {
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
				Organism.organismCount --;
				if (o.type == Organism.PLANT) {
	                Plant.count--;
	                Plant.death++;
	            } else if (o.type == Organism.CARNIVORE) {
	                Carnivore.count--;
	                Carnivore.death++;
	            } else if (o.type == Organism.HERBIVORE) {
	                Herbivore.count--;
	                Herbivore.death++;
	            }
				iterator.remove(); // Safe removal during iteration
			}
		}
      
		// Update remaining organisms
		for (Organism o : organisms.toArray(new Organism[0])) { // Clone list to avoid concurrent modification exception
			o.update();
		}
	}

	public synchronized static void addOrganism(Organism organism) {
		if(World.isFull()) return;
		organisms.add(organism);
		System.out.println(organism + " added");
	}

	public  synchronized void removeOrganism(Organism organism) {
		System.out.println(organism + " removed");
		organisms.remove(organism);

	}


	private int spawnX, spawnY;

	public  synchronized void spawnOrganisms(int numPlant, int numHerbivore, int numCarnivore) {
		clearWorld();
		Random rand = new Random();
		 WorldMap.reset();
		 
		// Spawn plants
		for (int i = 0; i < numPlant; i++) {
			generateSpawnPosition(rand);
			Plant newPlant = new Plant(200, spawnX, spawnY); // Initial energy of 100 for the plant
			addOrganism(newPlant);
			WorldMap.update(spawnX, spawnY, Organism.PLANT);
		}

		// Spawn herbivores
		for (int i = 0; i < numHerbivore; i++) {
			generateSpawnPosition(rand);
			Herbivore newHerbivore = new Herbivore(spawnX, spawnY, 200); // Initial energy of 100 for
																				// herbivores
			addOrganism(newHerbivore);
			WorldMap.update(spawnX, spawnY, Organism.HERBIVORE);
		}

		// Spawn carnivores
		for (int i = 0; i < numCarnivore; i++) {
			generateSpawnPosition(rand);
			Carnivore newCarnivore = new Carnivore(spawnX, spawnY, 200); // Initial energy of 100 for
																				// carnivores
			addOrganism(newCarnivore);
			WorldMap.update(spawnX, spawnY, Organism.CARNIVORE);
		}
	}

	private void generateSpawnPosition(Random rand) {
		do {
			spawnX = rand.nextInt(WIDTH);
			spawnY = rand.nextInt(HEIGHT);
		} while (WorldMap.isOccupied(spawnX, spawnY));
	}
	
	
	public synchronized void clearWorld() {
		organisms = new ArrayList<>(); // Turns out the clear() method keeps the list in memory, causing memory leaks
	    Organism.resetStats();
	}

	
	
	
	public static boolean isFull() {
		return organisms.size() >= CAP;
	}

}
