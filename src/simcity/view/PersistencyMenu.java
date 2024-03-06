package simcity.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class PersistencyMenu extends JDialog {

    public JList list;
    protected DefaultListModel<String> l1;
    protected JPanel panel;
    protected JPanel panel2;

    protected JButton cancel;
    protected int current = -1;
    protected int saveFileAmount = 10;

    protected File path;
    protected File[] saveFiles;
    protected ArrayList<String> fileNames;

    public PersistencyMenu() {

        this.panel = new JPanel();
        this.panel2 = new JPanel();

        this.path = new File("data/Saves");
        this.saveFiles = path.listFiles();
        this.fileNames = new ArrayList<>();

        this.cancel = new JButton("Cancel");

        this.saveFiles = path.listFiles();
        this.fileNames = new ArrayList<>();

        if (path.exists()) {
            for (File f : this.saveFiles) {
                fileNames.add(f.getName());
            }
        }

        l1 = new DefaultListModel<>();
        for (int i = 0; i < saveFileAmount; i++) {
            if (fileNames.indexOf("save" + (i + 1) + ".json") != -1) {
                l1.addElement("Save Data - #" + (i + 1));
            } else {
                l1.addElement("Empty Slot - #" + (i + 1));
            }

        }
        this.list = new JList<>(l1);

        list.setFixedCellWidth(500);
        list.setFixedCellHeight(50);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(list);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(cancel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel2.add(BorderLayout.CENTER, panel);

        list.setMinimumSize(new Dimension(500, 500));
        list.setPreferredSize(new Dimension(500, 500));

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(600, 700));
        this.setResizable(false);
        this.setModal(true);
        this.add(panel2);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                current = list.getSelectedIndex();
            }

        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    protected void refreshList() {
        l1.setElementAt("Save Data - #" + (list.getSelectedIndex() + 1), list.getSelectedIndex());
    }

}
