package fr.umlv.projet.explosion;

import java.util.Objects;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

/**
 * Class for Explode Bomb.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Explode implements RayCastCallback {
	/**
	 * Called for each fixture found in the query and control how the ray cast
	 * proceeds.
	 * 
	 * @param fixture
	 *            The fixture hit by the ray.
	 * @param point
	 *            The point of initial intersection.
	 * @param normal
	 *            The normal vector at the point of intersection.
	 * @return -1 to filter, 0 to terminate, fraction to clip the ray for closest
	 *         hit, 1 to continue.
	 */
	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
		Objects.requireNonNull(point);
		Objects.requireNonNull(normal);
		Objects.requireNonNull(fixture);
		fixture.getBody().applyLinearImpulse(normal.mul(-2000), point);
		return fraction;
	}
}
