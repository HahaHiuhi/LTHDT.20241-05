package Organism;

import World.World;
import World.WorldMap;

public class Herbivore extends Animal {
    // Static variables to keep track of the total number, births, deaths, and energy gained by herbivores
    public static int total = 0; // Total number of herbivores created
    public static int birth = 0; // Number of births that have occurred
    public static int death = 0; // Number of herbivores that have died
    public static int count = 0; // Current count of herbivores in the world
    public static int energyGet = 0; // Total energy consumed by herbivores
    protected static final int BIRTH_TIME = 20; // Number of frames it takes for a herbivore to be able to reproduce again
    protected static final int FRAME_PER_MOVE = 2; // Frames it takes for a herbivore to move

    // Constructor: Initialize a herbivore at a specific position with a given amount of initial energy
    public Herbivore(int x, int y, int initialEnergy) {
        // Call the parent constructor to initialize the basic attributes of the animal
        super(x, y, initialEnergy, Organism.HERBIVORE);
        this.resetBirth(); // Reset the birth cooldown when a new herbivore is created
        count++; // Increment the count of herbivores
        total++; // Increment the total number of herbivores
    }

    // Method to represent the herbivore as an emoji (for display purposes)
    @Override
    public String getEmoji() {
        return "🦌"; // A deer emoji for herbivores
    }

    // Method to give birth to a new herbivore at a specified location
    @Override
    protected void giveBirth(int x, int y) {
        World.addOrganism(new Herbivore(x, y, 100)); // Add a new herbivore at the specified location
        WorldMap.update(x, y, Organism.HERBIVORE); // Update the world map with the new herbivore's position
        this.energy -= 40; // The parent herbivore loses 40 energy to give birth
        birth++; // Increment the birth count
    }

    // Method to check if the herbivore is ready to reproduce
    @Override
    protected boolean canReproduce() {
        // Herbivores can reproduce if they are not hunting, their birth cooldown has passed, and they have enough energy (100 or more)
        return !isHunting() && birthCooldown >= BIRTH_TIME && energy >= 100;
    }

    // Method to check if it is time for the herbivore to move (based on the number of frames passed)
    @Override
    protected boolean isTimeToMove() {
        // A herbivore moves when the move frame has passed a certain threshold
        return moveFrame >= FRAME_PER_MOVE;
    }
}
