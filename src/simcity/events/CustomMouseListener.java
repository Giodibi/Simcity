package simcity.events;

import simcity.model.Ground;
import simcity.model.Building;
import simcity.model.Road;
import simcity.model.Police;
import simcity.model.Service;
import simcity.model.Residential;
import simcity.model.Forest;
import simcity.model.Map;
import simcity.model.Stadium;
import simcity.model.Industrial;
import simcity.model.Zone;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import simcity.model.Catastrophe;
import simcity.model.GameEngine;
import simcity.model.Sound;
import simcity.model.Person;
import simcity.view.Sprite;
import simcity.view.ZoneStats;

public class CustomMouseListener extends MouseAdapter {

    private Mode interactMode;
    private BuildItem builditem;
    private Sprite s;
    private ZoneStats stats;
    private Map map;
    private GameEngine engine;
    private Sound play;
    private Boolean spriteBounds;

    public CustomMouseListener(Sprite s, GameEngine engine) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.map = s.getMap();
        this.s = s;
        this.engine = engine;

        try {
            this.play = new Sound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setS(Sprite s) {
        this.s = s;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int posX = e.getPoint().x;
        int posY = e.getPoint().y;
        spriteBounds = (posX >= s.getX() + s.getWidth() / 3 && posX <= s.getX() + s.getWidth() - s.getWidth() / 3) && (posY >= s.getY() + 68 + 68 / 3 && posY <= s.getY() + s.getHeight() - 68 / 3);

        if (spriteBounds && !(s instanceof Stadium)) {
            s.setOffset(-10);

        } else {
            s.setOffset(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        interactMode = InteractMode.getInstance().getCurrentMode();
        builditem = InteractMode.getInstance().getCurrentBuildItem();

        int posX = e.getPoint().x;
        int posY = e.getPoint().y;
        spriteBounds = (posX >= s.getX() + s.getWidth() / 3 && posX <= s.getX() + s.getWidth() - s.getWidth() / 3) && (posY >= s.getY() + 68 + 68 / 3 && posY <= s.getY() + s.getHeight() - 68 / 3);

        if (spriteBounds) {
            Ground newGround = null;
            int moneyAmount = 0;
            switch (interactMode) {
                case DESTROY:
                    ///Call appropriate destroy method
                    try {
                    if (!this.engine.isMuted() && !(s instanceof Ground) && !(s instanceof Road)) {
                        if (s instanceof Forest) {
                            if (engine.getSpeed() == 1) {
                                play.play("data/Sounds/Building/Forest/MetalSawCutting_16.wav", engine.getSpeed());
                            } else if (engine.getSpeed() == 2) {
                                play.play("data/Sounds/Building/Forest/f_MetalSawCutting_16.wav", engine.getSpeed());
                            } else if (engine.getSpeed() == 3) {
                                play.play("data/Sounds/Building/Forest/ff_MetalSawCutting_16.wav", engine.getSpeed());
                            }
                        } else {
                            if (engine.getSpeed() == 1) {
                                play.play("data/Sounds/Destroy/Explosion_16.wav", engine.getSpeed());
                            } else if (engine.getSpeed() == 2) {
                                play.play("data/Sounds/Destroy/f_Explosion_16.wav", engine.getSpeed());
                            } else if (engine.getSpeed() == 3) {
                                play.play("data/Sounds/Destroy/ff_Explosion_16.wav", engine.getSpeed());
                            }
                        }

                    }
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                }

                int[] ind = map.getTileIndex(s);
                System.out.println(ind[0]);
                System.out.println(ind[1]);

                Sprite stadium_1 = null;
                Sprite stadium_2 = null;
                Sprite stadium_3 = null;
                Ground stadium_1_g = null;
                Ground stadium_2_g = null;
                Ground stadium_3_g = null;

                if (ind[0] == 0 && ind[1] == 0) { ///main road cant be destroyed
                    return;
                }

                if (s instanceof Building) {
                    try {
                        boolean isLeftCornerStadium
                                = map.getSpriteFromIndex(ind[0] + 1, ind[1]) instanceof Stadium
                                && map.getSpriteFromIndex(ind[0] + 1, ind[1] - 1) instanceof Stadium
                                && map.getSpriteFromIndex(ind[0], ind[1] - 1) instanceof Stadium;

                        if (isLeftCornerStadium && s instanceof Stadium) {
                            System.out.println("Stadion törlés");
                            stadium_1 = map.getSpriteFromIndex(ind[0] + 1, ind[1]);
                            stadium_2 = map.getSpriteFromIndex(ind[0] + 1, ind[1] - 1);
                            stadium_3 = map.getSpriteFromIndex(ind[0], ind[1] - 1);
                            System.out.println("leképzeve");

                            newGround = ((Building) s).Revert();
                            stadium_1_g = ((Building) stadium_1).Revert();
                            stadium_2_g = ((Building) stadium_2).Revert();
                            stadium_3_g = ((Building) stadium_3).Revert();

                            stadium_1_g.setMouseAdapter(new CustomMouseListener(stadium_1_g, engine));
                            engine.removeMouseListener(stadium_1.getMouseAdapter());
                            engine.addMouseListener(stadium_1_g.getMouseAdapter());
                            engine.addMouseMotionListener(stadium_1_g.getMouseAdapter());

                            map.setTile(ind[0] + 1, ind[1], stadium_1_g);

                            stadium_2_g.setMouseAdapter(new CustomMouseListener(stadium_2_g, engine));
                            engine.removeMouseListener(stadium_2.getMouseAdapter());
                            engine.addMouseListener(stadium_2_g.getMouseAdapter());
                            engine.addMouseMotionListener(stadium_2_g.getMouseAdapter());

                            map.setTile(ind[0] + 1, ind[1] - 1, stadium_2_g);

                            stadium_3_g.setMouseAdapter(new CustomMouseListener(stadium_3_g, engine));
                            engine.removeMouseListener(stadium_3.getMouseAdapter());
                            engine.addMouseListener(stadium_3_g.getMouseAdapter());
                            engine.addMouseMotionListener(stadium_3_g.getMouseAdapter());

                            map.setTile(ind[0], ind[1] - 1, stadium_3_g);

                        } else if (!isLeftCornerStadium && s instanceof Stadium) {
                            return;
                        } else {
                            newGround = ((Building) s).Revert();
                            moneyAmount = (((Building) s).getCost()) / 3;   ///Refund is going to be a third of the original cost                        }

                        }

                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (s instanceof Zone && ((Zone) s).getPeople().isEmpty()) {
                    newGround = ((Zone) s).Revert();
                    moneyAmount = (((Zone) s).getCost()) / 3;
                } else if (s instanceof Road) {
                    try {
                        newGround = ((Road) s).Revert();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                        Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    moneyAmount = (((Road) s).getCost()) / 3;

                } else {
                }
                if (newGround != null) {
                    s = newGround;
                }
                break;

                case BUILD:

                    if (s instanceof Building || s instanceof Road || s instanceof Zone) { ///if there is already a Building (not empty), then cant build there
                        return;
                    }

                    ///Call appropriate build method
                    Sprite newSprite = null;
                    int[] index = map.getTileIndex(s);

                    if (index[0] == 0 && index[1] == 0) { ///main road cant be changed
                        return;
                    }

                    switch (builditem) {
                        case FOREST: {
                            try {
                                if (!this.engine.isMuted()) {
                                    if (engine.getSpeed() == 1) {
                                        play.play("data/Sounds/Building/Forest/Plant_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 2) {
                                        play.play("data/Sounds/Building/Forest/f_Plant_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 3) {
                                        play.play("data/Sounds/Building/Forest/ff_Plant_16.wav", engine.getSpeed());
                                    }
                                }
                                newSprite = new Forest(s.getX(), s.getY(), engine.getDate(), map, engine.getSpeed());
                                map.setTile(index[0], index[1], newSprite);
                                moneyAmount = -((Forest) newSprite).getCost();
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                        case INDUSTRIAL: {
                            try {
                                if (!this.engine.isMuted()) {
                                    if (engine.getSpeed() == 1) {
                                        play.play("data/Sounds/Build/Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 2) {
                                        play.play("data/Sounds/Build/f_Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 3) {
                                        play.play("data/Sounds/Build/ff_Hammer_16.wav", engine.getSpeed());
                                    }
                                }
                                newSprite = new Industrial(s.getX(), s.getY(), "data/Sprites/Zone/Industrial/lvl0.png", "data/Sounds/Zone/Industrial/IndustryHammer_16.wav", map, engine.getSpeed());
                                map.setTile(index[0], index[1], newSprite);
                                moneyAmount = -((Industrial) newSprite).getCost();

                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case POLICE: {
                            try {
                                if (!this.engine.isMuted()) {
                                    if (engine.getSpeed() == 1) {
                                        play.play("data/Sounds/Build/Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 2) {
                                        play.play("data/Sounds/Build/f_Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 3) {
                                        play.play("data/Sounds/Build/ff_Hammer_16.wav", engine.getSpeed());
                                    }
                                }
                                newSprite = new Police(s.getX(), s.getY(), map, engine.getSpeed());
                                map.setTile(index[0], index[1], newSprite);
                                moneyAmount = -((Police) newSprite).getCost();

                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                        case RESIDENTIAL: {
                            try {
                                if (!this.engine.isMuted()) {
                                    if (engine.getSpeed() == 1) {
                                        play.play("data/Sounds/Build/Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 2) {
                                        play.play("data/Sounds/Build/f_Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 3) {
                                        play.play("data/Sounds/Build/ff_Hammer_16.wav", engine.getSpeed());
                                    }
                                }
                                newSprite = new Residential(s.getX(), s.getY(), "data/Sprites/Zone/Residential/Level0/residential0.png", "data/Sounds/Zone/Residential/Crowd_16.wav", map, engine.getSpeed());
                                map.setTile(index[0], index[1], newSprite);
                                moneyAmount = -((Residential) newSprite).getCost();

                                if (!engine.getStarterCitizens()) {
                                    for (int i = 0; i < 5; i++) {
                                        engine.getCitizens().add(new Person(map, engine.getCitizens()));
                                    }
                                    engine.setStarterCitizens();
                                    ((Residential) newSprite).LevelUp();
                                }

                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                        case ROAD: {
                            try {
                                if (!this.engine.isMuted()) {
                                    if (engine.getSpeed() == 1) {
                                        play.play("data/Sounds/Build/Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 2) {
                                        play.play("data/Sounds/Build/f_Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 3) {
                                        play.play("data/Sounds/Build/ff_Hammer_16.wav", engine.getSpeed());
                                    }
                                }
                                newSprite = new Road(s.getX(), s.getY(), map, engine.getSpeed());
                                map.setTile(index[0], index[1], newSprite);
                                moneyAmount = -((Road) newSprite).getCost();
                                ((Road) newSprite).SelectImage(false);
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                        case SERVICE: {
                            try {
                                if (!this.engine.isMuted()) {
                                    if (engine.getSpeed() == 1) {
                                        play.play("data/Sounds/Build/Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 2) {
                                        play.play("data/Sounds/Build/f_Hammer_16.wav", engine.getSpeed());
                                    } else if (engine.getSpeed() == 3) {
                                        play.play("data/Sounds/Build/ff_Hammer_16.wav", engine.getSpeed());
                                    }
                                }
                                newSprite = new Service(s.getX(), s.getY(), "data/Sprites/Zone/Service/lvl0.png", "data/Sounds/Zone/Service/ShopingCenter_16.wav", map, engine.getSpeed());
                                map.setTile(index[0], index[1], newSprite);
                                moneyAmount = -((Service) newSprite).getCost();

                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                        case STADIUM: {
                            try {
                                boolean isValidPlacement = map.getSpriteFromIndex(index[0], index[1]) instanceof Ground && (index[0] + 1 < map.getSize() && map.getSpriteFromIndex(index[0] + 1, index[1]) instanceof Ground) && (index[0] + 1 < map.getSize() && index[1] - 1 >= 0 && map.getSpriteFromIndex(index[0] + 1, index[1] - 1) instanceof Ground) && (index[1] - 1 >= 0 && map.getSpriteFromIndex(index[0], index[1] - 1) instanceof Ground);
                                if (isValidPlacement) {
                                    if (!this.engine.isMuted()) {
                                        if (engine.getSpeed() == 1) {
                                            play.play("data/Sounds/Build/Hammer_16.wav", engine.getSpeed());
                                        } else if (engine.getSpeed() == 2) {
                                            play.play("data/Sounds/Build/f_Hammer_16.wav", engine.getSpeed());
                                        } else if (engine.getSpeed() == 3) {
                                            play.play("data/Sounds/Build/ff_Hammer_16.wav", engine.getSpeed());
                                        }
                                    }

                                    newSprite = new Stadium(s.getX(), s.getY(), map, "data/Sprites/Building/Stadium/stadium_1.png", engine.getSpeed());
                                    Sprite newSprite1 = new Stadium(s.getX() + 66, s.getY() + 33, map, "data/Sprites/Building/Stadium/stadium_3.png", false, engine.getSpeed());
                                    Sprite newSprite2 = new Stadium(s.getX() + 132, s.getY(), map, "data/Sprites/Building/Stadium/stadium_4.png", false, engine.getSpeed());
                                    Sprite newSprite3 = new Stadium(s.getX() + 66, s.getY() - 33, map, "data/Sprites/Building/Stadium/stadium_2.png", false, engine.getSpeed());

                                    map.setTile(index[0], index[1], newSprite);

                                    newSprite1.setMouseAdapter(new CustomMouseListener(newSprite1, engine));
                                    engine.removeMouseListener(map.getSpriteFromIndex(index[0] + 1, index[1]).getMouseAdapter());
                                    engine.addMouseListener(newSprite1.getMouseAdapter());
                                    map.setTile(index[0] + 1, index[1], newSprite1);

                                    newSprite2.setMouseAdapter(new CustomMouseListener(newSprite2, engine));
                                    engine.removeMouseListener(map.getSpriteFromIndex(index[0] + 1, index[1] - 1).getMouseAdapter());
                                    engine.addMouseListener(newSprite2.getMouseAdapter());
                                    map.setTile(index[0] + 1, index[1] - 1, newSprite2);

                                    newSprite3.setMouseAdapter(new CustomMouseListener(newSprite3, engine));
                                    engine.removeMouseListener(map.getSpriteFromIndex(index[0], index[1] - 1).getMouseAdapter());
                                    engine.addMouseListener(newSprite3.getMouseAdapter());
                                    map.setTile(index[0], index[1] - 1, newSprite3);

                                    moneyAmount = -((Stadium) newSprite).getCost();
                                } else {
                                    return;
                                }

                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        default:
                            break;
                    }
                    s = newSprite;
                    break;

                case CATASTROPHE:

                    int[] inds = map.getTileIndex(s);

                    if (inds[0] == 0 && inds[1] == 0) { ///main road cant be destroyed
                        return;
                    }
                    Catastrophe tempCat;

                    //Call appropriate build method
                    if (s instanceof Building) {
                        try {
                            tempCat = engine.getRandCat().getRandomCatastrophe();
                            if (engine.catResult(tempCat)) {
                                newGround = ((Building) s).Revert();
                            }
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                            Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (s instanceof Zone) {
                        try {
                            tempCat = engine.getRandCat().getRandomCatastrophe();
                            if (engine.catResult(tempCat)) {
                                newGround = ((Zone) s).Revert();
                            }
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                            Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (s instanceof Road) {
                        try {
                            tempCat = engine.getRandCat().getRandomCatastrophe();
                            if (engine.catResult(tempCat)) {
                                try {
                                    newGround = ((Road) s).Revert();
                                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                                    Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                            Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                    }
                    if (newGround != null) {
                        s = newGround;
                    }

                    break;
                default:
                    //Display zone information
                    if (s instanceof Zone) {
                        this.stats = new ZoneStats((Zone) s, engine);
                        stats.setVisible(true);
                        if (!engine.isMuted()) {
                            s.playL(engine.getSpeed());
                        }

                    } else if (s instanceof Building || s instanceof Road) {
                        if (!engine.isMuted()) {
                            s.playL(engine.getSpeed());
                        }
                    }

                    break;
            }
            try {
                engine.changeFunds(moneyAmount);
            } catch (UnsupportedAudioFileException | InterruptedException ex) {
                Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
