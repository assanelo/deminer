package Deminer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Grid extends JPanel implements GridInterface, ActionListener {

    public Case[][] cases;
    public final int GRID_LENGTH_SMALL = 9;
    public final int GRID_LENGTH_MEDIUM = 16;
    public final int GRID_LENGTH_LARGE = 30;

    public final int GRID_BOMBE_FOR_SMALL_GRID = 10;
    public final int GRID_BOMBE_FOR_MEDIUM_GRID = 40;
    public final int GRID_BOMBE_FOR_LARGE_GRID = 99;

    public Chronometer chronometer;

    public JLabel flagingN;
    public JLabel gameState;

    public Font fontText;

    public void updateFlagingN() {

        int cmpF = 0, cmpB = 0;
        for (Case[] aCase : this.cases) {
            for (Case acase : aCase) {
                if (acase.getFlag()) cmpF++;
                if (acase.getBombe()) cmpB++;
            }
        }

        flagingN.setText(String.valueOf(cmpB-cmpF));
    }

    JButton resetGrid;

    // Constructor of the grade
    public Grid(int level) {

        fontText = new Font("Impact", Font.PLAIN, 30);

        chronometer = new Chronometer();
        gameState = new JLabel();
        gameState.setFont(fontText);
        flagingN = new JLabel();
        flagingN.setFont(fontText);
        resetGrid = new JButton("Reset");
        resetGrid.setFont(fontText);

        setVisible(true);

        fontText = new Font("Impact", Font.PLAIN, 10);

        init(level);
        resetGrid.addActionListener(this);
    }


    public void init(final int LEVEL) {
        int WIDTH = 0, HEIGTH = 0, BOMB = 0;
        if (LEVEL == 1) {
            WIDTH = GRID_LENGTH_SMALL;
            HEIGTH = GRID_LENGTH_SMALL;
            BOMB = GRID_BOMBE_FOR_SMALL_GRID;
            this.setBounds(new Rectangle(new Dimension(400, 400)));
        } else if (LEVEL == 2) {
            WIDTH = GRID_LENGTH_MEDIUM;
            HEIGTH = GRID_LENGTH_MEDIUM;
            BOMB = GRID_BOMBE_FOR_MEDIUM_GRID;
            this.setBounds(new Rectangle(new Dimension(800, 800)) );
        } else if (LEVEL == 3) {
            WIDTH = GRID_LENGTH_LARGE;
            HEIGTH = GRID_LENGTH_MEDIUM;
            BOMB = GRID_BOMBE_FOR_LARGE_GRID;
            this.setBounds(new Rectangle(new Dimension(1200, 800)) );
        } else {
            // À remplir exception
        }

        cases = new Case[HEIGTH][WIDTH];

       this.setLayout(new GridLayout(HEIGTH, WIDTH ));
        // We generate the grid cases
        for (int i = 0; i < HEIGTH; i++) {
            for (int j = 0; j < WIDTH ; j++) {
                this.cases[i][j] = new Case( (short)i, (short)j, false);
                this.add(cases[i][j]);
                cases[i][j].setFont(fontText);
            }
        }


        // We generate randomly the value of bomb position
        Random random = new Random();
        for (int i = 0; i < BOMB; i++) {
            int j, k;
            j = random.nextInt(HEIGTH);
            k = random.nextInt(WIDTH);
            this.cases[j][k].setBomb(true);
        }

        ButtonListener listener = new ButtonListener(this);

        for (int i = 0; i < HEIGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                this.cases[i][j].addMouseListener(listener);
            }
        }

        this.countBomb();
        this.setBounds(0,0, HEIGTH*12, WIDTH*12);

        setVisible(true);
    }


    // verify if all cases was discovered
    public void checkIfGamerDone() {
        for (Case[] aCase : this.cases) {
            for (Case acase : aCase) {
                if (acase.getFlag() && !acase.getBombe())
                    return;
            }
        }

        int cmpF = 0;
        int cmpB = 0;

        for (Case[] aCase : this.cases) {
            for (Case acase : aCase) {
                if (acase.getFlag()) cmpF++;
                if (acase.getBombe()) cmpB++;
            }
        }


        if (cmpF == cmpB)  {
            for (Case[] aCase : this.cases) {
                for (Case acase : aCase) {
                    if (acase.isEnabled()) acase.setEnabled(false);
                }
            }
        } else return;

        chronometer.stop();
        gameState.setText("You winn");
    }

    public static void setGameOver(Grid grid) {
        for (Case[] aCase : grid.cases) {
            for (Case acase : aCase) {
                if (acase.getBombe() && !acase.getFlag()) acase.setBackground(Color.RED);
            }
        }

        for (Case[] aCase : grid.cases) {
            for (Case acase : aCase) {
                if (acase.isEnabled()) acase.setEnabled(false);
            }
        }

        for (Case[] aCase : grid.cases) {
            for (Case acase : aCase) {
                if (!acase.isDiscovered()) acase.manage_left_click();
            }
        }

        grid.chronometer.stop();
        grid.gameState.setText("Game Over");
    }

    public void countBomb() {

        int WIDTH = this.cases[1].length, HEIGHT = this.cases.length;

        for (int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++) {/*
                if (cases[i][j].isBomb())
                    break;*/

                int sum = 0;
                for (int k = i - 1; k <= (i + 1); k++)
                    for (int l = j - 1; l <= (j + 1); l++) {
                        if (l >= 0 && k >= 0 && l < WIDTH && k < HEIGHT)
                            if (this.cases[k][l].isBomb())
                                sum++;
                    }
                if(cases[i][j] != null)  {
                    this.cases[i][j].setBombEnv((short) sum);
                }
            }
        }
    }

    public static void setDiscoverOtherCase(Grid grid, Case acase) {

        int WIDTH = grid.cases.length;
        int HEIGHT = grid.cases[0].length;

        int x = acase.getPositionX();
        int y = acase.getPositionY();

        if (!acase.isDiscovered()) {

            acase.manage_left_click();

            for (int i = (x - 1); i <= x + 1; i++) {
                for (int j = (y - 1); j <= y + 1; j++) {
                    if (j < HEIGHT && i < WIDTH && j>= 0 && i >= 0){
                        grid.cases[i][j].manage_left_click(grid);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetGrid) {
            if(this.cases[1].length == GRID_LENGTH_SMALL) {
                for (int i = 0; i < GRID_LENGTH_SMALL; i++) {
                    for (int j =0 ; j < GRID_LENGTH_SMALL; j++) {
                        this.remove(this.cases[i][j]);
                    }
                }
                this.init(1);
                this.chronometer.reset();
            } else if (this.cases[1].length == GRID_LENGTH_MEDIUM) {
                for (int i = 0; i < GRID_LENGTH_MEDIUM; i++) {
                    for (int j =0 ; j < GRID_LENGTH_MEDIUM; j++) {
                        this.remove(this.cases[i][j]);
                    }
                }
                this.init(2);
                this.chronometer.reset();
            }
            else if(this.cases[1].length == GRID_LENGTH_LARGE) {
                for (int i = 0; i < GRID_LENGTH_MEDIUM; i++) {
                    for (int j =0 ; j < GRID_LENGTH_LARGE; j++) {
                        this.remove(this.cases[i][j]);
                    }
                }
                this.init(3);
                this.chronometer.reset();
            }
        }
    }
}
