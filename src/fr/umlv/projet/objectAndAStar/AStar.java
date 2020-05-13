package fr.umlv.projet.objectAndAStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

/**
 * Class for algorithm A star.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 */
public class AStar {

	/**
	 * Find neighbors from a position, always return a List even if no positions are
	 * available (empty List).
	 * 
	 * @param basePos
	 *            The position.
	 * @return A list of positions.
	 * 
	 */
	private static List<Point> findNeighbors(Point basePos) {
		Objects.requireNonNull(basePos);
		List<Point> positions = new ArrayList<>();
		positions.add(new Point(basePos.getX() - 1, basePos.getY())); // Left
		positions.add(new Point(basePos.getX() + 1, basePos.getY())); // Right
		positions.add(new Point(basePos.getX(), basePos.getY() - 1)); // Up
		positions.add(new Point(basePos.getX(), basePos.getY() + 1)); // Down
		positions.add(new Point(basePos.getX() - 1, basePos.getY() - 1)); // Up-Left
		positions.add(new Point(basePos.getX() + 1, basePos.getY() - 1)); // Up-Right
		positions.add(new Point(basePos.getX() + 1, basePos.getY() + 1)); // Down-Right
		positions.add(new Point(basePos.getX() - 1, basePos.getY() + 1)); // Down-Left
		return positions;
	}

	/**
	 * Check if a location is free for Wall-J.
	 * 
	 * @param obstacles
	 *            A list of obstacles.
	 * @param position
	 *            The position.
	 * @return true if a robot can move here, false otherwise.
	 */
	public static boolean isPositionFree(List<ObjectWithBody> obstacles, Point position) {
		Objects.requireNonNull(obstacles);
		Objects.requireNonNull(position);
		for (ObjectWithBody o : obstacles) {
			if (isInsideAPosition(o, position))
				return false;
		}
		return true;
	}

	/**
	 * Check if a ObjectWithBody is inside a position (for Wall-J).
	 * 
	 * @param o
	 *            An object with body.
	 * @param position
	 *            The position.
	 * @return true if a robot can move here, false otherwise.
	 */
	private static boolean isInsideAPosition(ObjectWithBody o, Point position) {
		Objects.requireNonNull(o);
		Objects.requireNonNull(position);
		if (o.getPosX() > position.getX() + ObjectNoBody.getSizeX()
				|| o.getPosX() + ObjectWithBody.getSizeX() < position.getX()
				|| o.getPosY() > position.getY() + ObjectNoBody.getSizeY()
				|| o.getPosY() + ObjectWithBody.getSizeY() < position.getY()) {
			return false;
		}
		return true;
	}

	/**
	 * Remove the Point with the highest priority.
	 * 
	 * @param map
	 *            Map<Point, Integer> describing the points with theirs priority
	 *            value.
	 * @return The Point with the highest priority.
	 */
	private static Point getLowest(Map<Point, Integer> map) {
		Objects.requireNonNull(map);
		Entry<Point, Integer> min = Collections.min(map.entrySet(), new Comparator<Entry<Point, Integer>>() {
			public int compare(Entry<Point, Integer> entry1, Entry<Point, Integer> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		map.remove(min.getKey());
		return min.getKey();
	}

	/**
	 * Calculate the heuristic for A*.
	 * 
	 * @param a
	 *            The first Point.
	 * @param b
	 *            The second Point.
	 * @return The heuristic value.
	 */
	private static int heuristic(Point a, Point b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		// Manhattan distance on a square grid
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}

	/**
	 * Search a path with A*.
	 * 
	 * @param locationX
	 *            Coordinate of destination.
	 * @param locationY
	 *            Coordinate of destination.
	 * @param walli
	 *            The robot.
	 * @param obstacles
	 *            List obstacle.
	 * @return A Path, it contains a List of positions.
	 */
	public static List<Point> trueAstar(int locationX, int locationY, Robot walli, List<ObjectWithBody> obstacles) {
		Objects.requireNonNull(walli);
		Objects.requireNonNull(obstacles);
		Point targetPos = new Point(locationX, locationY);
		Point startPos = new Point((int) walli.getPosX(), (int) walli.getPosY());
		// Early collision test
		if (!isPositionFree(obstacles, targetPos)) {
			System.out.println("Position is blocked");
			return new ArrayList<>();
		}
		// Position, priority
		Map<Point, Integer> priorityPositions = new HashMap<>();
		Map<Point, Point> cameFrom = new HashMap<>();
		Map<Point, Integer> costSoFar = new HashMap<>();
		priorityPositions.put(startPos, 0);
		cameFrom.put(startPos, null);
		costSoFar.put(startPos, 0);
		Point current = null;
		while (!priorityPositions.isEmpty()) {
			// Soft-lock protection
			if (priorityPositions.size() > 6000) {
				System.out.println("Position might be too far");
				break;
			}
			current = getLowest(priorityPositions);
			if (current.equals(targetPos)) { // Position found, the path can now be generated
				break;
			}
			List<Point> neighbors = AStar.findNeighbors(current);
			for (Point next : neighbors) {
				if (!isPositionFree(obstacles, next))
					continue;
				int newCost = costSoFar.get(current) + 1;
				if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
					costSoFar.put(next, newCost);
					priorityPositions.put(next, newCost + heuristic(targetPos, next));
					cameFrom.put(next, current);
				}
			}
		}
		if (!current.equals(targetPos)) { // No more path could be generated
			System.out.println("Suitable position not found");
			return new ArrayList<>();
		}
		// Apply found path from the end positions
		current = targetPos;
		List<Point> path = new ArrayList<>();
		while (!current.equals(startPos)) {
			path.add(current);
			current = cameFrom.get(current);
		}
		path.add(current);
		Collections.reverse(path);
		return path;
	}
}
