package animationgui;

import commands.LoadCommand;
import commands.ResetCommand;
import fruitninjagame.Game;
import fruitninjagame.GameController;
import fruitninjagame.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    static MainMenuPanel panel;
    ImageIcon background;
    static JLabel welcomingLbl;
    static JButton newGameButton;
    static JButton loadGameButton;
    static JButton viewHighScoreButton;
    static JButton resetButton;
    static JButton exitButton;

    public MainMenuPanel() {
        panel = this;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        background = new ImageIcon("backgroundOne.jpg");

        welcomingLbl = new JLabel("Welcome to Fruit Ninja, Slice 'em up!");
        welcomingLbl.setAlignmentX(CENTER_ALIGNMENT);
        newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(CENTER_ALIGNMENT);
        loadGameButton = new JButton("Load Game");
        loadGameButton.setAlignmentX(CENTER_ALIGNMENT);
        viewHighScoreButton = new JButton("View High score");
        viewHighScoreButton.setAlignmentX(CENTER_ALIGNMENT);
        resetButton = new JButton("Reset");
        resetButton.setAlignmentX(CENTER_ALIGNMENT);
        exitButton = new JButton("Exit");
        exitButton.setAlignmentX(CENTER_ALIGNMENT);

        welcomingLbl.setFont(new Font("Verdana", Font.BOLD, 45));
        newGameButton.setFont(new Font("Verdana", Font.BOLD, 30));
        loadGameButton.setFont(new Font("Verdana", Font.BOLD, 30));
        viewHighScoreButton.setFont(new Font("Verdana", Font.BOLD, 30));
        resetButton.setFont(new Font("Verdana", Font.BOLD, 30));
        exitButton.setFont(new Font("Verdana", Font.BOLD, 30));



        welcomingLbl.setBounds(400, 2, 700, 50);
        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(welcomingLbl);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));
        panel.add(newGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(loadGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(viewHighScoreButton);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(resetButton);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(exitButton);
        newGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent I) {

                Game.destroy();

                Frame.getFrame().setPanel(new IntermediatePanel());

            }
        });
        loadGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent I) {
                Panel.getPanel().setPaused(false);
                Player.getInstance().setCommand(new LoadCommand());
                Player.getInstance().pressButton();

            }
        });
        resetButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent I) {
                Player.getInstance().setCommand(new ResetCommand());
                Player.getInstance().pressButton();

            }
        });
        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent I) {
                System.exit(0);

            }
        });

        viewHighScoreButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent I) {
                JOptionPane.showMessageDialog(null, GameController.getInstance().getHighscore().toString());

            }

        });

        panel.setVisible(true);

    }

    public static MainMenuPanel getPanel() {
        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, null);
    }
}
