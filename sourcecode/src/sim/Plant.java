package sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plant extends Organism {
	private final int energyProducedPerTick = 20; // Năng lượng sản xuất mỗi lần cập nhật (tick)
	private final int birthRate = 2; // Tỷ lệ sinh sản (số lượng cây con mỗi lần sinh sản)

	// Constructor
	public Plant(int energy, int posX, int posY, World world) {
		super(energy, posX, posY, world, Organism.PLANT); // Cây không di chuyển nên tốc độ = 0

	}

	// Phương thức sản xuất năng lượng qua quang hợp
	public void produceEnergy() {
		this.energy += energyProducedPerTick; // Tăng năng lượng mỗi tick
	}

	// Getters and Setters
	public float getEnergyProducedPerTick() {
		return energyProducedPerTick;
	}

	public int getBirthRate() {
		return birthRate;
	}

	private int[] dx = { -1, 1, 0, 0 }; // Possible x-direction (left, right)
	private int[] dy = { 0, 0, -1, 1 }; // Possible y-direction (up, down)

<<<<<<< HEAD
  
    
    @Override 
    public void reproduce() {
    	 if (energy > 150  && world.getOrganisms().size() < CAP) {  // Nếu năng lượng đủ, sinh sản
    		 for (int i = 0; i < birthRate; ++i) {
             world.getOrganisms().add(new Plant(this.posX, this.posY, 100, world));  // Tạo một động vật ăn thịt mới với năng lượng ban đầu
             energy -= 10;  // Trừ năng lượng của động vật mẹ khi sinh sản
         }
         }
    }
    @Override 
    public synchronized void update() {
    	if (isDead()) {
            this.die();  // Ensure dead organisms don't continue with actions like hunting or reproducing
            return;
        }
    	this.produceEnergy();
    	this.reproduce();
    	
    }
    @Override
    public String getEmoji() {
        return "🌱"; // Example emoji for plant
    }
=======
	@Override
	public void reproduce() {
		if (energy > 150 && world.getOrganisms().size() < CAP) { // Nếu năng lượng đủ, sinh sản
			for (int i = 0; i < birthRate; ++i) {
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
					// Stuck on this part for a while and I realized the parameter position of
					// plant aint like others kekw, no wonder it keeps spawning at 100
					// với năng lượng ban đầu
					energy -= 10; // Trừ năng lượng của động vật mẹ khi sinh sản
					world.occupy(reproduceX, reproduceY, Organism.PLANT); // Update world grid
				}
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
>>>>>>> 5fde20c36d6340b794deb0ed6ae99ce43952abe9
}
