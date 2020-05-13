package fr.umlv.projet.objectAndAStar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * Class for object Robot.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Robot extends ObjectNoBody{
	Rectangle2D.Float ellipse;
	Rectangle2D.Float head;
	
	/**
	 * Constructor for robot.
	 * 
	 * @param posX
	 *            Coordinated x of the robot.
	 * @param posY
	 *            Coordinated y of the robot.
	 */
	public Robot(int posX, int posY) {
		super(posX, posY);
		this.ellipse = new Rectangle2D.Float(posX, posY, 0, 0);
		this.head = new Rectangle2D.Float(posX, posY, 0, 0);
	}
	
	/**
	 * Change the coordinates of the robot so that it moves.
	 * 
	 * @param posX
	 *            Coordinated x of the robot.
	 * @param posY
	 *            Coordinated y of the robot.
	 */
	public void move(int posX, int posY) {
		super.setPosX(posX);
		super.setPosY(posY);
	}
	
	/**
	 * Draw the robot.
	 * 
	 * @param graphics
	 *            Lambda of the context renderFrame.
	 */
    @Override
    public void draw(Graphics2D graphics) {
    	Objects.requireNonNull(graphics);
        // show a new ellipse at the position of the pointer
        graphics.setColor(Color.BLUE);
        ellipse = new Rectangle2D.Float(getPosX(), getPosY(), sizeX, sizeY);
        head = new Rectangle2D.Float(getPosX() + sizeX/2, getPosY(), 10, 10);
        graphics.fill(ellipse);
        graphics.setColor(Color.GREEN);
        graphics.fill(head);
      
    }
    
}

