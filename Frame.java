/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationgui;

import javax.swing.*;

/**
 *
 * @author MIX
 */
public class Frame extends JFrame{
    static Frame frame;
    JPanel panel;
  
    public Frame()
    {   frame=this;
        panel=new MainMenuPanel();
        setSize(1300,700);
        setLayout(null);
        setTitle("FruitNinja");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon i=new ImageIcon("icon.png");
        setIconImage(i.getImage());
        add(panel);
        setContentPane(panel);
        setVisible(true);
        
    }


    public void setPanel(JPanel panel) {
        this.panel = panel;
        setContentPane(panel);
    }
    
    

  public static Frame getFrame()
  {
      return frame;   
  }
  
    
}
