package sim;



import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;

import Organism.Carnivore;
import Organism.Herbivore;
import Organism.Organism;
import Organism.Plant;
import World.World;

public class WorldRenderer extends JPanel {
	
	private static final long serialVersionUID = -4155452838523149843L;
	


    public WorldRenderer() {
      
        setPreferredSize(new Dimension(World.WIDTH * 20, World.HEIGHT * 20)); // scale the grid by cell size
        
        // Add a border to the panel
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Black border with width 2
        setBorder(border);
    }

    @Override
    protected  synchronized void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the background

        // Set the grid size and cell size
        int cellWidth = 20;
        int cellHeight = 20;

        // Enable anti-aliasing for smoother rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the grid (optional, can be commented out for better performance)
        g2d.setColor(Color.GRAY);
        for (int i = 0; i <= World.WIDTH; i++) {
            g2d.drawLine(i * cellWidth, 0, i * cellWidth, World.HEIGHT * cellHeight);
        }
        for (int i = 0; i <= World.HEIGHT; i++) {
            g2d.drawLine(0, i * cellHeight, World.WIDTH * cellWidth, i * cellHeight);
        }

        // Get a snapshot of organisms to avoid concurrent modification
        List<Organism> organismsSnapshot;
        synchronized (World.class) {
            organismsSnapshot = new ArrayList<>(World.getOrganisms());
        }

        // Draw the organisms efficiently
        for (Organism organism : organismsSnapshot) {
            // Set the color based on the organism type
            if (organism instanceof Plant) {
                g2d.setColor(Color.GREEN);
            } else if (organism instanceof Herbivore) {
                g2d.setColor(Color.BLUE);
            } else if (organism instanceof Carnivore) {
                g2d.setColor(Color.RED);
            }

            // Calculate position and size
            int x = organism.getPosX() * cellWidth;
            int y = organism.getPosY() * cellHeight;

            // Draw the organism (emoji or circle as fallback)
            String emoji = organism.getEmoji();
            if (emoji != null && !emoji.isEmpty()) {
                g2d.drawString(emoji, x + cellWidth / 4, y + cellHeight * 3 / 4); // Emoji centered
            } else {
                g2d.fillOval(x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2); // Fallback
            }
        }
    }


}
