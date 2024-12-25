package Organism;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import World.WorldMap;

public abstract class Organism {

    public static int organismCount = 0; // Total count of all organisms in the simulation

    // Constants to represent cell types
    public static final int EMPTY = 0, PLANT = 1, HERBIVORE = 2, CARNIVORE = 3;

    // Constants to represent organism states
    protected static final int DEAD = 1, ALIVE = 0, HUNTING = 3, MATING = 4;

    protected int energy; // Current energy level of the organism
    protected int posX; // X-coordinate of the organism's position
    protected int posY; // Y-coordinate of the organism's position
    protected int state; // Current state of the organism (e.g., alive, hunting, mating)
    protected int birthCooldown; // Cooldown time since the last reproduction
    public int type; // Type of the organism (e.g., plant, herbivore, carnivore)

    // Constructor: Initialize an organism with energy, position, and type
    protected Organism(int energy, int posX, int posY, int type) {
        this.energy = energy;
        this.posX = posX;
        this.posY = posY;
        this.state = ALIVE;
        this.type = type;
        organismCount++; // Increment global organism count
    }

    // Mark the organism as dead and update relevant statistics
    protected void die() {
        this.state = DEAD; // Change state to DEAD
       
     
        WorldMap.update(posX, posY, EMPTY); // Clear the organism's position on the world map
    }

    // Abstract method to be implemented by subclasses for updates per simulation tick
    public abstract void update();

    // Check if the organism is dead (based on state or energy level)
    public boolean isDead() {
        return this.state == DEAD || this.energy <= 0;
    }

    // Abstract method to return an emoji representation of the organism
    public abstract String getEmoji();

    // Getter for X-coordinate
    public int getPosX() {
        return this.posX;
    }

    // Getter for Y-coordinate
    public int getPosY() {
        return this.posY;
    }

    // Reset the birth cooldown and set the organism's state to ALIVE
    public void resetBirth() {
        birthCooldown = 0;
        state = ALIVE;
    }

    // Abstract method to define how an organism gives birth (implemented by subclasses)
    protected abstract void giveBirth(int x, int y);

    // Choose a random adjacent direction for reproduction or movement
    protected int[] chooseDirection() {
        int[] ans = new int[2];
        ans[0] = posX;
        ans[1] = posY;

        int[] dx = { -1, 1, 0, 0 }; // Possible x-direction (left, right)
        int[] dy = { 0, 0, -1, 1 }; // Possible y-direction (up, down)

        Random rand = new Random();
        List<Integer> choices = new ArrayList<>();
        choices.add(0);
        choices.add(1);
        choices.add(2);
        choices.add(3);

        int choice;
        do {
            choice = rand.nextInt(choices.size()); // Randomly select a direction
            Integer c = choices.get(choice);
            ans[0] = this.posX + dx[c];
            ans[1] = this.posY + dy[c];
            choices.remove(c); // Remove the tested direction
        } while (!choices.isEmpty() && WorldMap.isOccupied(ans[0], ans[1]));

        return ans;
    }

    // Handle reproduction if conditions are met
    protected void reproduce() {
        if (canReproduce()) { // Check if the organism has enough energy to reproduce
            int[] reproducePos = chooseDirection(); // Choose a direction for the offspring
            if (!WorldMap.isOccupied(reproducePos[0], reproducePos[1])) {
                this.giveBirth(reproducePos[0], reproducePos[1]); // Create the offspring
                this.resetBirth(); // Reset the birth cooldown
            }
        } else {
            birthCooldown++; // Increment cooldown if reproduction is not possible
            state = ALIVE; // Maintain alive state
        }
    }

    // Abstract method to check if the organism can reproduce (implemented by subclasses)
    protected abstract boolean canReproduce();

    // Reset statistics for all organisms (use when restarting simulator)
    public static void resetStats() {
        organismCount = 0;
        Plant.total = 0;
        Plant.count = 0;
        Plant.energyGet = 0;
        Plant.birth = 0;
        Plant.death = 0;
        Carnivore.total = 0;
        Carnivore.count = 0;
        Carnivore.energyGet = 0;
        Carnivore.birth = 0;
        Carnivore.death = 0;
        Herbivore.total = 0;
        Herbivore.count = 0;
        Herbivore.energyGet = 0;
        Herbivore.birth = 0;
        Herbivore.death = 0;
    }
}
