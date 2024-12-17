package sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Herbivore extends Animal {
	private static final int BIRTH_TIME = 30;

	public Herbivore(int x, int y, int initialEnergy, World world) {
		super(x, y, initialEnergy, world, Organism.HERBIVORE);
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
				
						world.addOrganism(new Herbivore(reproduceX, reproduceY, 100, world)); // Tạo một động vật ăn cỏ
																							// mới
																							// với năng lượng ban đầu
						this.energy -= 40; // Trừ năng lượng của động vật mẹ khi sinh sản
						world.occupy(reproduceX, reproduceY, Organism.HERBIVORE);
						
						birthCooldown = BIRTH_TIME;
					}
				} 
		else move();
		state = ALIVE;
			
	}

	@Override
	public void hunt() {
		if (isMating()) return;
		// Tìm động vật ăn cỏ gần nhất để ăn
		Organism plant = world.findNearest(this.posX, this.posY, Organism.PLANT);
		if (plant != null && !plant.isDead()) {
			int dx = plant.posX - this.posX, dy = plant.posY - this.posY;
			if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
				world.c[1] += plant.energy / 10;
				this.energy += plant.energy / 10; // Tăng năng lượng bằng năng lượng động vật ăn cỏ
				plant.die(); // Động vật ăn cỏ bị tiêu thụ
				state = ALIVE;
			}
			move(plant.posX, plant.posY);
		} else
			move();

	}

	@Override
	public String getEmoji() {
		return "🦌"; // Example emoji for herbivore
	}
}
