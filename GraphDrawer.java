package gui3_app;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class GraphDrawer extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int MARGIN = 50;

    private ArrayList<Point> dataPoints;

    public GraphDrawer() {
        dataPoints = new ArrayList<>();
        readDataFromFile("tempTable.txt");
    }

    // Added this method to add data points to the graph
    public void addDataPoint(int timmar, float grader) {
        dataPoints.add(new Point(timmar, (int) grader)); // Cast grader to int
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a BufferedImage with the same dimensions as the component
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D imgGraphics = img.createGraphics();

        // Set up the drawing area on the BufferedImage's Graphics2D context
        imgGraphics.setColor(Color.WHITE);
        imgGraphics.fillRect(0, 0, getWidth(), getHeight());

        // Draw the x and y axes on the BufferedImage's Graphics2D context
        imgGraphics.setColor(Color.BLACK);
        imgGraphics.drawLine(MARGIN, getHeight() - MARGIN, getWidth() - MARGIN, getHeight() - MARGIN); // x-axis
        imgGraphics.drawLine(MARGIN, getHeight() - MARGIN, MARGIN, MARGIN); // y-axis

        // Draw tick marks and labels for the x-axis on the BufferedImage's Graphics2D
        // context
        int interval = (getWidth() - 2 * MARGIN) / 10;
        for (int i = 0; i <= 10; i++) {
            int x = MARGIN + i * interval;
            imgGraphics.drawLine(x, getHeight() - MARGIN - 5, x, getHeight() - MARGIN + 5); // tick mark
            imgGraphics.drawString(Integer.toString(i * 876), x - 10, getHeight() - MARGIN + 20); // label
        }

        // Draw tick marks and labels for the y-axis on the BufferedImage's Graphics2D
        // context
        double minGrader = dataPoints.stream().mapToDouble(p -> p.y).min().orElse(0);
        double maxGrader = dataPoints.stream().mapToDouble(p -> p.y).max().orElse(0);
        int yInterval = (getHeight() - 2 * MARGIN) / 10;
        for (int i = 0; i <= 10; i++) {
            int y = getHeight() - MARGIN - i * yInterval;
            imgGraphics.drawLine(MARGIN - 5, y, MARGIN + 5, y); // tick mark
            double label = minGrader + i * (maxGrader - minGrader) / 10;
            imgGraphics.drawString(String.format("%.2f", label), 5, y + 5); // label
        }
        // Draw the data points on the BufferedImage's Graphics2D context
        imgGraphics.setColor(Color.RED);
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            Point p1 = dataPoints.get(i);
            Point p2 = dataPoints.get(i + 1);
            int x1 = mapXToPixel(p1.x);
            int y1 = mapYToPixel(p1.y, minGrader, maxGrader);
            int x2 = mapXToPixel(p2.x);
            int y2 = mapYToPixel(p2.y, minGrader, maxGrader);
            imgGraphics.drawLine(x1, y1, x2, y2);
        }

        // Draw the BufferedImage onto the original Graphics2D object
        g2d.drawImage(img, 0, 0, null);

        // Dispose of the BufferedImage's Graphics context
        imgGraphics.dispose();
    }

    private void readDataFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by comma to extract the values of timmar and grader
                String[] parts = line.split(", ");
                // Split the timmar part by space to extract the value of timmar
                int timmar = Integer.parseInt(parts[0].split(" = ")[1]);
                // Split the grader part by space to extract the value of grader
                float grader = Float.parseFloat(parts[1].split(" = ")[1]);
                dataPoints.add(new Point(timmar, Math.round(grader)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * private int mapXToPixel(int x) {
     * return MARGIN + (x * (WIDTH - 2 * MARGIN)) / (8760);
     * }
     * 
     * private int mapYToPixel(float y, double minGrader, double maxGrader) {
     * return HEIGHT - MARGIN - (int) ((y - minGrader) * (HEIGHT - 2 * MARGIN) /
     * (maxGrader - minGrader));
     * }
     */

    private int mapXToPixel(float x) {
        return MARGIN + (int) ((x / 8760) * (WIDTH - 2 * MARGIN));
    }

    private int mapYToPixel(float y, double minGrader, double maxGrader) {
        return HEIGHT - MARGIN - (int) (((y - minGrader) / (maxGrader - minGrader)) * (HEIGHT - 2 * MARGIN));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GraphDrawer());
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);
        });
    }
}

/*
 * @Override
 * protected void paintComponent(Graphics g) {
 * super.paintComponent(g);
 * Graphics2D g2d = (Graphics2D) g;
 * 
 * // Enable anti-aliasing
 * g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
 * RenderingHints.VALUE_ANTIALIAS_ON);
 * g2d.draw((Graphics2D) g);
 * 
 * // Set up the drawing area
 * g2d.setColor(Color.WHITE);
 * g2d.fillRect(0, 0, WIDTH, HEIGHT);
 * 
 * // Draw the x and y axes
 * g2d.setColor(Color.BLACK);
 * g2d.drawLine(MARGIN, HEIGHT - MARGIN, WIDTH - MARGIN, HEIGHT - MARGIN); //
 * x-axis
 * g2d.drawLine(MARGIN, HEIGHT - MARGIN, MARGIN, MARGIN); // y-axis
 * 
 * // Draw tick marks and labels for the x-axis
 * int interval = (WIDTH - 2 * MARGIN) / 10;
 * for (int i = 0; i <= 10; i++) {
 * int x = MARGIN + i * interval;
 * g2d.drawLine(x, HEIGHT - MARGIN - 5, x, HEIGHT - MARGIN + 5); // tick mark
 * g2d.drawString(Integer.toString(i * 876), x - 10, HEIGHT - MARGIN + 20); //
 * label
 * }
 * 
 * // Draw tick marks and labels for the y-axis
 * double minGrader = dataPoints.stream().mapToDouble(p -> p.y).min().orElse(0);
 * double maxGrader = dataPoints.stream().mapToDouble(p -> p.y).max().orElse(0);
 * int yInterval = (HEIGHT - 2 * MARGIN) / 10;
 * for (int i = 0; i <= 10; i++) {
 * int y = HEIGHT - MARGIN - i * yInterval;
 * g2d.drawLine(MARGIN - 5, y, MARGIN + 5, y); // tick mark
 * double label = minGrader + i * (maxGrader - minGrader) / 10;
 * g2d.drawString(String.format("%.2f", label), 5, y + 5); // label
 * }
 * 
 * // Draw the data points
 * g2d.setColor(Color.RED);
 * for (int i = 0; i < dataPoints.size() - 1; i++) {
 * Point p1 = dataPoints.get(i);
 * Point p2 = dataPoints.get(i + 1);
 * int x1 = mapXToPixel(p1.x);
 * int y1 = mapYToPixel(p1.y, minGrader, maxGrader);
 * int x2 = mapXToPixel(p2.x);
 * int y2 = mapYToPixel(p2.y, minGrader, maxGrader);
 * g2d.drawLine(x1, y1, x2, y2);
 * }
 * }
 */
