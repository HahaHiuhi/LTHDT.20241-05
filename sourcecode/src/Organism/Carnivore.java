package Organism;

import World.World;
import World.WorldMap;

public class Carnivore extends Animal {
    // Static variables to keep track of the total number, births, deaths, and energy gained by carnivores
    public static int total = 0; // Total number of carnivores created
    public static int birth = 0; // Number of births that have occurred
    public static int death = 0; // Number of carnivores that have died
    public static int count = 0; // Current count of carnivores in the world
    public static int energyGet = 0; // Total energy consumed by carnivores
    protected static final int BIRTH_TIME = 35; // Number of frames it takes for a carnivore to be able to reproduce again
    protected static final int FRAME_PER_MOVE = 1; // Frames it takes for a carnivore to move

    // Constructor: Initialize a carnivore at a specific position with a given amount of initial energy
    public Carnivore(int x, int y, int initialEnergy) {
        // Call the parent constructor to initialize the basic attributes of the animal
        super(x, y, initialEnergy, Organism.CARNIVORE);
        this.resetBirth(); // Reset the birth cooldown when a new carnivore is created
        count++; // Increment the count of carnivores
        total++; // Increment the total number of carnivores
    }

    // Method to represent the carnivore as an emoji (for display purposes)
    @Override
    public String getEmoji() {
        return "🐯"; // A tiger emoji for carnivores
    }

    // Method to give birth to a new carnivore at a specified location
    @Override
    protected void giveBirth(int x, int y) {
        World.addOrganism(new Carnivore(x, y, 100)); // Add a new carnivore at the specified location
        WorldMap.update(x, y, Organism.CARNIVORE); // Update the world map with the new carnivore's position
        this.energy -= 40; // The parent carnivore loses 40 energy to give birth
        birth++; // Increment the birth count
    }

    // Method to check if the carnivore is ready to reproduce
    @Override
    protected boolean canReproduce() {
        // Carnivores can reproduce if they are not hunting, their birth cooldown has passed, and they have enough energy (100 or more)
        return !isHunting() && birthCooldown >= BIRTH_TIME && energy >= 100;
    }

    // Method to check if it is time for the carnivore to move (based on the number of frames passed)
    @Override
    protected boolean isTimeToMove() {
        // A carnivore moves when the move frame has passed a certain threshold
        return moveFrame >= FRAME_PER_MOVE;
    }
}
