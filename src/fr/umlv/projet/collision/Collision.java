package fr.umlv.projet.collision;

import java.util.Objects;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

/**
 * Class for Collision.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Collision implements ContactListener {

	/**
	 * Detect the begin of a contact between two fixture.
	 * 
	 * @param contact
	 *            The contact detected.
	 */
	@Override
	public void beginContact(Contact contact) {
		Objects.requireNonNull(contact);
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		if ((char) a.getUserData() == 'T' && (char) b.getUserData() == 'G') {
			b.setUserData('E');
		} else if ((char) a.getUserData() == 'G' && (char) b.getUserData() == 'T') {
			a.setUserData('E');
		}
	}

	/**
	 * Detect the end of a contact between two fixture. 
	 * Function needed but not used.
	 * 
	 */
	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Function needed but not used.
	 */
	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
	}

	/**
	 * Function needed but not used.
	 */
	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
	}
}
