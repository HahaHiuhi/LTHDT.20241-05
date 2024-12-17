package sim;



import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;

public class WorldRenderer extends JPanel {
	
	private static final long serialVersionUID = -4155452838523149843L;
	
	private World world;

    public WorldRenderer(World world) {
        this.world = world;
        setPreferredSize(new Dimension(world.WIDTH * 20, world.HEIGHT * 20)); // scale the grid by cell size
        
        // Add a border to the panel
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Black border with width 2
        setBorder(border);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure the background is cleared before painting

        // Set the grid size and cell size
        int cellWidth = 20;
        int cellHeight = 20;

        // Draw the grid (optional)
        g.setColor(Color.GRAY);
        for (int i = 0; i <= world.WIDTH; i++) {
            g.drawLine(i * cellWidth, 0, i * cellWidth, world.HEIGHT * cellHeight);
        }
        for (int i = 0; i <= world.HEIGHT; i++) {
            g.drawLine(0, i * cellHeight, world.WIDTH * cellWidth, i * cellHeight);
        }

        // Get a snapshot of organisms (copy the list to avoid ConcurrentModificationException)
        List<Organism> organismsSnapshot = new ArrayList<>(world.getOrganisms());

        // Draw the organisms as emojis
       
        for (Organism organism : organismsSnapshot) {
        	if(organism instanceof Plant)  g.setColor(Color.GREEN);
        	else if (organism instanceof Herbivore) g.setColor(Color.BLUE);
        	else if (organism instanceof Carnivore)  g.setColor(Color.RED);
            String emoji = organism.getEmoji();
            int x = organism.getPosX() * cellWidth;
            int y = organism.getPosY() * cellHeight;
            g.drawString(emoji, x + cellWidth / 4, y + cellHeight / 2); // Center the emoji in the cell
        }
    }

}
