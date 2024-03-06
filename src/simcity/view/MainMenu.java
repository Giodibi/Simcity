package simcity.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenu extends JPanel {

    private JPanel innerPanel;
    private JButton newGame;
    private JButton loadGame;
    private JButton exitGame;

    public MainMenu(SimCity_GUI gui) {

        this.innerPanel = new JPanel();
        this.newGame = new JButton("New Game");
        this.loadGame = new JButton("Load Game");
        this.exitGame = new JButton("Exit");
        newGame.setMinimumSize(new Dimension(400, 100));
        newGame.setMaximumSize(new Dimension(400, 100));
        newGame.setPreferredSize(new Dimension(400, 100));
        loadGame.setMinimumSize(new Dimension(400, 100));
        loadGame.setMaximumSize(new Dimension(400, 100));
        loadGame.setPreferredSize(new Dimension(400, 100));
        exitGame.setMinimumSize(new Dimension(400, 100));
        exitGame.setMaximumSize(new Dimension(400, 100));
        exitGame.setPreferredSize(new Dimension(400, 100));

        innerPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        innerPanel.add(newGame);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        innerPanel.add(loadGame);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        innerPanel.add(exitGame);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        this.add(BorderLayout.CENTER, innerPanel);
        this.setOpaque(true);
        innerPanel.setOpaque(false);
        this.setVisible(true);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gui.startGame();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getLoadMenu().setVisible(true);
            }
        });

        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Any unsaved progress will be lost!", "Quit Game?", JOptionPane.YES_NO_CANCEL_OPTION);
                if (input == 0) {
                    System.exit(0);
                }
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("data/Menu/bg.jpg").getImage(), 0, 0, 1600, 900, null);
    }

}
