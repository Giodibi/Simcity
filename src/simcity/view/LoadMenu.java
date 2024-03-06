package simcity.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import simcity.persistency.Persistency;

public class LoadMenu extends PersistencyMenu {

    private JButton load;

    public LoadMenu(Persistency persistency, SimCity_GUI gui) {
        super();

        this.setTitle("SimCity - Load menu");
        this.load = new JButton("Load");

        this.panel.add(load);

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame dialog = new JFrame("Load Game");
                if (current != -1 && persistency.loadGame(current + 1)) {
                    persistency.loadGame(current + 1);
                    gui.loadGame(persistency);
                    JOptionPane.showMessageDialog(dialog, "Save file #" + (current + 1) + " succesfully loaded!");
                } else {
                    JOptionPane.showMessageDialog(dialog, "No save data selected, loading not possible!");

                }
            }
        });
    }
}
