package sim;

public class Herbivore extends Animal {
    public Herbivore(int x, int y, int initialEnergy, World world) {
        super(x, y, initialEnergy, world);
    }

    @Override
    public void eat() {
        // Tìm cây gần nhất để ăn (cây là producers)
        Plant plant = world.findNearestPlant(x, y);
        if (plant != null) {
            energy += plant.getEnergy();  // Tăng năng lượng của động vật ăn cỏ bằng năng lượng cây
            plant.consume();  // Cây bị tiêu thụ
        }
    }

    @Override
    public void reproduce() {
        if (energy > 10) {  // Nếu năng lượng đủ, sinh sản
            world.addHerbivore(new Herbivore(x, y, 5, world));  // Tạo một động vật ăn cỏ mới với năng lượng ban đầu
            energy -= 5;  // Trừ năng lượng của động vật mẹ khi sinh sản
        }
    }
}
