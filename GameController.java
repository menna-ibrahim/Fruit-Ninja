/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruitninjagame;

import animationgui.Frame;
import animationgui.Panel;
import gamestrategy.MediumStrategy;
import gamestrategy.HardStrategy;
import gamestrategy.IGameStrategy;
import gamestrategy.EasyStrategy;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author MIX
 */
public class GameController implements IGameActions {

    private static GameController instance;
    private IGameStrategy strategy;
    Memento memento;
    HighScore highscore;

    private GameController() {
        // initially set strategy
        setStrategy();
        // initially read the last high Score
        readHighScore();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    
    @Override
    public void updateLives(IGameObject g) {
        if (!g.isSliced() && g.hasMovedOffScreen()) {
            if (g.getObjectType() == 0 || g.getObjectType() == 3 || g.getObjectType() == 4) {
                Game.getInstance().getPlayer().getState().setLives(Game.getInstance().getPlayer().getState().getLives() - 1);
                g = null;
            } else {
                g = null;
            }
        }
        saveGame();

    }

    @Override
    public IGameObject createGameObject() {
        Random rand = new Random();
        setStrategy();
        IGameObject g = FactoryOfGameObjects.getInstance().getObject(rand.nextInt(44));
        return g;

    }

    @Override
    public void sliceObjects(IGameObject g, Point point) {
        if ((point.x < g.getXlocation() && point.x >= g.getXlocation() + g.getImage().getIconWidth()) || (point.x > g.getXlocation() && point.x <= g.getXlocation() + g.getImage().getIconWidth())) {
            if (point.y > g.getYlocation() && point.y <= g.getYlocation() + g.getImage().getIconHeight()) {
                if (!g.isSliced()) {
                    g.slice();
                    if (g.getObjectType() == 1) {
                        Game.getInstance().getPlayer().getState().setLives(Game.getInstance().getPlayer().getState().getLives() - 1);
                    } else if (g.getObjectType() == 0) {
                        Game.getInstance().getPlayer().getState().setScore(Game.getInstance().getPlayer().getState().getScore() + 1);
                    } else if (g.getObjectType() == 2) {
                        Game.getInstance().getPlayer().getState().setLives(0);
                    } else if (g.getObjectType() == 3) {
                        Game.getInstance().getPlayer().getState().setScore(Game.getInstance().getPlayer().getState().getScore() + 25);
                    } else if (g.getObjectType() == 4) {
                        Game.getInstance().getPlayer().getState().setLives(Game.getInstance().getPlayer().getState().getLives() + 1);
                    }
                    //updating and setting strategy with every slice
                    setStrategy();
                    saveGame();

                }
            }
        }

       // Player has Reached the high score
        if (Game.getInstance().getPlayer().getState().getScore() > highscore.getHighScore()) {
            if (Game.getInstance().getPlayer().isReachHighScore() == false) {
                Game.getInstance().getPlayer().setReachHighScore(true);
            }
            highscore.setHighScore(Game.getInstance().getPlayer().getState().getScore());
            Panel.updateHighscore();

        }
        // Player has Reached the high score and Game Over
        if (Game.getInstance().getPlayer().getState().getLives() == 0 && Game.getInstance().getPlayer().isReachHighScore()) {
            updateHighScore(Game.getInstance().getPlayer().getState().getScore());

        }
    }
    @Override
    public void setStrategy() {
        if (Game.getInstance().getPlayer().getState().getScore() >= 0 && Game.getInstance().getPlayer().getState().getScore() < 20) {
            strategy = new EasyStrategy();
        } else if (Game.getInstance().getPlayer().getState().getScore() >= 20 && Game.getInstance().getPlayer().getState().getScore() < 50) {
            strategy = new MediumStrategy();
        } else if (Game.getInstance().getPlayer().getState().getScore() >= 50) {
            strategy = new HardStrategy();
        }
    }

    public IGameStrategy getStrategy() {
        return this.strategy;
    }

    @Override
    public void saveGame() {

        try {
            memento = Player.getInstance().save();
            JAXBContext jaxbContext = JAXBContext.newInstance(State.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            OutputStream os = new FileOutputStream("Save.xml");
            if (Player.getInstance().getState().getLives() == 0) {
                deleteSaved();
            } else {
                marshaller.marshal(memento.getState(), os);
            }
        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "An error occurred!!");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "An error occurred!!");
            e.printStackTrace();

        }

    }

    @Override
    public void loadGame() {

        try {
            File inputFile = new File("save.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(State.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            memento = new Memento((State) unmarshaller.unmarshal(inputFile));
            
            
            Player.getInstance().load(memento);
            Frame.getFrame().setPanel(new Panel());

        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "No games to load");
            System.out.println(e.getMessage());

        }
         
        
    }
   

    @Override
    public void ResetGame() {
        File file = new File("save.xml");
        highscore.setHighScore(0);
        updateHighScore(highscore.getHighScore());
        try {
            file.delete();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File not found");
            System.out.println(e.getMessage());

        }
        JOptionPane.showMessageDialog(null, "Reset Complete");
    }

    public void deleteSaved() {
        File file = new File("save.xml");

        try {
            file.delete();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File not found");
            System.out.println(e.getMessage());

        }
    }

    public void readHighScore() {
        JAXBContext jaxbcontext;
        Unmarshaller unmarshaller;
        try {
            jaxbcontext = JAXBContext.newInstance(HighScore.class);
            unmarshaller = jaxbcontext.createUnmarshaller();
            highscore = (HighScore) unmarshaller.unmarshal(new File("HighScore.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public void updateHighScore(int newHighScore) {
        JAXBContext jaxbcontext;
        try {
            jaxbcontext = JAXBContext.newInstance(HighScore.class);
            Marshaller marshaller = jaxbcontext.createMarshaller();
            highscore.setHighScore(newHighScore);
            marshaller.marshal(highscore, new File("HighScore.xml"));
        } catch (JAXBException e) {

            e.printStackTrace();
        }

    }

    public HighScore getHighscore() {
        return highscore;
    }

}
