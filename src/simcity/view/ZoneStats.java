package simcity.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import simcity.model.Service;
import simcity.model.Residential;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import simcity.model.GameEngine;
import simcity.model.Zone;

public class ZoneStats extends JDialog {

    private JPanel panel;
    private JPanel container;

    private JLabel capacity;
    private JLabel happiness;
    private JLabel level;
    private JButton upgrade;

    public ZoneStats(Zone z, GameEngine engine) {
        this.panel = new JPanel();
        this.container = new JPanel();

        if (z instanceof Residential) {
            this.setTitle("SimCity - Lakó zóna");
        } else if (z instanceof Service) {
            this.setTitle("SimCity - Szolgáltatási zóna");
        } else {
            this.setTitle("SimCity - Ipari zóna");
        }
        this.capacity = new JLabel("Capacity: " + z.getPeople().size() + " / " + z.getMaxCapacity());
        z.CalcHappiness(engine);
        this.happiness = new JLabel("Happiness: " + z.getHappines());
        this.level = new JLabel("Level: " + z.getLevel());
        this.upgrade = new JButton("Upgrade Zone - " + z.getUpgradeCost() + " $");

        capacity.setFont(new Font("arial", Font.PLAIN, 20));
        happiness.setFont(new Font("arial", Font.PLAIN, 20));
        level.setFont(new Font("arial", Font.PLAIN, 20));

        upgrade.setMinimumSize(new Dimension(400, 100));
        upgrade.setMaximumSize(new Dimension(400, 100));
        upgrade.setPreferredSize(new Dimension(400, 100));

        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(capacity);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(happiness);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(level);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(upgrade);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        container.add(BorderLayout.CENTER, panel);

        upgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                z.Upgrade();
                z.Doing();
                level.setText("Level: " + z.getLevel());
                upgrade.setText("Upgrade Zone - " + z.getUpgradeCost() + " $");
                capacity.setText("Capacity: " + z.getPeople().size() + " / " + z.getMaxCapacity());
                try {
                    engine.changeFunds(-z.getUpgradeCost());
                } catch (UnsupportedAudioFileException | InterruptedException ex) {
                    Logger.getLogger(ZoneStats.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        this.setModal(true);
        this.setMinimumSize(new Dimension(600, 500));
        this.setLayout(new BorderLayout());
        this.getContentPane().add(BorderLayout.CENTER, container);

    }
}
