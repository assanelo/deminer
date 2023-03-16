package Deminer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deminer extends JFrame {

    public Grid[] grids;
    public JPanel[] tabPanel;

    public Deminer() {

        grids = new Grid[3];
        tabPanel = new JPanel[3];

        JTabbedPane tabbedPane = new JTabbedPane();

        for (int i = 1; i <= 3 ; i++) {
            grids[i-1] = new Grid(i);
            tabPanel[i-1] = new JPanel();

            tabPanel[i-1].setLayout(new BorderLayout());

            JPanel elementsPrimary = new JPanel();

            elementsPrimary.add(grids[i-1].resetGrid);
            elementsPrimary.add(grids[i-1].chronometer);
            elementsPrimary.add(grids[i-1].gameState);
            elementsPrimary.add(grids[i-1].flagingN);
            elementsPrimary.setLayout(new FlowLayout());

            tabPanel[i-1].add(grids[i-1], BorderLayout.CENTER);
            tabPanel[i-1].add(elementsPrimary, BorderLayout.NORTH);

            tabbedPane.addTab("Niveau"+i , tabPanel[i-1]);
        }

        this.add(tabbedPane);

        // Configurer la JFrame
        this.setTitle("Ma JFrame de test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }

    public static void main(String[] args) {
        new Deminer();
    }

    /*@Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = (JPanel) e.getSource();

        for (int i = 0; i < 3; i++) {

            if (panel == tabPanel[i]) {
                if (i == 0) {
                    panel.setBounds(0, 0, 4000, 4000);
                } else if (i == 1) {
                    panel.setBounds(0, 0, 8000, 8000);
                } else {
                    panel.setBounds(0, 0, 12000, 8000);
                }
            }
        }

    }*/
}
