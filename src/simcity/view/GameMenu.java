package simcity.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameMenu extends JDialog {

    private JPanel panel;
    private JPanel panelBig;
    private JButton resume;
    private JButton load;
    private JButton save;
    private JButton exit;

    public GameMenu(SaveMenu saveMenu, LoadMenu loadMenu) {
        this.panel = new JPanel();
        this.panelBig = new JPanel();
        this.resume = new JButton("Resume");
        this.load = new JButton("Load Game");
        this.save = new JButton("Save Game");
        this.exit = new JButton("Exit");

        resume.setMinimumSize(new Dimension(400, 100));
        resume.setMaximumSize(new Dimension(400, 100));
        resume.setPreferredSize(new Dimension(400, 100));
        load.setMinimumSize(new Dimension(400, 100));
        load.setMaximumSize(new Dimension(400, 100));
        load.setPreferredSize(new Dimension(400, 100));
        save.setMinimumSize(new Dimension(400, 100));
        save.setMaximumSize(new Dimension(400, 100));
        save.setPreferredSize(new Dimension(400, 100));
        exit.setMinimumSize(new Dimension(400, 100));
        exit.setMaximumSize(new Dimension(400, 100));
        exit.setPreferredSize(new Dimension(400, 100));

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(resume);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(load);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(save);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exit);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panelBig.add(BorderLayout.CENTER, panel);

        this.setModal(true);
        this.setTitle("SimCity - Game Menu");
        this.setMinimumSize(new Dimension(600, 600));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(panelBig);

        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenu.setVisible(true);
            }

        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMenu.setVisible(true);
            }

        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Any unsaved progress will be lost!", "Quit Game?", JOptionPane.YES_NO_CANCEL_OPTION);
                if (input == 0) {
                    System.exit(0);
                }
            }

        });

    }

}
