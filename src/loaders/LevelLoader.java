package loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import utilities.Console;
import utilities.Console.in;
import world.Level;
import elements.Member;

public class LevelLoader {

	public static Level getLevel(String id) {
		Level level = new Level();
		loadObjects(id, level);
		return level;
	}

	private static void loadObjects(String name, Level level) {
		File file = ResourceLoader.getFile(name + ".csv");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int n = 0;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(cvsSplitBy);
				int id = 0;
				try {
					Member e;
					ElementData ed = new ElementData();
					int x, y;
					if (data[0].startsWith("FORMAT")) {
						continue; // so that n isn't incremented
					}
					if (data[0].startsWith("HEAD")) {
						// header line
						level.setWidth(Integer.parseInt(data[2]));
						level.setHeight(Integer.parseInt(data[3]));
						level.setBackground(data[4]);
						XmlParser.parseXmlFile(name + ".xml");
						Console.log("there are " + TypeMaker.players.size()
								+ " players loaded now", in.INFO);
						continue; // so that n isn't incremented
					} else {
						id = Integer.parseInt(data[1]);
						x = Integer.parseInt(data[2]);
						y = Integer.parseInt(data[3]);
						ed.level = level;
						ed.name = data[4];
					}
					if (data[0].startsWith("Prop")) {
						// prop things
					} else if (data[0].startsWith("Decoration")) {
						// decoration things
					} else if ((data[0].startsWith("Player"))) {
						ed.mainPlayer = true;
					} else if (data[0].startsWith("NPC")) {
						// npc stuff
						ed.mainPlayer = false;
					} else if (data[0].startsWith("Item")) {
						// item things
					}
					ed.elementType = data[0].trim();
					if (ed.elementType.equalsIgnoreCase("NPC")) {
						ed.elementType = "Player";
					}
					ed.level = level;
					e = TypeMaker.createElement(id, x, y, ed);

					if (e == null) {
						Console.log("Uknown or empty line...", Console.in.ERROR);
						continue; // break so n is not incremented on error
					}

					level.addMember(e);
					Console.log("Member " + e.getName() + " was added", in.INFO);
				} catch (Exception e) {
					e.printStackTrace();
					Console.log("An error occured " + e.getMessage(), in.ERROR);
				}
				n++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Console.log("Done loading file: " + file.getName() + " with " + n
				+ " entries", Console.in.INFO);
	}
}
