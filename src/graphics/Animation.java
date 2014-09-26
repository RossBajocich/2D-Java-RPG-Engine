package graphics;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import utilities.Console;
import utilities.Console.in;
import utilities.Images;
import utilities.Images.EXT;

public class Animation {
	LinkedList<String> images = new LinkedList<String>();
	int index = 0;
	double last_frame_t, duration;
	boolean loop;

	public Animation(double frame_duration, boolean loop) {
		this.duration = frame_duration;
		this.loop = loop;
	}

	public void setImages(LinkedList<String> input) {
		images = input;
	}

	public void addImage(String b) {
		images.add(b);
		Images.load(b, EXT.NONE);
	}

	public BufferedImage getNext() {
		if (images.size() == 1) {
			return Images.get(images.getFirst());
		}
		if (System.nanoTime() / 1000 > duration + last_frame_t) {
			index++;
			last_frame_t = System.nanoTime() / 1000;
		}
		if (index >= images.size()) {
			if(loop){
				index = 1;
			}else{
				index = images.size()-1;
			}
		}
		return Images.get(images.get(index));
	}

	public BufferedImage getPrevious() {
		index--;
		if (index < 0) {
			index = images.size();
		}
		return Images.get(images.get(index));
	}

	public void setIndex(int i) {
		if (i > 0 && i <= images.size()) {
			index = i;
			return;
		}
		Console.log("The input index " + i
				+ " does not fit inside the images with size " + images.size(),
				in.ERROR);

	}
}
