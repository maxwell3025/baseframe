package baseframe;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Heat extends baseframe {
	int radius = 10;
	double speed = 3;
	int fleb = resolution;
	double[][] heat = new double[resolution][resolution];
	double[][] buffer = new double[resolution][resolution];

	public Heat() {
	}

	public void update() {
		if (mouseheld) {
		}
		display=keyspressed[KeyEvent.VK_CONTROL];
		showframe=keyspressed[KeyEvent.VK_CONTROL];
		for (int x = 0; x < fleb - 1; x++) {
			heat[x][1] = (double)x/100;
		}
		time++;
		if (mouselevel < 1) {
			mouselevel = 1;
		}
		if (mouselevel > 999) {
			mouselevel = 999;
		}
		resolution = mouselevel;
		for (double x = 0; x < fleb - 1; x++) {
			for (double y = 0; y < fleb - 1; y++) {
				double sidesfree = 0;
				if (x < fleb) {
					buffer[(int) x + 1][(int) y] += heat[(int) x][(int) y] / speed;
					sidesfree++;
				}
				if (y < fleb) {
					buffer[(int) x][(int) y + 1] += heat[(int) x][(int) y] / speed;
					sidesfree++;
				}
				if (x > 0) {
					buffer[(int) x - 1][(int) y] += heat[(int) x][(int) y] / speed;
					sidesfree++;
				}
				if (y > 0) {
					buffer[(int) x][(int) y - 1] += heat[(int) x][(int) y] / speed;
					sidesfree++;
				}
				buffer[(int) x][(int) y] -= (heat[(int) x][(int) y] / speed) * sidesfree;
			}
		}
		heat = buffer;
		for (double x = 0; x < resolution - 1; x++) {
			for (double y = 0; y < resolution - 1; y++) {
				double mx = mousex;
				double my = mousey;
				double realx = x * pixelwidth;
				double realy = y * pixelheight;
				if (mouseheld) {
					if (distance(mx / pixelwidth - x, my / pixelheight - y) < radius)
						heat[(int) x][(int) y] += (radius - distance(mx / pixelwidth - x, my / pixelheight - y));
				}
				double colorgradient = heat[(int) x][(int) y];

				palette[(int) x][(int) y] = inputcolor((int) (128 + colorgradient * 128),
						(int) (128 + colorgradient * 128 * colorgradient),
						(int) (128 - colorgradient + colorgradient * colorgradient * 4));
			}
		}
		if (Math.abs(heat[(int) (mousex / pixelwidth)][(int) (mousey / pixelheight)]) > 0.0009765625) {
			display1 = "" + ((heat[(int) (mousex / pixelwidth)][(int) (mousey / pixelheight)] * 50) + 80);
		} else {
			display1 = "80";
		}
	}

	public static void main(String[] args) {
		Heat b = new Heat();
		new Thread(b).start();
	}

}
