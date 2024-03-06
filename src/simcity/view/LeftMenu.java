package simcity.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;
import javax.swing.JPanel;
import simcity.model.GameEngine;
import simcity.model.Sound;

public class LeftMenu {

    private JPanel panel;
    private JButton menu;
    private JButton stats;
    private JButton sound;
    private Sound sounds;

    public LeftMenu(SimCity_GUI gui, GameEngine engine, SpeedMenu speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.panel = new JPanel();
        this.menu = new JButton("Menu");
        this.stats = new JButton("Stats");
        this.sound = new JButton("ToMute");
        this.sounds = new Sound();

        panel.add(menu);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(stats);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(sound);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.setLayout(new BoxLayout(panel, Y_AXIS));
        panel.setOpaque(false);

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setSpeed(0);
                speed.setStopClick();
                gui.getGameMenu().setVisible(true);
            }
        });
        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setSpeed(0);
                speed.setStopClick();
                engine.calculateExpenses();
                engine.calculateIncome();
                gui.getStatsMenu().refreshTable(engine);
                gui.getStatsMenu().refreshSecondTable(engine);
                gui.getStatsMenu().canChangeTaxes(engine);
                gui.getStatsMenu().setVisible(true);
            }
        });
        sound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.muted(); //ellenkezőjére cseréli mint ami volt
                if (!engine.isMuted()) {
                    sound.setText("ToMute");
                } else {
                    sound.setText("Unmute");
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

}
