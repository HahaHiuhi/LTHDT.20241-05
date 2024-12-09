package sim;

package sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private int width;
    private int height;
    private List<Organism> organisms;
    private int state;

    
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.organisms = new ArrayList<>();
        this.state = 0; 
    }

  
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void setOrganisms(List<Organism> organisms) {
        this.organisms = organisms;
    }

    public int getState() {
        return state;
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
        for (Organism organism : organisms) {
            organism.update(); // Update each organism
        }
    }

  
    public void addOrganism(Organism organism) {
        this.organisms.add(organism);
    }

  
    public void removeOrganism(Organism organism) {
        this.organisms.remove(organism);
    }

  
    public Plant findNearestPlant(int x, int y) {
        Plant nearestPlant = null;
        double minDistance = Double.MAX_VALUE;

        for (Organism organism : organisms) {
            if (organism instanceof Plant) {
                Plant plant = (Plant) organism;
                double distance = Math.sqrt(Math.pow(x - plant.getX(), 2) + Math.pow(y - plant.getY(), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestPlant = plant;
                }
            }
        }

        return nearestPlant;
    }

    public Herbivore findNearestHerbivore(int x, int y) {
        Herbivore nearestHerbivore = null;
        double minDistance = Double.MAX_VALUE;

        for (Organism organism : organisms) {
            if (organism instanceof Herbivore) {
                Herbivore herbivore = (Herbivore) organism;
                double distance = Math.sqrt(Math.pow(x - herbivore.getX(), 2) + Math.pow(y - herbivore.getY(), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestHerbivore = herbivore;
                }
            }
        }

        return nearestHerbivore;
    }

  
    public Carnivore findNearestCarnivore(int x, int y) {
        Carnivore nearestCarnivore = null;
        double minDistance = Double.MAX_VALUE;

        for (Organism organism : organisms) {
            if (organism instanceof Carnivore) {
                Carnivore carnivore = (Carnivore) organism;
                double distance = Math.sqrt(Math.pow(x - carnivore.getX(), 2) + Math.pow(y - carnivore.getY(), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestCarnivore = carnivore;
                }
            }
        }

        return nearestCarnivore;
    }

 
    public void spawnOrganisms() {
        Random rand = new Random();

       
        int plantX = rand.nextInt(width);
        int plantY = rand.nextInt(height);
        Plant newPlant = new Plant(plantX, plantY, 5); // Initial energy of 5
        addOrganism(newPlant);

        if (rand.nextDouble() < 0.2) {  // 20% chance to spawn an herbivore
            int herbivoreX = rand.nextInt(width);
            int herbivoreY = rand.nextInt(height);
            Herbivore newHerbivore = new Herbivore(herbivoreX, herbivoreY, 10, this);
            addOrganism(newHerbivore);
        }

        if (rand.nextDouble() < 0.1) {  // 10% chance to spawn a carnivore
            int carnivoreX = rand.nextInt(width);
            int carnivoreY = rand.nextInt(height);
            Carnivore newCarnivore = new Carnivore(carnivoreX, carnivoreY, 20, this);
            addOrganism(newCarnivore);
        }
    }
}
