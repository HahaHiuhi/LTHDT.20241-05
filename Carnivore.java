package sim;

public class Carnivore extends Animal {
    public Carnivore(int x, int y, int initialEnergy, World world) {
        super(x, y, initialEnergy, world);
    }

    @Override
    public void eat() {
        // Tìm động vật ăn cỏ gần nhất để ăn
        Herbivore herbivore = world.findNearestHerbivore(x, y);
        if (herbivore != null) {
            energy += herbivore.getEnergy();  // Tăng năng lượng bằng năng lượng động vật ăn cỏ
            herbivore.decayEnergy();  // Động vật ăn cỏ bị tiêu thụ
        }
    }

    @Override
    public void reproduce() {
        if (energy > 20) {  // Nếu năng lượng đủ, sinh sản
            world.addCarnivore(new Carnivore(x, y, 10, world));  // Tạo một động vật ăn thịt mới với năng lượng ban đầu
            energy -= 10;  // Trừ năng lượng của động vật mẹ khi sinh sản
        }
    }
}
