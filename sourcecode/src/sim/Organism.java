package sim;

public abstract class Organism {

	public static final int EMPTY = 0, PLANT = 1, HERBIVORE = 2, CARNIVORE = 3;

	protected final int DEAD = 1, ALIVE = 0, HUNTING = 3, MATING = 4;
	protected int energy;
	protected int posX;
	protected int posY;
	protected World world;
	protected int state;
	protected int birthCooldown;
	protected int type;

	// Constructor
	public Organism(int energy, int posX, int posY, World world, int type) {
		this.energy = energy;
		this.posX = posX;
		this.posY = posY;
		this.world = world;
		this.state = ALIVE;
	}

	public void die() {
		this.state = DEAD;
		world.unoccupy(posX, posY); // update world grid
	}

	public abstract void update(); // update() is called per tick

	public boolean isDead() {
		return this.state == DEAD || this.energy <= 0;
	}
	
	

	public abstract void reproduce();

	public abstract String getEmoji(); // Abstract method to get the emoji
    
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
}
    
