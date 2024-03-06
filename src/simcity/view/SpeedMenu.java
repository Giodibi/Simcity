package simcity.view;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import simcity.model.GameEngine;

public class SpeedMenu {

    private JPanel mainPanel;
    private JPanel panel;
    private JToggleButton stop;
    private JToggleButton normal;
    private JToggleButton fast;
    private JToggleButton extraFast;
    private JToggleButton[] buttonArray;

    public SpeedMenu(GameEngine gameArea) {
        this.mainPanel = new JPanel(new BorderLayout());
        this.panel = new JPanel();
        this.stop = new JToggleButton("||");
        this.normal = new JToggleButton("▷");
        this.fast = new JToggleButton("|▷");
        this.extraFast = new JToggleButton("▷▷");
        this.buttonArray = new JToggleButton[]{
            stop, normal, fast, extraFast
        };

        panel.setOpaque(false);
        mainPanel.setOpaque(false);

        panel.add(stop);
        panel.add(normal);
        panel.add(fast);
        panel.add(extraFast);

        mainPanel.add(BorderLayout.EAST, panel);

        stop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (stop.isSelected()) {
                    unToggleAll(stop);
                    gameArea.setSpeed(0);
                    stop.setEnabled(false);
                }
            }
        });

        normal.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (normal.isSelected()) {
                    unToggleAll(normal);
                    gameArea.setSpeed(1);
                    normal.setEnabled(false);
                }
            }
        });

        fast.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (fast.isSelected()) {
                    unToggleAll(fast);
                    gameArea.setSpeed(2);
                    fast.setEnabled(false);
                }
            }
        });

        extraFast.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (extraFast.isSelected()) {
                    unToggleAll(extraFast);
                    gameArea.setSpeed(3);
                    extraFast.setEnabled(false);
                }
            }
        });

        this.normal.setSelected(true);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void unToggleAll(JToggleButton button) {
        for (JToggleButton b : buttonArray) {
            if (!b.equals(button)) {
                b.setSelected(false);
                b.setEnabled(true);
            }
        }
    }

    public void disableMenu() {
        for (JToggleButton b : buttonArray) {
            b.setEnabled(false);
        }
    }

    public void setStopClick() {
        unToggleAll(stop);
        stop.setSelected(true);
        stop.setEnabled(false);
    }
}
