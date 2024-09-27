import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

public class DrawRectangle extends JPanel implements MouseListener, MouseMotionListener {

    private Picture picture; // The Picture object to display
    private Point startPoint; // Where the mouse was pressed
    private Point currentPoint; // Current point as mouse is dragged
    private Rectangle2D.Double currentRectangle; // Currently drawn rectangle
    private HashMap<String, List<Rectangle2D.Double>> rectangles; // Map to store rectangles by ID
    private HashMap<String, Color> idColors; // Map to store colors for each ID
    private JTextField idField; // Text field to enter rectangle ID
    private String currentId; // The current ID entered by the user
    private Color currentColor; // Color for the current ID

    public DrawRectangle(Picture picture) {
        super();
        this.picture = picture; // The picture to display
        this.rectangles = new HashMap<>(); // Initialize the map to store rectangles
        this.idColors = new HashMap<>(); // Initialize the map to store colors for each ID

        // Add a text field for entering the rectangle ID
        idField = new JTextField(10);
        JLabel title = new JLabel("Enter Rectangle ID");
        title.setForeground(Color.white);
        this.add(title);
        this.add(idField);

        this.addMouseListener(this); // Add mouse listeners
        this.addMouseMotionListener(this);
    }

    // Paint the image, rectangles, and the legend
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the picture first
        BufferedImage image = picture.getBufferedImage();
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }

        // Draw all rectangles from the map
        Graphics2D g2d = (Graphics2D) g;
        for (String id : rectangles.keySet()) {
            g2d.setColor(idColors.get(id)); // Set color based on the ID
            for (Rectangle2D.Double rect : rectangles.get(id)) {
                g2d.draw(rect); // Draw each rectangle with its associated color
                drawLegend(g2d, rect, id);
            }
        }

        // Draw the current rectangle being dragged (if any)
        if (currentRectangle != null) {
            g2d.setColor(currentColor); // Set the current color
            g2d.draw(currentRectangle); // Draw the current rectangle
            // drawLegend(g2d, currentRectangle);
        }

    }

    // Draw the legend showing the ID and its corresponding color
    private void drawLegend(Graphics2D g2d, Rectangle2D.Double rect, String id) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        int xPosition = (int) rect.x;
        int yPosition = (int) rect.y;

        g2d.setColor(idColors.get(id));
        g2d.fillRect(xPosition, yPosition, 20, 20); // Draw color box
        g2d.setColor(Color.WHITE);
        g2d.drawRect(xPosition, yPosition, 20, 20); // Draw border around the color box
        g2d.drawString(id, xPosition + 30, yPosition + 15); // Draw the ID next to the box
        g2d.setColor(idColors.get(id));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint(); // Capture the starting point
        currentId = idField.getText(); // Get the ID from the text field
        if (currentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID for the rectangle.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the ID already has a color, if not assign a random color
        if (!idColors.containsKey(currentId)) {
            currentColor = new Color((int) (Math.random() * 0x1000000)); // Random color
            idColors.put(currentId, currentColor); // Store color for this ID
        } else {
            currentColor = idColors.get(currentId); // Use existing color for this ID
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentPoint = e.getPoint(); // Update the current point as the mouse drags
        updateRectangle(); // Recalculate and redraw the rectangle
        repaint(); // Request to redraw the panel with the updated rectangle
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentId != null && !currentId.isEmpty() && currentRectangle != null) {
            // Add the completed rectangle to the list for the given ID
            rectangles.computeIfAbsent(currentId, k -> new ArrayList<>()).add(currentRectangle);
        }
        currentRectangle = null; // Clear the current rectangle
        repaint(); // Repaint to display the final set of rectangles
    }

    // Calculate the rectangle based on start and current points
    private void updateRectangle() {
        if (startPoint != null && currentPoint != null) {
            int x = Math.min(startPoint.x, currentPoint.x);
            int y = Math.min(startPoint.y, currentPoint.y);
            int width = Math.abs(startPoint.x - currentPoint.x);
            int height = Math.abs(startPoint.y - currentPoint.y);
            currentRectangle = new Rectangle2D.Double(x, y, width, height);
        }
    }

    // Unused methods
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
