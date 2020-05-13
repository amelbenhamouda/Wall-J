package fr.umlv.projet.objectAndAStar;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.umlv.projet.ObjectCreate;

/**
 * Class for Level Loader.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class LevelLoader {
	private int posX;
	private int posY;
	private List<Path> list;// Levels list
	private Path dir;

	/**
	 * Constructor for LevelLoader.
	 */
	public LevelLoader() {
		this.posX = 0;
		this.posY = 0;
		this.list = new ArrayList<>();
		this.dir = Paths.get("Benhamouda_Hardy_Rubio_WallJ/levels");
	}

	/**
	 * The method reads the file from the character by character level, and adds the
	 * found objects a array list object with body. Moreover it checks that the read
	 * file is well built on the sides.
	 * 
	 * @param line
	 *            The file line.
	 * @param wallList
	 *            The array list of object with body.
	 * @param world
	 *            In which the object is located.
	 */
	private void processLine(String line, List<ObjectWithBody> wallList, World world, int numberLine) {
		Objects.requireNonNull(line);
		Objects.requireNonNull(wallList);
		Objects.requireNonNull(world);
		// Filter each words
		for (char c : line.toCharArray()) {
			switch (c) {
			case 'W': // Wall
				ObjectCreate tmpWall = new Wall(posX, posY, world);
				wallList.add((ObjectWithBody) tmpWall);
				break;
			case 'G': // Garbage
				ObjectCreate tmpGarbage = new Garbage(posX, posY, world);
				wallList.add((ObjectWithBody) tmpGarbage);
				break;
			case 'T': // Trash
				ObjectCreate tmpTrashCan = new TrashCan(posX, posY, world);
				wallList.add((ObjectWithBody) tmpTrashCan);
				break;
			case ' ': // Empty area
				// Check the sides of the level
				if(checkArea(posX, posY, line, numberLine)) {
					System.out.println("Erreur fichier");
					System.exit(1);
				}
				break;
			default:
				throw new IllegalThreadStateException("Unknown instance type '" + c + "'");
			}
			posX += 32;
		}
	}
	/**
	 * Check if the area game is valid
	 * @param posX Coordinate x of space
	 * @param posY Coordinate y of space 
	 * @param line who is reading
	 * @param numberLine number of line of file
	 * @return true if the area game is no valid and false if not
	 */
	private boolean checkArea(int posX, int posY, String line, int numberLine) {
		Objects.requireNonNull(line);
		// Top and left
				if (posX == 0 || posY == 0) {
					return true;
				}
				// Right
				if(posX == (line.length()-1)*32) {
					return true;
				}
				//down
				if(posY == (numberLine-1)*32) {
					return true;
				}
				return false;
	}

	/**
	 * Course the levels folder to insert the names of the file into a array list.
	 * 
	 * @throws IOException
	 *             Because of the stream.
	 * @see LevelLoader#loadLevelsList()
	 */
	public void loadLevelsList() throws IOException {
		// Look at existing files and put them in a array list.
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path path : stream) {
				list.add(path);
			}
		}
		// Sort the array list.
		Collections.sort(list);
	}

	/**
	 * Load the level in the order that the the previous level is finished.
	 * 
	 * @param wallList
	 *            The array list of object with body.
	 * @param world
	 *            In which the object is located.
	 * @param i
	 *            Index for the list of file names
	 * @throws IOException
	 *             Because of the reader.
	 * @see LevelLoader#loadLevel()
	 */
	public void loadLevel(List<ObjectWithBody> wallList, World world, int i) throws IOException {
		Objects.requireNonNull(wallList);
		Objects.requireNonNull(world);
		int numberLine=0;
		if (list.size() == 0) {
			throw new IllegalStateException("Il n'y a plus de niveaux à charger.");
		}
		if (i < list.size()) {
			try (BufferedReader reader = Files.newBufferedReader(list.get(i))) {
				while ((reader.readLine()) != null) {
					numberLine++;
				}
			}
			
			try (BufferedReader reader = Files.newBufferedReader(list.get(i))) {
				String line;
				posY = 0;
				while ((line = reader.readLine()) != null) {
					posX = 0;
					this.processLine(line, wallList, world, numberLine);
					posY += 32;
				}
			}
		} else {
			System.out.println("Il n'y a plus de niveaux vous avez fini le jeu.");
			System.exit(0);
		}
	}

}