package fr.umlv.projet;

import java.awt.Graphics2D;

/**
 * Class for interface ObjectDraw.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public interface ObjectDraw {
	/**
	 * Draw the object.
	 * 
	 * @param graphics
	 *            Lambda of the context renderFrame.
	 */
	public abstract void draw(Graphics2D graphics);

}
