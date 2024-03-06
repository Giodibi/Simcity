package simcity.view;

import simcity.events.Mode;
import simcity.events.InteractMode;
import simcity.events.BuildItem;
import simcity.model.GameEngine;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import simcity.model.Catastrophe;

class Toolbox {

    private JPanel mainPanel;
    private JPanel panel;
    private JPanel zonesPanel;
    private JPanel catastrophePanel;
    private JToggleButton destroy;
    private JToggleButton zones;
    private JToggleButton residential;
    private JToggleButton industrial;
    private JToggleButton service;
    private JToggleButton roads;
    private JToggleButton police;
    private JToggleButton stadium;
    private JToggleButton forests;
    private JToggleButton catastrophe;
    private JToggleButton catastrophe_rand;
    private JToggleButton catastrophe_choosen;
    private JToggleButton[] buttonArray;

    private Mode mode;

    public Toolbox(GameEngine gameArea, InteractMode interactMode) {
        this.mainPanel = new JPanel(new BorderLayout());
        this.panel = new JPanel();
        this.zonesPanel = new JPanel();
        this.catastrophePanel = new JPanel();

        this.destroy = new JToggleButton("Destroy");
        this.zones = new JToggleButton("Zones - 2.000$");
        this.residential = new JToggleButton("Residential");
        this.industrial = new JToggleButton("Industrial");
        this.service = new JToggleButton("Service");
        this.roads = new JToggleButton("Roads - 900$");
        this.police = new JToggleButton("Police - 2.000$");
        this.stadium = new JToggleButton("Stadium - 3.000$");
        this.forests = new JToggleButton("Forests - 600$");
        this.catastrophe = new JToggleButton("Catastrophe");
        this.catastrophe_rand = new JToggleButton("Random");
        this.catastrophe_choosen = new JToggleButton("Manual");

        this.buttonArray = new JToggleButton[]{
            destroy, zones, residential, industrial, service, roads,
            police, stadium, forests, catastrophe, catastrophe_rand,
            catastrophe_choosen
        };

        panel.setOpaque(false);
        mainPanel.setOpaque(false);
        zonesPanel.setOpaque(false);
        catastrophePanel.setOpaque(false);

        panel.add(destroy);
        panel.add(zones);
        panel.add(roads);
        panel.add(police);
        panel.add(stadium);
        panel.add(forests);
        panel.add(catastrophe);

        zonesPanel.add(residential);
        zonesPanel.add(industrial);
        zonesPanel.add(service);
        zonesPanel.setVisible(false);

        catastrophePanel.add(catastrophe_rand);
        catastrophePanel.add(catastrophe_choosen);
        catastrophePanel.setVisible(false);

        mainPanel.add(BorderLayout.NORTH, zonesPanel);
        mainPanel.add(BorderLayout.CENTER, catastrophePanel);
        mainPanel.add(BorderLayout.SOUTH, panel);

        destroy.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (destroy.isSelected()) {
                    unToggleAll(destroy);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/destroy_c.png").getImage(),
                            new Point(16, 10), "custom cursor"));
                    interactMode.setCurrentMode(mode.DESTROY);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });

        zones.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (zones.isSelected()) {
                    unToggleAll(zones);
                    zonesPanel.setVisible(true);
                } else {
                    zonesPanel.setVisible(false);
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        catastrophe.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (catastrophe.isSelected()) {
                    unToggleAll(catastrophe);
                    catastrophePanel.setVisible(true);
                } else {
                    catastrophePanel.setVisible(false);
                }
            }
        });

        forests.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (forests.isSelected()) {
                    unToggleAll(forests);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/forest_c_res.png").getImage(),
                            new Point(16, 24), "custom cursor"));
                    interactMode.setCurrentMode(mode.BUILD);
                    interactMode.setCurrentBuildItem(BuildItem.FOREST);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        police.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (police.isSelected()) {
                    unToggleAll(police);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/police_c_res.png").getImage(),
                            new Point(16, 24), "custom cursor"));
                    interactMode.setCurrentMode(mode.BUILD);
                    interactMode.setCurrentBuildItem(BuildItem.POLICE);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        roads.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (roads.isSelected()) {
                    unToggleAll(roads);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/road_c_res.png").getImage(),
                            new Point(16, 24), "custom cursor"));
                    interactMode.setCurrentMode(mode.BUILD);
                    interactMode.setCurrentBuildItem(BuildItem.ROAD);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        stadium.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (stadium.isSelected()) {
                    unToggleAll(stadium);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/stadium_icon.png").getImage(),
                            new Point(16, 24), "custom cursor"));
                    interactMode.setCurrentMode(mode.BUILD);
                    interactMode.setCurrentBuildItem(BuildItem.STADIUM);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        residential.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (residential.isSelected()) {
                    unToggleAll(residential);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/res_c.png").getImage(),
                            new Point(16, 24), "custom cursor"));
                    interactMode.setCurrentMode(mode.BUILD);
                    interactMode.setCurrentBuildItem(BuildItem.RESIDENTIAL);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        industrial.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (industrial.isSelected()) {
                    unToggleAll(industrial);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/ind_c.png").getImage(),
                            new Point(16, 24), "custom cursor"));
                    interactMode.setCurrentMode(mode.BUILD);
                    interactMode.setCurrentBuildItem(BuildItem.INDUSTRIAL);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        service.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (service.isSelected()) {
                    unToggleAll(service);
                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/ser_c.png").getImage(),
                            new Point(16, 24), "custom cursor"));
                    interactMode.setCurrentMode(mode.BUILD);
                    interactMode.setCurrentBuildItem(BuildItem.SERVICE);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        catastrophe_choosen.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (catastrophe_choosen.isSelected()) {
                    unToggleAll(catastrophe_choosen);

                    gameArea.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon("data/Sprites/Cursor/catastrophe_c.png").getImage(),
                            new Point(16, 16), "custom cursor"));
                    interactMode.setCurrentMode(mode.CATASTROPHE);
                } else {
                    gameArea.setCursor(Cursor.getDefaultCursor());
                    interactMode.setCurrentMode(mode.NORMAL);
                }
            }

        });
        catastrophe_rand.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (catastrophe_rand.isSelected()) {
                    unToggleAll(catastrophe_rand);
                    try {
                        //Call random catastrophe from model
                        Catastrophe tempCat;
                        tempCat = gameArea.getRandCat().getRandomCatastrophe();
                        gameArea.catResult(tempCat);
                        System.out.println("Random cat happened! Type: " + tempCat);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                        Logger.getLogger(Toolbox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void unToggleAll(JToggleButton button) {
        for (JToggleButton b : buttonArray) {
            if (!b.equals(button)) {
                if ((button.equals(residential) || button.equals(service) || button.equals(industrial)) && b.equals(zones)) {
                    b.setSelected(true);
                } else if ((button.equals(catastrophe_choosen) || button.equals(catastrophe_rand)) && b.equals(catastrophe)) {
                    b.setSelected(true);
                } else {
                    b.setSelected(false);
                }
            }
        }
    }
}
