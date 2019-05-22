import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private World world = null;

    @Override
    protected void paintComponent(java.awt.Graphics g) {

        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (world != null) {

            int availableHeight = this.getHeight() - 40;
            int availableWidth = this.getWidth();

            int cWidth = availableWidth / world.getWidth();
            int cHeight = availableHeight / world.getHeight();
            int min = Math.min(cWidth, cHeight);

            g.setColor(Color.BLACK);
            for (int i = 0; i < world.getHeight(); i++) {
                for (int j = 0; j < world.getWidth(); j++) {
                    if (world.getCell(j, i)) {
                        g.fillRect(j * min, i * min, min, min);
                    }
                }
            }
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < world.getHeight(); i++) {
                for (int j = 0; j < world.getWidth(); j++) {
                    g.drawRect(j * min, i * min, min, min);
                }
            }
            g.setColor(new Color(0, 0, 0));
            g.drawString("Generation: " + world.getGenerationCount(), 20, this.getHeight() - 25);
            
        }
    }

    public void display(World w) {
        world = w;
        repaint();
    }
}