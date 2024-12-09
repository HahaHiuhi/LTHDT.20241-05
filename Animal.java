package sim;

public abstract class Animal {
    protected int x, y;      // Vị trí của động vật trên thế giới (hệ tọa độ)
    protected int energy;    // Năng lượng của động vật
    protected World world;   // Thế giới mà động vật sống

    // Constructor
    public Animal(int x, int y, int initialEnergy, World world) {
        this.x = x;
        this.y = y;
        this.energy = initialEnergy;
        this.world = world;
    }

    // Di chuyển đến một ô mới trong thế giới
    public void move() {
        // Di chuyển ngẫu nhiên trong phạm vi các ô lân cận
        int newX = (x + (int)(Math.random() * 3) - 1) % world.getWidth();
        int newY = (y + (int)(Math.random() * 3) - 1) % world.getHeight();
        this.x = newX;
        this.y = newY;
    }

    // Giảm năng lượng do mất năng lượng qua thời gian
    public void decayEnergy() {
        energy -= 1; // Giảm năng lượng mỗi tick (có thể thay đổi tùy theo mô phỏng)
    }

    // Kiểm tra xem động vật có chết không (nếu năng lượng <= 0)
    public boolean isDead() {
        return energy <= 0;
    }

    // Phương thức trừu tượng để động vật ăn (cần được triển khai trong các lớp con)
    public abstract void eat();

    // Phương thức trừu tượng để động vật sinh sản (cần được triển khai trong các lớp con)
    public abstract void reproduce();
}
