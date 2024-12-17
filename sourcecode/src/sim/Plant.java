package sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plant extends Organism {
	private final int ENERGY_PER_TICK = 100; // Năng lượng sản xuất mỗi lần cập nhật (tick)
	private final int BIRTH_TIME = 10; 
	
	// Constructor
	public Plant(int energy, int posX, int posY, World world) {
		super(energy, posX, posY, world, Organism.PLANT); // Cây không di chuyển nên tốc độ = 0
		birthCooldown = BIRTH_TIME;

	}

	// Phương thức sản xuất năng lượng qua quang hợp
	public void produceEnergy() {
		this.energy += ENERGY_PER_TICK; // Tăng năng lượng mỗi tick
		world.c[0] += ENERGY_PER_TICK;
	}




	@Override
	public void reproduce() {
		if (birthCooldown > 0) birthCooldown --;
		else if (world.getOrganisms().size() < world.CAP) { 
			int[] dx = { -1, 1, 0, 0 }; // Possible x-direction (left, right)
			int[] dy = { 0, 0, -1, 1 }; // Possible y-direction (up, down)
		    
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
					reproduceX = this.posX + dx[c];
					reproduceY = this.posY + dy[c];
					choices.remove(c);
				} while (!choices.isEmpty() && world.isOccupied(reproduceX, reproduceY));

				if (!world.isOccupied(reproduceX, reproduceY)) {
					world.addOrganism(new Plant(100, reproduceX, reproduceY, world)); // Tạo một động vật ăn thịt
																						// mới
					
	
					world.occupy(reproduceX, reproduceY, Organism.PLANT); // Update world grid
					birthCooldown = BIRTH_TIME;
				}
			}
		}
	

	@Override
	public synchronized void update() {
		if (isDead()) {
			// Ensure dead organisms don't continue with actions like hunting or reproducing
			return;
		}
		this.produceEnergy();
		this.reproduce();

	}

	@Override
	public String getEmoji() {
		return "🌱"; // Example emoji for plant
	}
}
