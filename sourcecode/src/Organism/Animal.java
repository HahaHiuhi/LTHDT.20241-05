package Organism;

import java.util.Random;

import World.World;
import World.WorldMap;

public abstract class Animal extends Organism {

    protected int moveFrame = 0; // Tracks the number of frames since the last move (used to regulate movement speed)
    public static int birthDeath = 0;
    // Constructor: Initialize an animal at a specific position with a given type and initial energy
    public Animal(int x, int y, int initialEnergy, int type) {
        super(initialEnergy, x, y, type);
    }

    // Method to move the animal to a specific destination
    protected void move(int destX, int destY) {
        if (!isTimeToMove()) return; // Check if the animal is allowed to move
        moveFrame = 0; // Reset the move frame counter after moving

        int x = posX, y = posY, newX = x, newY = y;
        if (x == destX && y == destY) return; // If already at destination, no movement needed

        // Calculate movement in the x and y directions
        int dx = destX - x, dy = destY - y;
        if (dx > 0) newX++;
        else if (dx < 0) newX--;
        if (dy > 0) newY++;
        else if (dy < 0) newY--;

        // Ensure the new position is not occupied
        if (WorldMap.isOccupied(newX, newY)) return;

        WorldMap.update(x, y, EMPTY); // Clear the current position on the map
        this.posX = newX; // Update the animal's x-coordinate
        this.posY = newY; // Update the animal's y-coordinate
        WorldMap.update(newX, newY, type); // Mark the new position on the map
    }


    // Reduces the animal's energy over time (simulating energy decay)
    public void decayEnergy() {
        this.energy -= 1; // Decrease energy by 1 per tick
    }

    // Method to hunt for prey
    public void hunt() {
        if (isMating()) return; // Do not hunt if the animal is mating

        // Find the nearest prey of the appropriate type
        int[] prey = WorldMap.findNearest(this.posX, this.posY, type - 1);
        Organism o = null;
        if (prey[0] != -1 && prey[1] != -1) o = World.getOrganismAt(prey[0], prey[1], type - 1);

        if (o != null && !o.isDead()) {
            // Check if prey is within eating distance
            int dx = prey[0] - this.posX, dy = prey[1] - this.posY;
            if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
                int en = o.energy / 10; // Calculate energy gained
                this.energy += en; // Increase the predator's energy
                if (this.type == Organism.CARNIVORE) Carnivore.energyGet += en; // Update carnivore stats
                else if (this.type == Organism.HERBIVORE) Herbivore.energyGet += en; // Update herbivore stats
                o.die(); // Kill the prey
                state = ALIVE; // Set the predator's state to active
            }
            move(prey[0], prey[1]); // Move closer to the prey
        } else {
            move(); // Move randomly if no prey is found
            state = ALIVE; // Set the predator's state to active
        }
    }

    // Method called each simulation tick to update the animal's state
    @Override
    public void update() {
        this.decayEnergy(); // Reduce energy first
        if (this.isDead()) {
            this.die(); // Remove dead animals from the simulation
            return;
        }
        moveFrame++; // Increment the frame counter for movement

        // Determine the animal's state (hunting or mating)
        if (state == ALIVE) {
            Random rand = new Random();
            if (canReproduce()) {
            	int chance = rand.nextInt(100); // Generate a random number between 0 and 99

            	if (chance < 50 + birthDeath) { //
            	    state = MATING;
            	} else { 
            	    state = HUNTING;
            	}
            }
            else state = HUNTING;
        }
        hunt(); // Attempt to hunt for food
        reproduce(); // Attempt to reproduce if conditions are met
    }

    // Method to move the animal in a random direction
    protected void move() {
        if (!isTimeToMove()) return; // Check if the animal is allowed to move

        int [] direction = this.chooseDirection();

        // Ensure the new position is within bounds of the world
        if (direction[0] >= 0 && direction[0] < World.WIDTH && direction[1] >= 0 && direction[1] < World.HEIGHT) {
            move(direction[0], direction[1]); // Move to the new position
        }
        moveFrame = 0; // Reset the move frame counter
    }

    // Check if the animal is currently hunting
    protected boolean isHunting() {
        return this.state == HUNTING;
    }

    // Check if the animal is currently mating
    protected boolean isMating() {
        return this.state == MATING;
    }
    
 // Abstract method to check if the animal is allowed to move (based on move frames)
    protected abstract boolean isTimeToMove();
}
