package fr.umlv.projet.objectAndAStar;

import java.awt.geom.Rectangle2D;
import java.util.Objects;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.umlv.projet.ObjectCreate;
import fr.umlv.projet.ObjectDraw;

/**
 * Abstract class for ObjectWithBody. Allows to represent objects with a body.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
abstract class ObjectWithBody implements ObjectCreate, ObjectDraw {
	private int posX;
	private int posY;
	static final int sizeX = 32;
	static final int sizeY = 32;
	private final Rectangle2D.Float square;
	final Body body;

	/**
	 * Constructor for ObjectWithBody.
	 * 
	 * @param posX
	 *            Coordinated x of the objectWithBody.
	 * @param posY
	 *            Coordinated y of the objectWithBody.
	 * @param world
	 *            In which the objectWithBody is located.
	 */
	public ObjectWithBody(int posX, int posY, World world) {
		Objects.requireNonNull(world);
		if (posX < 0 || posY < 0) {
			throw new IllegalArgumentException("coordinate's  wrong must be positive : " + posX + " x " + posY + " y ");
		}
		this.posX = posX;
		this.posY = posY;
		this.square = new Rectangle2D.Float(posX, posY, sizeX, sizeY);
		this.body = create(posX, posY, world);
	}

	/**
	 * Getter for field square.
	 * 
	 * @return current square.
	 */
	public Rectangle2D.Float getSquare() {
		return square;
	}

	/**
	 * Move the objectWithBody according to the new coordinate.
	 * 
	 * @param x
	 *            Coordinated x of the new destination.
	 * @param y
	 *            Coordinated y of the new destination.
	 */
	public void translate(float x, float y) {
		this.square.x = x;
		this.square.y = y;
	}

	/**
	 * Getter for the body's ObjectWithBody.
	 * 
	 * @return current body of the objectWithBody.
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * Getter for the coordinate x of objectWithBody.
	 * 
	 * @return current coordinate x of objectWithBody.
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Setter for the coordinate x of objectWithBody.
	 * 
	 * @param posX
	 *            To set.
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * Getter for the coordinate y of objectWithBody.
	 * 
	 * @return current coordinate y of objectWithBody.
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Setter for the coordinate y of objectWithBody.
	 * 
	 * @param posY
	 *            To set.
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * Getter for the size x of objectWithBody.
	 * 
	 * @return current size x of objectWithBody.
	 */
	public static int getSizeX() {
		return sizeX;
	}

	/**
	 * Getter for the size y of objectWithBody.
	 * 
	 * @return current size y of objectWithBody.
	 */
	public static int getSizeY() {
		return sizeY;
	}

}