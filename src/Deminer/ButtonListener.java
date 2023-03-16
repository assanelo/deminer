package Deminer;

import java.awt.event.*;

public class ButtonListener extends MouseAdapter {

    private Grid grid;

    public ButtonListener(Grid grid) {
        this.grid = grid;
    }



    public void mousePressed(MouseEvent e) {
        Case acase = (Case) e.getSource();

        if (acase.isEnabled())  {
            if(!grid.chronometer.started) {
                grid.chronometer.start();
            }

            if (e.getButton() == MouseEvent.BUTTON1) {
                acase.manage_left_click(grid);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                acase.manage_right_click();
                grid.updateFlagingN();
                grid.checkIfGamerDone();
            }
        }
    }

}
