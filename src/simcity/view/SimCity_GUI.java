package simcity.view;

import simcity.events.InteractMode;
import simcity.model.GameEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import static simcity.events.BuildItem.NOTHING;
import static simcity.events.Mode.NORMAL;
import simcity.persistency.Persistency;

public class SimCity_GUI {

    private JFrame frame;
    private GameEngine gameArea;
    private InteractMode iMode;
    private Toolbox toolbox;
    private SpeedMenu speedMenu;
    private StatsPanel statsPanel;
    private LeftMenu leftMenu;
    private MainMenu mainMenu;
    private JPanel panelNorth;
    private JScrollPane scroll;
    private TimerPanel timepanel;
    private Persistency persistency;
    private LoadMenu loadMenu;
    private SaveMenu saveMenu;
    private Timer gameOverTimer;

    private GameMenu gameMenu;
    private StatsMenu statsMenu;

    public SimCity_GUI() {
        try {
            gameArea = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(SimCity_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        reconstructGui();

        frame = new JFrame("SimCity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(mainMenu);
        frame.revalidate();
        frame.setPreferredSize(new Dimension(1600, 900));
        frame.pack();
        frame.setVisible(true);

    }

    public void reconstructGui() {

        persistency = new Persistency();
        loadMenu = new LoadMenu(persistency, this);
        saveMenu = new SaveMenu(persistency, gameArea);
        gameMenu = new GameMenu(saveMenu, loadMenu);
        mainMenu = new MainMenu(this);
        gameOverTimer = new Timer(1000, new GameOverListener());

        panelNorth = new JPanel(new BorderLayout());
        InteractMode.getInstance().setCurrentMode(NORMAL);
        InteractMode.getInstance().setCurrentBuildItem(NOTHING);
        iMode = InteractMode.getInstance();

        toolbox = new Toolbox(gameArea, iMode);
        timepanel = new TimerPanel(gameArea);
        statsPanel = new StatsPanel(gameArea);
        scroll = new JScrollPane(gameArea);

        statsMenu = new StatsMenu(gameArea);
    }

    public void startGame() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        frame.getContentPane().removeAll();

        timepanel.StartTimer();
        statsPanel.startTimer();

        gameArea.startGame();
        gameArea.setOpaque(false);
        gameArea.setPreferredSize(new Dimension(gameArea.getMap().getSize() * 132, gameArea.getMap().getSize() * 68));
        gameArea.setBorder(null);

        gameOverTimer.start();

        speedMenu = new SpeedMenu(gameArea);
        leftMenu = new LeftMenu(this, gameArea, speedMenu);
        panelNorth.add(BorderLayout.EAST, speedMenu.getPanel());
        panelNorth.add(BorderLayout.WEST, timepanel.getPanel());
        panelNorth.add(BorderLayout.CENTER, statsPanel.getPanel());
        panelNorth.setOpaque(false);

        scroll.getVerticalScrollBar().setUnitIncrement(10);
        scroll.getHorizontalScrollBar().setUnitIncrement(10);
        scroll.getVerticalScrollBar().setOpaque(false);
        scroll.getHorizontalScrollBar().setOpaque(false);
        scroll.setViewportBorder(null);
        scroll.setPreferredSize(new Dimension(100, 100));
        scroll.setViewportView(gameArea);

        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(createEmptyBorder());
        scroll.setOpaque(false);

        frame.getContentPane().add(BorderLayout.SOUTH, toolbox.getPanel());
        frame.getContentPane().add(BorderLayout.WEST, leftMenu.getPanel());
        frame.getContentPane().add(BorderLayout.NORTH, panelNorth);
        frame.getContentPane().add(BorderLayout.CENTER, scroll);
        frame.getContentPane().setBackground(new Color(150, 162, 82));

        frame.invalidate();
        frame.validate();
        frame.repaint();
        frame.pack();
    }

    public SaveMenu getSaveMenu() {
        return saveMenu;
    }

    public LoadMenu getLoadMenu() {
        return loadMenu;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public StatsMenu getStatsMenu() {
        return statsMenu;
    }

    public void loadGame(Persistency persistency) {
        this.gameArea = new GameEngine(persistency.getGameData());
        reconstructGui();

        try {
            startGame();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(SimCity_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class GameOverListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (gameArea.isGameOver()) {
                JOptionPane.showMessageDialog(frame, "The City is revolting, you have been over thrown!");
                speedMenu.disableMenu();
                gameOverTimer.stop();
            }

        }
    }
}
