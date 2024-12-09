package sim;

public abstract class Organism {
    protected float energy;
    protected int speed;
    protected int posX;
    protected int posY;

    // Constructor
    public Organism(float energy, int speed, int posX, int posY) {
        this.energy = energy;
        this.speed = speed;
        this.posX = posX;
        this.posY = posY;
    }

    // Getters and Setters
    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    // Abstract methods
    public abstract void move();
    public abstract void spawn();
    public abstract void die();
}
