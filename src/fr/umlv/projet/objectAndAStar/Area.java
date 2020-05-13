package fr.umlv.projet.objectAndAStar;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import fr.umlv.projet.collision.Collision;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

/**
 * Class for execution.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Area {
	private final World world;
	private final List<Bomb> tabBomb;
	private List<Point> path;
	private int nubG;
	private int time= 0;
	private int phase = 1;

	/**
	 * Constructor for Area.
	 */
	public Area() {
		// No gravity because of top-down view
		tabBomb = new ArrayList<>();
		world = new World(new Vec2(0.0f, 0.0f));
		path = new ArrayList<>();
		nubG = 0;
	}

	/**
	 * This method draws the all game.
	 * 
	 * @param context
	 *            So that we can use renderFrame.
	 * @param walli
	 *            The robot.
	 * @param tabBomb
	 *            The list of bombs.
	 * @param path
	 *            Where should the robot go.
	 * @param rect
	 *            The Float class defines a rectangle specified in float
	 *            coordinates.
	 * @param wallList
	 *            The array list of object with body.
	 * @param phase
	 *            Lets know if we are in an explosion or the robot is moving.
	 * @param time
	 *            The time that the party.
	 */
	private void drawAll(ApplicationContext context, Robot walli, List<Bomb> tabBomb, List<Point> path, Rectangle2D.Float rect, List<ObjectWithBody> wallList) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(walli);
		Objects.requireNonNull(tabBomb);
		Objects.requireNonNull(path);
		Objects.requireNonNull(rect);
		Objects.requireNonNull(wallList);
		context.renderFrame(graphics -> {
			
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(rect);
			
			for (ObjectWithBody w : wallList) {
				w.draw(graphics);
			}
			if (phase != 2) {
				for (Bomb b : tabBomb) {
					b.draw(graphics);
				}
				if (!path.isEmpty()) {
					walli.move(path.get(0).getX(), path.get(0).getY());
					walli.draw(graphics);
					path.remove(0);
				}
				if (walli.getPosX() != 0 && walli.getPosY() != 0) {
					walli.draw(graphics);
				}
			}else {
				walli.move(0, 0);
				for (Bomb bomb : tabBomb) {
					bomb.draw(graphics);
					if (bomb.getCounter() <= 0) {
						bomb.drawExplode(graphics);
					}

				}
				graphics.setColor(Color.RED);
				String str = String.valueOf(time / 60);
				graphics.drawString("Timer : "+str, 32, 15);
			}
		});
	}

	/**
	 * This method Goes to the next level.
	 * 
	 * @param numLevel
	 *            The number of the level to download.
	 * @param loader
	 *            The parameter that uses levelLoader to download the level.
	 * @param wallList
	 *            The array list of object with body.
	 */
	private void newlevel(int numLevel, LevelLoader loader, List<ObjectWithBody> wallList) {
		Objects.requireNonNull(loader);
		Objects.requireNonNull(wallList);
		phase = 1;
		time = 0;
		for (ObjectWithBody w : wallList) {
			world.destroyBody(w.body);
		}
		// Empty the list of object with body
		wallList.clear();
		// Empty the list of bombs
		tabBomb.clear();
		// Goes to the next level
		try {
			loader.loadLevel(wallList, world, numLevel);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Erreur sur le fichier.");
		}
		for (ObjectWithBody w : wallList) {
			if ((char) w.getBody().m_fixtureList.getUserData() == 'G') {
				nubG++;
			}
		}
	}
	/**
	 * Destroy the garbage who have a contact with trash can
	 * @param wallList list with the garbage
	 */
	private void destroyall(List<ObjectWithBody> wallList) {
		Iterator<ObjectWithBody> it = wallList.iterator();
		while (it.hasNext()) {
			ObjectWithBody w = it.next();
			if ((char) w.getBody().m_fixtureList.getUserData() == 'E') {
				world.destroyBody(w.body);
				it.remove();
				nubG--;
			}
			if (nubG == 0) {
				break;

			}
		}
	}
	
	/**
	 * Executable.
	 * 
	 * @param args
	 *            The String class represents character strings.
	 * @throws IOException
	 *             Because of the loadLevel.
	 * @see LevelLoader#loadLevelsList()
	 */
	public static void main(String[] args) throws IOException {
		List<ObjectWithBody> wallList = new ArrayList<>();
		LevelLoader loader = new LevelLoader();
		loader.loadLevelsList();
		// Print all levels files dir
		Application.run(Color.LIGHT_GRAY, context -> {
			// get the size of the screen
			ScreenInfo screenInfo = context.getScreenInfo();
			float width = screenInfo.getWidth();
			float height = screenInfo.getHeight();
			System.out.println("size of the screen (" + width + " x " + height + ")");
			Rectangle2D.Float rect = new Rectangle2D.Float(0, 0, width, height);
			

			Area area = new Area();
			Robot walli = new Robot(0, 0);
			Bomb bomb;
			int numLevel = 0;
			try {
				loader.loadLevel(wallList, area.world, numLevel);
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalStateException("Erreur sur le fichier.");
			}
			float timeStep = 1.0f / 100.0f;
			int velocityIterations = 10;
			int positionIterations = 2;
			int counterexplode = 0;
			for (ObjectWithBody w : wallList) {
				if (w instanceof Garbage) {
					area.nubG++;
				}
			}
			for (;;) {
				Collision listener = new Collision();
				area.drawAll(context, walli, area.tabBomb, area.path, rect, wallList);
				area.world.setContactListener(listener);
				Event event = context.pollOrWaitEvent(20);
				counterexplode += 20;
				area.world.step(timeStep, velocityIterations, positionIterations);
				area.world.clearForces();
				if (event == null) {
					// no event
					if (area.phase == 2) {
						area.time += 20;
						area.destroyall(wallList);
						// Goes to the next level when there is no garbage.
						if (area.nubG == 0) {
							numLevel = numLevel + 1;
							area.newlevel(numLevel, loader, wallList);
							continue;
						}
						// Repeat the level when there is no time.
						else if (area.time / 60 >= 500) {
							area.nubG = 0;
							area.newlevel(numLevel, loader, wallList);
							continue;
						}
						for (Bomb b : area.tabBomb) {
							if (b.getCounter() == 0) {
								b.explode(area.world);
								b.setCounter(-1);
							} else if (b.getCounter() > 0) {
								if (counterexplode % 100 == 0) {
									b.setCounter(b.getCounter() - 1);
								}
							}
						}
					}
					continue;
				}
				Point2D.Float location = event.getLocation();
				Action action = event.getAction();
				if (action == Action.KEY_PRESSED) {
					// Place the bombs
					if (event.getKey() == KeyboardKey.SPACE && area.phase == 1) {
						if (area.tabBomb.isEmpty()) {
							area.tabBomb.add(new Bomb(walli.getPosX(), walli.getPosY()));
						} else {
							int size = area.tabBomb.size() - 1;
							bomb = area.tabBomb.get(size);
							if (walli.getPosX() == bomb.getPosX() && walli.getPosY() == bomb.getPosY() && size != 3) {
								int counter = bomb.getCounter() + 1;
								area.tabBomb.get(size).setCounter(counter);
							} else if (area.tabBomb.size() != 3) {
								area.tabBomb.add(new Bomb(walli.getPosX(), walli.getPosY()));
							}
						}
						continue;
					}
					// Disappear the robot and detonates the bombs according to their incrementing
					else if (event.getKey() == KeyboardKey.B && area.phase == 1) {
						if (area.tabBomb.size() == 3) {
							area.phase = 2;
						}
					}
					// When the player presses the q button
					else if (event.getKey() == KeyboardKey.Q){
						System.out.println("abort abort !");
						context.exit(0);
						return;
					}
				} else if (action == Action.POINTER_UP) {
					// To draw the robot to the player's clique
					if (walli.getPosX() == 0.0 || walli.getPosY() == 0.0) {
						if (AStar.isPositionFree(wallList, new Point((int) location.x, (int) location.y)))
							walli.move((int) location.x, (int) location.y);
					}
					area.path = AStar.trueAstar((int) location.x - ObjectNoBody.getSizeX() / 2,
							(int) location.y - ObjectNoBody.getSizeX() / 2, walli, wallList);
				}
			}
		});
	}

}