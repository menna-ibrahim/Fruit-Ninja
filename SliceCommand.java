/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import fruitninjagame.GameController;
import fruitninjagame.IGameObject;
import java.awt.Point;

/**
 *
 * @author MIX
 */
public class SliceCommand implements ICommand {

    private IGameObject gameObject;
    private Point p;

    public SliceCommand(IGameObject gameObject, Point p) {
        this.gameObject = gameObject;
        this.p = p;
    }

    @Override
    public void execute() {
        GameController.getInstance().sliceObjects(this.gameObject, p);
    }

}
