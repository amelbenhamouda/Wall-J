package fr.umlv.projet.objectAndAStar;

import fr.umlv.projet.ObjectDraw;

/**
 * Abstract class for ObjectNoBody. Allows to represent objects without a body.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
abstract class ObjectNoBody implements ObjectDraw {
	private int posX;
	private int posY;
	static final int sizeX = 32;
	static final int sizeY = 32;

	/**
	 * Constructor for ObjectNoBody.
	 * 
	 * @param posX
	 *            Coordinated x of the ObjectNoBody.
	 * @param posY
	 *            Coordinated y of the ObjectNoBody.
	 * @param world
	 *            In which the ObjectNoBody is located.
	 */
	public ObjectNoBody(int posX, int posY) {
		if (posX < 0 || posY < 0) {
			throw new IllegalArgumentException("coordinate's  wrong must be positive : " + posX + " x " + posY + " y ");
		}
		this.posX = posX;
		this.posY = posY;
	}

	/**
	 * Getter for the coordinate x of ObjectNoBody.
	 * 
	 * @return current coordinate x of ObjectNoBody.
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Setter for the coordinate x of ObjectNoBody.
	 * 
	 * @param posX
	 *            To set.
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * Getter for the coordinate y of ObjectNoBody.
	 * 
	 * @return current coordinate y of ObjectNoBody.
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Setter for the coordinate y of ObjectNoBody.
	 * 
	 * @param posY
	 *            To set.
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * Getter for the size x of ObjectNoBody.
	 * 
	 * @return current size x of ObjectNoBody.
	 */
	public static int getSizeX() {
		return sizeX;
	}

	/**
	 * Getter for the size y of ObjectNoBody.
	 * 
	 * @return current size y of ObjectNoBody.
	 */
	public static int getSizeY() {
		return sizeY;
	}

}
