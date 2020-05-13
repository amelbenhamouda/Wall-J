package fr.umlv.projet;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

/**
 * Class for interface ObjectCreate.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public interface ObjectCreate {
	/**
	 * This method creates the body of an object.
	 *
	 * @param posX
	 *            Coordinated x of the object.
	 * @param posY
	 *            Coordinated y of the object.
	 * @param world
	 *            In which the object is located.
	 * 
	 * @return body The body of the object.
	 */
	public abstract Body create(int posX, int posY, World world);

}
