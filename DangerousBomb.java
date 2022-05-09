/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombs;


import animationgui.Panel;
import animationgui.Animation;
import fruitninjagame.GameController;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author MIX
 */
public class DangerousBomb extends Bomb{



    public DangerousBomb() {
        x = rand.nextInt(300) + 100;
        y = 600;
        max = rand.nextInt(300) + 50;
        initialV = GameController.getInstance().getStrategy().getObjectsVelocity();
        fallV = GameController.getInstance().getStrategy().getObjectsVelocity();
        slice = false;
        top = false;

        a = new Animation(this, Panel.getPanel());
        image = resize(new ImageIcon("Bomb.png"));
        slicedImage = resize(new ImageIcon("Explosion.png"));

    }


    public int getObjectType() {
        return 1;
    }



    private ImageIcon resize(ImageIcon imageicon) {
        Image image = imageicon.getImage();
        Image image2 = image.getScaledInstance(80, 80, image.SCALE_SMOOTH);
        imageicon = new ImageIcon(image2);
        return imageicon;
    }

}
