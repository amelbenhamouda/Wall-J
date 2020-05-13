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
 * Class for object Garbage.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Garbage extends ObjectWithBody {

	/**
	 * Constructor for Garbage.
	 * 
	 * @param posX
	 *            Coordinated x of the garbage.
	 * @param posY
	 *            Coordinated y of the garbage.
	 * @param world
	 *            In which the garbage is located.
	 */
	public Garbage(int posX, int posY, World world) {
		super(posX, posY, world);
	}

	/**
	 * Creates a body for the garbage.
	 * 
	 * @param posX
	 *            Coordinated x of the garbage.
	 * @param posY
	 *            Coordinated y of the garbage.
	 * @param world
	 *            In which the garbage is located.
	 * 
	 * @return body The body of the garbage.
	 */
	@Override
	public Body create(int posX, int posY, World world) {
		Objects.requireNonNull(world);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(posX, posY);
		bodyDef.allowSleep = false;
		bodyDef.linearDamping = 0.2f;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sizeX / 2, sizeY / 2);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 0.5f;
		fixture.restitution = 1.0f;
		fixture.userData = 'G';

		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		return body;
	}

	/**
	 * Draw the garbage.
	 * 
	 * @param graphic
	 *            Lambda of the context renderFrame.
	 */
	public void draw(Graphics2D graphic) {
		Objects.requireNonNull(graphic);
		translate(getBody().getPosition().x, getBody().getPosition().y);
		graphic.setColor(Color.GREEN);
		graphic.fill(getSquare());
	}
}
