package sim;

public class Plant extends Organism {
    private float energyProducedPerTick; // Năng lượng sản xuất mỗi lần cập nhật (tick)
    private int birthRate; // Tỷ lệ sinh sản (số lượng cây con mỗi lần sinh sản)

    // Constructor
    public Plant(float energy, int posX, int posY, float energyProducedPerTick, int birthRate) {
        super(energy, 0, posX, posY); // Cây không di chuyển nên tốc độ = 0
        this.energyProducedPerTick = energyProducedPerTick;
        this.birthRate = birthRate;
    }

    // Phương thức sản xuất năng lượng qua quang hợp
    public void produceEnergy() {
        this.energy += energyProducedPerTick; // Tăng năng lượng mỗi tick
    }

    @Override
    public void move() {
        // Cây không di chuyển
    }

    @Override
    public void spawn() {
        // Logic để sinh cây mới theo tỷ lệ sinh sản
        System.out.println("Plant spawned at (" + posX + ", " + posY + ")");
    }

    @Override
    public void die() {
        // Khi cây chết, năng lượng sẽ bằng 0
        System.out.println("Plant at (" + posX + ", " + posY + ") has died.");
        this.energy = 0;
    }

    // Getters and Setters
    public float getEnergyProducedPerTick() {
        return energyProducedPerTick;
    }

    public void setEnergyProducedPerTick(float energyProducedPerTick) {
        this.energyProducedPerTick = energyProducedPerTick;
    }

    public int getBirthRate() {
        return birthRate;
    }

    public void setBirthRate(int birthRate) {
        this.birthRate = birthRate;
    }
}
