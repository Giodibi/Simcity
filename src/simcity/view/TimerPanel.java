package simcity.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import simcity.model.GameEngine;

public class TimerPanel {

    private JPanel mainPanel;
    private JPanel panel;
    private JLabel timeLabel;
    private long startTime;
    private Timer timer;

    public TimerPanel(GameEngine gameArea) {
        this.mainPanel = new JPanel(new BorderLayout());
        this.panel = new JPanel();
        this.timeLabel = new JLabel("");
        timeLabel.setFont(new Font("arial", Font.PLAIN, 20));

        panel.setOpaque(false);
        mainPanel.setOpaque(false);

        panel.add(timeLabel);

        mainPanel.add(BorderLayout.EAST, panel);

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText(gameArea.getDate().toString());
            }
        });
        startTime = System.currentTimeMillis();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public void StartTimer() {
        this.timer.start();
    }

    public void StopTimer() {
        this.timer.stop();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

}
