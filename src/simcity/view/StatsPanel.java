package simcity.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import simcity.model.GameEngine;

public class StatsPanel {

    private JPanel panel;
    private JLabel funds;
    private JLabel pops;
    private JLabel happiness;
    private Timer refreshTimer;

    public StatsPanel(GameEngine gameArea) {
        this.panel = new JPanel();
        this.funds = new JLabel("Funds: " + gameArea.getFunds() + " $");
        this.pops = new JLabel("Pops: " + gameArea.getPops());;
        this.happiness = new JLabel("Happiness: " + gameArea.getHappiness());;

        funds.setFont(new Font("arial", Font.PLAIN, 20));
        pops.setFont(new Font("arial", Font.PLAIN, 20));
        happiness.setFont(new Font("arial", Font.PLAIN, 20));

        panel.add(funds);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(pops);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(happiness);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.setOpaque(false);

        refreshTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funds.setText("Funds: " + gameArea.getFunds() + " $");
                pops.setText("Pops: " + gameArea.getPops());
                happiness.setText("Happiness: " + gameArea.getHappiness());
            }
        });

    }

    public void startTimer() {
        refreshTimer.start();
    }

    public JPanel getPanel() {
        return panel;
    }

    public Timer getTimer() {
        return this.refreshTimer;
    }

}
