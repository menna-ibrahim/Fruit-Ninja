
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationgui;

import commands.*;
import fruitninjagame.Game;
import fruitninjagame.GameController;
import fruitninjagame.IGameObject;
import fruitninjagame.Player;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author MIX
 */
public class Panel extends JPanel implements MouseListener, MouseMotionListener {

    ImageIcon background;
    static JLabel scoreLbl;
    static JLabel highscore;
    JLabel timerLbl;
    JLabel timePlayedLbl;
    Animation a;
    Point pressed;
    Point release;
    int timeInterval;
    boolean dragging = false;
    private static Timer timer;
    static Timer t;
    static Panel panel;
    private Boolean paused = false;

    public Boolean getPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public Panel() {

        panel = this;
        setLayout(null);
        background = new ImageIcon("background.jpg");

        scoreLbl = new JLabel(Player.playerState());
        highscore = new JLabel(GameController.getInstance().getHighscore().toString());
        timerLbl = new JLabel();
        timePlayedLbl = new JLabel("Time Played: ");
        JButton mainMenu = new JButton("Back");

        scoreLbl.setBounds(20, 5, 250, 80);
        highscore.setBounds(950, 5, 300, 40);
        timePlayedLbl.setBounds(400, 5, 250, 40);
        timerLbl.setBounds(630, 5, 100, 40);
        mainMenu.setBounds(20, 600, 100, 50);

        scoreLbl.setFont(new Font("Verdana", Font.BOLD, 30));
        highscore.setFont(new Font("Verdana", Font.BOLD, 30));
        timerLbl.setFont(new Font("Verdana", Font.BOLD, 30));
        scoreLbl.setFont(new Font("Verdana", Font.BOLD, 30));
        timePlayedLbl.setFont(new Font("Verdana", Font.BOLD, 30));
        mainMenu.setFont(new Font("Verdana", Font.BOLD, 20));

        Date start = new Date(System.currentTimeMillis() + 3600 * 2000);
        start.setTime(start.getTime() - Player.getInstance().getState().getTime());
        //count up timer label on screen
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");;
                Date now = new Date();
                String time = sdf.format(now.getTime() - start.getTime());
                Player.getInstance().getState().setTime(now.getTime() - start.getTime());
                String playerTime = sdf.format(Player.getInstance().getState().getTime());
                timerLbl.setText(new String(sdf.format(Player.getInstance().getState().getTime())));
                GameController.getInstance().saveGame();
                if (Game.getInstance().getPlayer().getState().getLives() == 0 || paused) {
                    timer.stop();
                }

            }
        });

        //creating objects with time intervals
        timeInterval = GameController.getInstance().getStrategy().getTimeInterval();
        t = new Timer(timeInterval, new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                timeInterval = GameController.getInstance().getStrategy().getTimeInterval();
                IGameObject g = GameController.getInstance().createGameObject();
                Game.getInstance().getGameObjects().add(g);
                if (Game.getInstance().getPlayer().getState().getLives() == 0 || paused) {
                    t.stop();
                }

            }
        });

        mainMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent I) {
                paused = true;
                Frame.getFrame().setPanel(MainMenuPanel.getPanel());

            }
        });

        addMouseListener(this);
        addMouseMotionListener(this);

        t.start();
        timer.start();
        add(scoreLbl);
        add(timerLbl);
        add(timePlayedLbl);
        add(highscore);
        add(mainMenu);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, null);
        for (IGameObject game : Game.getInstance().getGameObjects()) {
            if (game != null) {
                game.getAnimation().doDrawing(g);
            }
        }

    }

    public void AnimateOne() {
        for (IGameObject game : Game.getInstance().getGameObjects()) {
            if (game != null) {
                game.getAnimation().update();
            }
        }

        repaint();
    }

    public static Panel getPanel() {
        return panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
//slicing game objects

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        for (IGameObject g : Game.getInstance().getGameObjects()) {

            Player.getInstance().setCommand(new SliceCommand(g, point));
            Player.getInstance().pressButton();

        }

        updateStatus();

    }

    @Override
    public void mouseMoved(MouseEvent e
    ) {
    }

    public static void updateStatus() {
        scoreLbl.setText(Player.playerState());
        if (Player.getInstance().getState().getLives() == 0) {
            Panel.endGame();
            Panel.stopTimer();
        }

    }
    public static void stopTimer()
    {  if(t!=null&&timer!=null)
    { t.stop();
        timer.stop();}
    }

    public static void updateHighscore() {
        highscore.setText(GameController.getInstance().getHighscore().toString());
    }

    public static void endGame() {
        GameController.getInstance().deleteSaved();
        Frame.getFrame().setPanel(new GameOverPanel());

    }

    public void setPanel(Panel panel) {
        Panel.panel = panel;

    }

}
