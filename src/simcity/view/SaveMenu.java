package simcity.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import simcity.model.GameEngine;
import simcity.persistency.GameData;
import simcity.persistency.Persistency;

public class SaveMenu extends PersistencyMenu {

    private JButton save;

    public SaveMenu(Persistency persistency, GameEngine engine) {
        super();

        this.setTitle("SimCity - Save menu");
        this.save = new JButton("Save");

        this.panel.add(save);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame dialog = new JFrame("Save Game");
                if (current != -1) {
                    persistency.setGameData(new GameData(engine));
                    persistency.saveGame(current + 1);
                    JOptionPane.showMessageDialog(dialog, "Game state succesfully saved to slot #" + (current + 1));
                    refreshList();
                } else {
                    JOptionPane.showMessageDialog(dialog, "No slot selected, saving not possible!");

                }
            }
        });
    }
}
