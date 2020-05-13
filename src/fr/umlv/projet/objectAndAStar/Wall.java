package fr.umlv.projet.objectAndAStar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Class for object Wall.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Wall extends ObjectWithBody {

	/**
	 * Constructor for Wall.
	 * 
	 * @param posX
	 *            Coordinated x of the wall.
	 * @param posY
	 *            Coordinated y of the wall.
	 * @param world
	 *            In which the wall is located.
	 */
	public Wall(int posX, int posY, World world) {
		super(posX, posY, world);
	}

	/**
	 * Creates a body for the wall.
	 * 
	 * @param posX
	 *            Coordinated x of the wall.
	 * @param posY
	 *            Coordinated y of the wall.
	 * @param world
	 *            In which the wall is located.
	 * 
	 * @return body The body of the wall.
	 */
	@Override
	public Body create(int posX, int posY, World world) {
		Objects.requireNonNull(world);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(posX, posY);
		bodyDef.allowSleep = false;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sizeX / 2, sizeY / 2);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 5;
		fixture.friction = 0.3f;
		fixture.restitution = 1.1f;
		fixture.userData = 'W';

		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);

		return body;
	}

	/**
	 * Draw the wall.
	 * 
	 * @param graphic
	 *            Lambda of the context renderFrame.
	 */
	@Override
	public void draw(Graphics2D graphic) {
		Objects.requireNonNull(graphic);
		graphic.setColor(Color.BLACK);
		graphic.fill(getSquare());
	}

}
