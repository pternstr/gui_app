/*
 * To build use the following command: 
 * javac -source 17 -target 17 HelloGUI.java Calculator.java GraphDrawer.java
 * 
 * To run use the following command: java gui3_app.HelloGUI
 *  
 */

package gui3_app;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class HelloGUI {
    private JTextField inputField1;
    private JTextField inputField2;
    private JTextField inputField3;
    private JTextField inputField4;

    public static void main(String[] args) {
        // Calculator calc = new Calculator();
        HelloGUI gui = new HelloGUI();
        gui.createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Create the frame
        JFrame frame = new JFrame("Hello World GUI");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        inputField1 = new JTextField(10);
        inputField1.setText("5");
        inputField2 = new JTextField(10);
        inputField2.setText("10");
        inputField3 = new JTextField(10);
        inputField3.setText("15");
        inputField4 = new JTextField(10);
        inputField4.setText("7.5");

        JButton calButton = new JButton("Calculate");
        calButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

        // Add components with constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Ange dim. effekt (kw):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(inputField1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Ange temp inomhus (degC):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(inputField2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Enter Number 3:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(inputField3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Ange årsmedel temperatur:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(inputField4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calButton, gbc);

        frame.add(panel);
        frame.setSize(350, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void calculate() {
        // Get input values from the text fields

        int num1 = Integer.parseInt(inputField1.getText());
        int num2 = Integer.parseInt(inputField2.getText());
        int num3 = Integer.parseInt(inputField3.getText());
        float mtemp = Float.parseFloat(inputField4.getText());

        Calculator calc = new Calculator();
        // int sum = calc.add(num1, num2, num3);
        calc.generateGraph(mtemp);
        float grader = calc.getGrader();
        float gradtimmar = calc.getGradtimmar();
        JOptionPane.showMessageDialog(null, "temp är: " + grader);
        JOptionPane.showMessageDialog(null, "gradtimmar är: " + gradtimmar);
    }
}

// compile the code: javac -source 17 -target 17 HelloGUI.java Calculator.java
// run the code: java -cp /home/pternstr/private gui3_app.HelloGUI
