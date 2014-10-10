package utilities;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import utilities.Console.in;
import loaders.ResourceLoader;

public class Images {
	public enum EXT {
		PNG(".png"), JPG(".png"), GIF(".png"), JPEG(".png"), NONE("");
		String t;

		EXT(final String t) {
			this.t = t;
		}

		@Override
		public String toString() {
			return t;
		}
	}

	static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	public static BufferedImage get(String id) {
		if (images.containsKey(id)) {
			return images.get(id);
		}
		Console.log("Images.get(" + id + ") returning null!", in.INFO);
		return null;
	}

	public static void load(String id, EXT t) {
		if (!images.containsKey(id)) {
			images.put(id, ResourceLoader.getImage(id + t.toString()));
		}
	}

	public static void add(String id) {
		if (!images.containsKey(id)) {
			images.put(id, ResourceLoader.getImage(id));
		}
	}

	public static void set(BufferedImage b, String id) {
		if (!images.containsValue(b)) {
			images.put(id, b);
		}
	}
}
