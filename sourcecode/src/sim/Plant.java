package sim;

public class Plant extends Organism {
    private final int energyProducedPerTick = 20; // Năng lượng sản xuất mỗi lần cập nhật (tick)
    private final int birthRate = 2; // Tỷ lệ sinh sản (số lượng cây con mỗi lần sinh sản)

    // Constructor
    public Plant(int energy, int posX, int posY, World world) {
        super(energy, posX, posY, world); // Cây không di chuyển nên tốc độ = 0
 
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
}
