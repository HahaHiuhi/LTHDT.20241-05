package sim;

public abstract class Organism {
	protected final int DEAD = 1, ALIVE = 0, CAP = 200;
    protected int energy;
    protected int posX;
    protected int posY;
    protected World world;
    protected int state;

    // Constructor
    public Organism(int energy, int posX, int posY,World world) {
        this.energy = energy;
        this.posX = posX;
        this.posY = posY;
        this.world = world;
        this.state = ALIVE;
    }

    
   
    public void die() {
    	this.state = DEAD;
    }
    
    public abstract void update(); //update() is called per tick
    	
    
    
    public boolean isDead() {
       return this.state == DEAD || this.energy <= 0;	
    }
    public abstract void reproduce();
    
    public abstract String getEmoji(); // Abstract method to get the emoji

}
