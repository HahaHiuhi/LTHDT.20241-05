package sim;

public class Carnivore extends Animal {
    public Carnivore(int x, int y, int initialEnergy, World world) {
        super(x, y, initialEnergy, world);
    }

    @Override
    public void hunt() {
        // Tìm động vật ăn cỏ gần nhất để ăn
        Organism herbivore = world.findNearest(this.posX, this.posY, 2);
        if (herbivore != null && !herbivore.isDead()) {
        	int dx = herbivore.posX - this.posX,
        		dy = herbivore.posY - this.posY;
        	if(dx == 0 && dy == 0) {
            this.energy += herbivore.energy/10;  // Tăng năng lượng bằng năng lượng động vật ăn cỏ
            herbivore.die();  // Động vật ăn cỏ bị tiêu thụ
        	}
        	else move(herbivore.posX,herbivore.posY);
        }
        else move();
    }

    @Override
    public void reproduce() {
    	if (this.energy > 120  && world.getOrganisms().size() < CAP) {  // Nếu năng lượng đủ, sinh sản
       	 Organism partner = world.findNearest(this.posX, this.posY, 3);
            if (partner != null && !partner.isDead()) {
            	int dx = partner.posX - this.posX,
            		dy = partner.posY - this.posY;
            	if(Math.abs(dx) == 1 && Math.abs(dy) == 1) {
            	   world.addOrganism(new Carnivore(this.posX, this.posY, 100, world));  // Tạo một động vật ăn cỏ mới với năng lượng ban đầu
                   this.energy -= 40;  // Trừ năng lượng của động vật mẹ khi sinh sản
            	}
            	else move(partner.posX ,partner.posY);
            }
           
       }
    }
    @Override
    public String getEmoji() {
        return "🐯"; // Example emoji for carnivore
    }


}
