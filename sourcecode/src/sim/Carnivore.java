package sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carnivore extends Animal {

	protected final int BIRTH_TIME = 50;
	public Carnivore(int x, int y, int initialEnergy, World world) {
		super(x, y, initialEnergy, world, Organism.CARNIVORE);
	}

	@Override
	public void hunt() {
		if (isMating()) return;
		// Tìm động vật ăn cỏ gần nhất để ăn
		Organism herbivore = world.findNearest(this.posX, this.posY, 2);
		if (herbivore != null && !herbivore.isDead()) {
			int dx = herbivore.posX - this.posX, dy = herbivore.posY - this.posY;
			if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
				this.energy += herbivore.energy / 10; // Tăng năng lượng bằng năng lượng động vật ăn cỏ
				herbivore.die(); // Động vật ăn cỏ bị tiêu thụ
				state = ALIVE;
			}
			move(herbivore.posX, herbivore.posY);
		} else
			move();
	}


	@Override
	public void reproduce() {
		if (isHunting()) return;
		if (birthCooldown > 0) birthCooldown --;
		else if (this.energy > 120 && world.getOrganisms().size() < world.CAP) { // Nếu năng lượng đủ, sinh sản
	
				
				int[] dirX = { -1, 1, 0, 0 }; // Possible x-direction (left, right)
				int[] dirY = { 0, 0, -1, 1 }; // Possible y-direction (up, down)
			
					
					Random rand = new Random();
					List<Integer> choices = new ArrayList<Integer>();
					choices.add(0);
					choices.add(1);
					choices.add(2);
					choices.add(3);
					int choice, reproduceX, reproduceY;

					do {
						choice = rand.nextInt(choices.size());
						Integer c = choices.get(choice);
						reproduceX = this.posX + dirX[c];
						reproduceY = this.posY + dirY[c];
						choices.remove(c);
					} while (!choices.isEmpty() && world.isOccupied(reproduceX, reproduceY));

					if (!world.isOccupied(reproduceX, reproduceY)) {
						world.addOrganism(new Carnivore(this.posX, this.posY, 100, world)); // Tạo một động vật ăn cỏ
																							// mới
																							// với năng lượng ban đầu
						this.energy -= 40; // Trừ năng lượng của động vật mẹ khi sinh sản
						world.occupy(reproduceX, reproduceY, Organism.CARNIVORE);
						
						birthCooldown = BIRTH_TIME;
					}
	
			
		}
		else move();
		state = ALIVE;

		
	}

	@Override
	public String getEmoji() {
		return "🐯"; // Example emoji for carnivore
	}

}
