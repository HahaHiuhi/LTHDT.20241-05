package sim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class World {
	private final int DEAD = 0, HEALTHY = 1, CLEARED = 2;

	public final int WIDTH = 30;
	public final int HEIGHT = 30;
	public final int CAP = 700;
	private List<Organism> organisms;
	private int[][] worldGrid;
	private int state;

	public World() {
		this.worldGrid = new int[WIDTH][HEIGHT];
		this.organisms = new ArrayList<>();
		this.state = HEALTHY;
	}

	// Phương thức trả về số lượng cây (Plants)
    public long getPlantsCount() {
        return organisms.stream().filter(o -> o instanceof Plant).count();
    }

    // Phương thức trả về số lượng động vật ăn cỏ (Herbivores)
    public long getHerbivoresCount() {
        return organisms.stream().filter(o -> o instanceof Herbivore).count();
    }

    // Phương thức trả về số lượng động vật ăn thịt (Carnivores)
    public long getCarnivoresCount() {
        return organisms.stream().filter(o -> o instanceof Carnivore).count();
    }
    
	public List<Organism> getOrganisms() {
		return organisms;
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
		for (Organism o : organisms.toArray(new Organism[0])) { // Clone list to avoid concurrent modification exception
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
			if (organism instanceof Plant && type == Organism.PLANT) {

				distance = Math.sqrt(Math.pow(x - organism.getPosX(), 2) + Math.pow(y - organism.getPosY(), 2));
				if (distance < minDistance) {
					minDistance = distance;
					nearest = organism;
				}
			} else if (organism instanceof Herbivore && type == Organism.HERBIVORE) {

				distance = Math.sqrt(Math.pow(x - organism.getPosX(), 2) + Math.pow(y - organism.getPosY(), 2));
				if (distance < minDistance) {
					minDistance = distance;
					nearest = organism;
				} else if (organism instanceof Carnivore && type == Organism.CARNIVORE) {
					distance = Math.sqrt(Math.pow(x - organism.getPosX(), 2) + Math.pow(y - organism.getPosY(), 2));
					if (distance < minDistance) {
						minDistance = distance;
						nearest = organism;
					}
				}
			}
		}

		return nearest;
	}

	private int spawnX, spawnY;

	public void spawnOrganisms(int numPlant, int numHerbivore, int numCarnivore) {
		Random rand = new Random();

		// Spawn plants
		for (int i = 0; i < numPlant; i++) {
			generateSpawnPosition(rand);
			Plant newPlant = new Plant(100, spawnX, spawnY, this); // Initial energy of 100 for the plant
			addOrganism(newPlant);
			occupy(spawnX, spawnY, Organism.PLANT);
		}

		// Spawn herbivores
		for (int i = 0; i < numHerbivore; i++) {
			generateSpawnPosition(rand);
			Herbivore newHerbivore = new Herbivore(spawnX, spawnY, 100, this); // Initial energy of 100 for
																				// herbivores
			addOrganism(newHerbivore);
			occupy(spawnX, spawnY, Organism.HERBIVORE);
		}

		// Spawn carnivores
		for (int i = 0; i < numCarnivore; i++) {
			generateSpawnPosition(rand);
			Carnivore newCarnivore = new Carnivore(spawnX, spawnY, 100, this); // Initial energy of 100 for
																				// carnivores
			addOrganism(newCarnivore);
			occupy(spawnX, spawnY, Organism.CARNIVORE);
		}
	}

	private void generateSpawnPosition(Random rand) {
		do {
			spawnX = rand.nextInt(WIDTH);
			spawnY = rand.nextInt(HEIGHT);
		} while (isOccupied(spawnX, spawnY));
	}

	public boolean isOccupied(int x, int y) {
		return x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || worldGrid[x][y] != Organism.EMPTY;
	}

	public void occupy(int x, int y, int type) {
		worldGrid[x][y] = type;
	}

	public void unoccupy(int x, int y) {
		worldGrid[x][y] = Organism.EMPTY;
	}
	
	public void clearWorld() {
		organisms.clear();
		state = CLEARED;
	}

}
