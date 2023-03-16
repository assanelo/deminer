package Deminer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class Case extends JButton implements CaseInterface {
    private boolean bomb = false;
    private boolean discovered = false;
    private boolean flag = false;
    private short positionX;
    private short positionY;
    private short bombEnv = -1;

    // Constructor

    Case() {
        super();
        this.setBackground(Color.LIGHT_GRAY);
    }

    Case(short positionX, short positionY, boolean bomb) {
        super();
        this.setBackground(Color.LIGHT_GRAY);
        this.bomb = bomb;
        this.positionX = positionX;
        this.positionY = positionY;
        this.setSize(13, 13);
        this.setMaximumSize(new Dimension(13, 13));
        this.setMinimumSize(new Dimension(11, 11));
    }

    // Events listener
    public void manage_left_click(Grid grid) {
        if (this.bomb && !this.flag && !this.discovered || this.getBombEnv() > 0) {
            manage_left_click();
        } if (this.bomb && !this.flag && !this.discovered) {
            this.setBackground(Color.RED);
            Grid.setGameOver(grid);
        } else {
            Grid.setDiscoverOtherCase(grid, this);
        }
        this.discovered = true;
    }

    public void manage_left_click() {
        if (this.getBombEnv() >= 0 && !this.flag && !this.discovered && !this.bomb) {
            this.setEnabled(false);
            this.setBackground(Color.ORANGE);
            if (this.getBombEnv() >= 1 && !this.bomb) {
                this.setBackground(Color.YELLOW);
                this.setText(String.valueOf(this.getBombEnv()));
            }
            this.discovered = true;
        }
    }

    @Override
    public void manage_right_click() {
        if (!flag) {
            this.flag = true;
            this.discovered = true;
            this.setBackground(Color.GREEN);
        } else {
            this.flag = false;
            this.discovered = false;
            this.setBackground(Color.LIGHT_GRAY);
        }

    }

    // Getter and setter

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean getBombe() {
        return this.bomb;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setPosition(short positionX, short positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public short getBombEnv() {
        return bombEnv;
    }

    public void setBombEnv(short bombEnv) {
        this.bombEnv = bombEnv;
    }

    public short getPositionX() {
        return positionX;
    }

    public void setPositionX(short positionX) {
        this.positionX = positionX;
    }

    public short getPositionY() {
        return positionY;
    }

    public void setPositionY(short positionY) {
        this.positionY = positionY;
    }

    public boolean isBomb() {
        return this.bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag() {
    }

    @Override
    public String toString() {
        return "Bomb: " + this.bomb + ", " +
                "Flag: " + this.flag + ", " +
                "X:Y = " + this.positionX + ":" + this.positionY + ", " +
                "BombEnv : " + this.bombEnv;
    }


}
