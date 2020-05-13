package fr.umlv.projet.objectAndAStar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import fr.umlv.projet.explosion.Explode;

/**
 * Class for object Bomb.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Bomb extends ObjectNoBody {
	private int counter;
	private final Vec2 vector;
	private final Ellipse2D.Float square;

	/**
	 * Constructor for bomb.
	 * 
	 * @param posX
	 *            Coordinated x of the bomb.
	 * @param posY
	 *            Coordinated y of the bomb.
	 */

	public Bomb(int posX, int posY) {
		super(posX, posY);
		if (posX < 0 || posY < 0) {
			throw new IllegalArgumentException("coordinate's robot wrong must be positive: " + posX + " x " + posY + " y ");
		}
		this.counter = 0;
		this.square = new Ellipse2D.Float(posX, posY, getSizeX(), getSizeY());
		this.vector = new Vec2(posX, posY);
	}

	/**
	 * Draw a bomb.
	 * 
	 * @param graphics
	 *            Lambda of context renderFrame.
	 * 
	 */
	public void draw(Graphics2D graphics) {
		Objects.requireNonNull(graphics);
		// show a new ellipse at the position of the pointer
		graphics.setColor(Color.BLUE);
		graphics.fill(square);
		graphics.setColor(Color.RED);
		String str = String.valueOf(getCounter());
		if (getCounter() < 0) {
			graphics.drawString("0", getPosX(), getPosY());
		} else {
			graphics.drawString(str, getPosX(), getPosY());
		}
	}

	/**
	 * Getter for counter's bomb.
	 * 
	 * @return current counter.
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Setter for counter's bomb.
	 * 
	 * @param counter
	 *            To set.
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * Simulate an explosion of a bomb using ray cast.
	 * 
	 * @param world
	 *            In which the bomb is located.
	 * @see @link raycastCallback#raycast
	 */
	public void explode(World world) {
		Objects.requireNonNull(world);
		int nbRay = 360;
		float radius = 70f;
		Vec2 center = vector;
		for (double i = 0; i < 2 * Math.PI; i += (2 * Math.PI) / nbRay) {
			Vec2 rayEnd = new Vec2((float) (center.x + Math.cos(i) * radius),
					(float) (center.y + Math.sin(i) * radius));
			Explode callback = new Explode();
			world.raycast(callback, center, rayEnd);
		}
	}

	/**
	 * Draw an explosion of a bomb.
	 * 
	 * @param graphics
	 *            Lambda of context renderFrame.
	 */
	public void drawExplode(Graphics2D graphics) {
		Objects.requireNonNull(graphics);
		int nbRay = 20;
		float radius = 50f;
		Vec2 center = vector;
		for (double i = 0; i < 2 * Math.PI; i += (2 * Math.PI) / nbRay) {
			graphics.setColor(Color.RED);
			graphics.fill(new Ellipse2D.Float((float) (center.x + Math.cos(i) * radius),
					(float) (center.y + Math.sin(i) * radius), 10, 10));
		}
	}
}