
/**
 * @author Shamak Gowda
 * This class is entirely for drawing rectangles
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private JButton saveButton; // Button to save rectangles to a file
    private JButton undoButton; // to Undo

    public DrawRectangle(Picture picture) {
        super();
        this.picture = picture;
        this.rectangles = new HashMap<>();
        this.idColors = new HashMap<>();

        // Create a new label to tell the user to enter the rectangle id
        idField = new JTextField(10);
        JLabel title = new JLabel("Enter Rectangle ID");
        title.setForeground(Color.red);
        this.add(title);
        this.add(idField);

        // Add a save button
        saveButton = new JButton("Save Rectangles");
        saveButton.addActionListener(e -> saveRectangles());
        this.add(saveButton);

        undoButton = new JButton("Undo Last Rectangle");
        undoButton.addActionListener(e -> undoLastRectangle());
        this.add(undoButton);

        // MouseListener for the user to drag the rectangle
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * @param Graphics g, which allows me to draw on components like Jpanel
     * @return void
     *         Paint the image, rectangles, and the legend
     */
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
                g2d.draw(rect);
                drawLegend(g2d, rect, id);
            }
        }

        // Draw the current rectangle being dragged (if any)
        if (currentRectangle != null) {
            g2d.setColor(currentColor);
            g2d.draw(currentRectangle);
        }
    }

    /**
     * 
     * @param g2d
     * @param rect
     * @param id
     * @return void
     *         Draw the legend showing the ID and its corresponding color
     */
    private void drawLegend(Graphics2D g2d, Rectangle2D.Double rect, String id) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        int xPosition = (int) rect.x;
        int yPosition = (int) rect.y;

        g2d.setColor(idColors.get(id));
        g2d.fillRect(xPosition, yPosition, 20, 20);
        g2d.drawRect(xPosition, yPosition, 20, 20);
        g2d.drawString(id, xPosition + 30, yPosition + 15); // Draw the ID next to the box

    }

    /**
     * @param MouseEvent e allows the user to draw the rectangle
     * @return void
     *         Draw the rectangle with the correct color.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint(); // Get the starting point
        currentId = idField.getText();
        // create an alert box
        if (currentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID for the rectangle.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!idColors.containsKey(currentId)) {
            // Generate vibrant colors by controlling RGB components
            // High values --> more white
            int r = (int) (Math.random() * 128) + 128; // Red is between 128 and 255
            int g = (int) (Math.random() * 128) + 128; // Green is between 128 and 255
            int b = (int) (Math.random() * 128) + 128; // Blue is between 128 and 255

            // randomly set one of the RGB channels to a lower value
            // for more color variety but still ensure high contrast.
            if (Math.random() < 0.33)
                r = (int) (Math.random() * 128); // Chance to lower Red
            else if (Math.random() < 0.5)
                g = (int) (Math.random() * 128); // Chance to lower Green
            else
                b = (int) (Math.random() * 128); // Chance to lower Blue

            currentColor = new Color(r, g, b);
            idColors.put(currentId, currentColor);
        } else {
            currentColor = idColors.get(currentId); // Retrieve the existing color
        }
    }

    /**
     * @param MouseEvent e
     *                   Update the current point as the mouse drags. Recalculate
     *                   and redraw the rectangle. Redraw the panel with the updated
     *                   rectangle
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        currentPoint = e.getPoint();
        updateRectangle();
        repaint();
    }

    /**
     * @param MouseEvent e
     * @return void
     *         Add the completed rectangle to the list for the given ID
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentId != null && !currentId.isEmpty() && currentRectangle != null) {
            rectangles.computeIfAbsent(currentId, k -> new ArrayList<>()).add(currentRectangle);
        }
        currentRectangle = null; // Clear the current rectangle
        repaint();
    }

    /**
     * @param none
     * @return void
     *         Calculate the rectangle based on start and current points
     */
    private void updateRectangle() {
        if (startPoint != null && currentPoint != null) {
            int x = Math.min(startPoint.x, currentPoint.x);
            int y = Math.min(startPoint.y, currentPoint.y);
            int width = Math.abs(startPoint.x - currentPoint.x);
            int height = Math.abs(startPoint.y - currentPoint.y);
            currentRectangle = new Rectangle2D.Double(x, y, width, height);
        }
    }

    /**
     * @param none
     * @return none
     *         Save rectangles and their IDs to a file.
     */
    private void saveRectangles() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write image dimensions
                writer.write("Image Width: " + picture.getWidth());
                writer.newLine();
                writer.write("Image Height: " + picture.getHeight());
                writer.newLine();
                writer.write("Rectangles:\n");
                for (String id : rectangles.keySet()) {
                    for (Rectangle2D.Double rect : rectangles.get(id)) {
                        writer.write("ID: " + id + ", Rect: (" + rect.x + ", " + rect.y + ", " + rect.width + ", "
                                + rect.height + ")\n");
                    }
                }

                // create an alert for the user
                JOptionPane.showMessageDialog(this, "Rectangles saved successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving rectangles: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * @param none
     * @return void
     *         Undo the last rectangle drawn
     */
    private void undoLastRectangle() {
        if (currentId != null && rectangles.containsKey(currentId)) {
            List<Rectangle2D.Double> rectList = rectangles.get(currentId);
            if (!rectList.isEmpty()) {
                rectList.remove(rectList.size() - 1); // Remove the last rectangle
                if (rectList.isEmpty()) {
                    rectangles.remove(currentId); // Remove the ID if no rectangles are left
                    idColors.remove(currentId);
                }
            }
        }
        repaint(); // Redraw without the last rectangle
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
