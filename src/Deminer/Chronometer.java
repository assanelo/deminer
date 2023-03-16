package Deminer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Chronometer extends JPanel implements Runnable {

    Timer timer;
    int millisecondes = 0;
    int secondes = 0;
    int minutes = 0;
    int heures = 0;

    boolean started = false;

    JLabel labelChronometer;


    public Chronometer() {
        this.setSize(100, 100);

        labelChronometer = new JLabel("00:00:00.000");
        labelChronometer.setFont(new Font("Impact", Font.PLAIN, 30));

        add(labelChronometer, BorderLayout.CENTER);

        timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                millisecondes++;
                if (millisecondes == 100) {
                    millisecondes = 0;
                    secondes++;
                }
                if (secondes == 60) {
                    secondes = 0;
                    minutes++;
                }
                if (minutes == 60) {
                    minutes = 0;
                    heures++;
                }
                labelChronometer.setText(String.format("%02d:%02d:%02d.%03d", heures, minutes, secondes, millisecondes));
            }
        });
    }


    public void reset() {
        timer.stop();
        millisecondes = 0;
        secondes = 0;
        minutes = 0;
        heures = 0;
        labelChronometer.setText("00:00:00.000");
    }

    @Override
    public void run() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void start() {
        timer.start();
    }

    public String getText() {
        return labelChronometer.getText();
    }
}
