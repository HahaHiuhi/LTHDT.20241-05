package Organism;

import World.World;
import World.WorldMap;

public class Plant extends Organism {
	public static int total = 0;
	public static int birth = 0, death = 0;
	public static int count = 0;
	public static int energyGet = 0;
	private final int ENERGY_PER_TICK = 10; // Năng lượng sản xuất mỗi lần cập nhật (tick)
	private final int BIRTH_TIME = 10; 

	
	// Constructor
	public Plant(int energy, int posX, int posY) {
		super(energy, posX, posY, Organism.PLANT); // Cây không di chuyển nên tốc độ = 0
		birthCooldown = 0;
        count ++;
        total++;
	}

	// Phương thức sản xuất năng lượng qua quang hợp
	public void produceEnergy() {
		this.energy += ENERGY_PER_TICK; // Tăng năng lượng mỗi tick
		energyGet += ENERGY_PER_TICK;
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

	@Override
	protected void giveBirth(int x, int y) {
		World.addOrganism(new Plant(100, x, y)); 


		WorldMap.update(x, y, Organism.PLANT); // Update world grid
		birthCooldown = BIRTH_TIME;
		birth ++;
		
	}

	@Override
	protected boolean canReproduce() {
		
		return birthCooldown >= BIRTH_TIME && !World.isFull();
	}
}
