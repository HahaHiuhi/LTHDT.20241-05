package sim;

import java.util.Random;

public abstract class Animal extends Organism implements Moveable {

	

	// Constructor
	public Animal(int x, int y, int initialEnergy, World world, int type) {
		super(initialEnergy, x, y, world, type);

	}

	// Di chuyển đến một ô mới trong thế giới
	public void move(int destX, int destY) {
		int x = posX, y = posY;
		if (x == destX && y == destY)
			return;
		int dx = destX - x, dy = destY - y;
		if (dx > 0)
			x++;
		else if (dx < 0)
			x--;
		if (dy > 0)
			y++;
		else if (dy < 0)
			y--;

		// Ensure the new position is not already occupied
		if (world.isOccupied(x, y)) {
			return;
		}

		world.unoccupy(this.posX, this.posY); // Update world grid
		this.posX = x;
		this.posY = y;
		world.occupy(x, y, type); // Update world grid
	}

	// Giảm năng lượng do mất năng lượng qua thời gian
	public void decayEnergy() {
		this.energy -= 3; // Giảm năng lượng mỗi tick (có thể thay đổi tùy theo mô phỏng)
	}

	public abstract void hunt();

	@Override
	public synchronized void update() {
		this.decayEnergy(); // Decay energy first, as it might affect subsequent actions
		if (this.isDead()) {
			this.die(); // Ensure dead organisms don't continue with actions like hunting or reproducing
			return;
		}
        if(state == ALIVE) {
        	Random rand = new Random();
        	if(energy > 120 ) 
        	state = rand.nextInt((MATING - HUNTING) + 1) + HUNTING;
        	else state = HUNTING;
        }
		hunt(); // Organism hunts for food. Energy might be gained here.
		reproduce(); // Reproduction happens if conditions are met (e.g., enough energy)
	}

	@Override
	public void move() {
		// Generate random direction (up, down, left, right)
		Random rand = new Random();
		int[] dx = { -1, 1, 0, 0 }; // Possible x-direction (left, right)
		int[] dy = { 0, 0, -1, 1 }; // Possible y-direction (up, down)

		// Pick a random direction
		int direction = rand.nextInt(4); // Choose a random direction (0-3)

		// Calculate the new position
		int newX = this.posX + dx[direction];
		int newY = this.posY + dy[direction];

		// Ensure the new position is within bounds of the world
		if (newX >= 0 && newX < world.WIDTH && newY >= 0 && newY < world.HEIGHT) {
			move(newX, newY); // Move to the new position
		}
	}
      
	public boolean isHunting() {
		return this.state == HUNTING ;
	}
	
	public boolean isMating() {
		return this.state == MATING ;
	}
	
}
