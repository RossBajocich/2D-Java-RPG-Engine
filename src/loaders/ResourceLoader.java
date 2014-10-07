package loaders;

import graphics.Animation;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import utilities.Console;
import utilities.Console.in;
import utilities.Images;

public class ResourceLoader {

	static ResourceLoader r = new ResourceLoader();

	public static BufferedImage getImage(String fileName) {
		BufferedImage i = null;
		String path = "../images/";
		File f = null;
		try {
			f = new File(ResourceLoader.class.getResource(path + fileName)
					.getPath());
		} catch (NullPointerException e) {
			Console.log("file + " + fileName + " is not there", in.ERROR);
			e.printStackTrace();
		}

		if (f != null && f.exists() && !f.isDirectory()) {
			try {
				// TODO: Don't use bin directory
				i = ImageIO.read(r.getClass().getClassLoader()
						.getResource("images/" + fileName));
			} catch (Exception e) {
				Console.log("image= " + fileName + " is not located here",
						Console.in.ERROR);
				e.printStackTrace();
			}
		}
		return i;
	}

	public static Animation getAnimationFromSheet(String fileName, int xloc,
			int yloc, int width, int height, String id, int count, int offset, double frame_duration, boolean loop) {
		Animation a = new Animation(frame_duration, loop);
		BufferedImage b = getImage(fileName);

		int i = 0;
		for (int x = 0; x < count; x++) {
			Images.set(b.getSubimage(xloc + (x * width) + offset, yloc, width, height),
					id + "_" + i);
			a.addImage(id + "_" + i);
			i++;
		}

		return a;
	}

	public static BufferedImage getImageFromSheet(String fileName, int x,
			int y, int width, int height, String id) {
		BufferedImage b = getImage(fileName);

		if (x < 0 || y < 0 || x + width > b.getWidth()
				|| y + height > b.getHeight()) {
			Console.log(
					"The requested Subimage is out of bounds of the source image!",
					Console.in.ERROR);
			return null;
		}

		Images.set(b.getSubimage(x, y, width, height), fileName + "_" + id);
		return Images.get(fileName + "_" + id);
	}

	public static File getFile(String fileName) {
		String path = "/Resources/files/";

		//ClassLoader cl = ResourceLoader.class.getClassLoader();
		// System.out.println(System.getProperty("java.class.path"));

		File location = new File(ResourceLoader.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath());
		String p = location.getParent();

		File r = new File(p + path + fileName);

		return r;
	}
}
