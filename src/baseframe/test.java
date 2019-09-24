package baseframe;

import java.awt.Color;

public class test extends baseframe {
	public test() {

	}

	@Override
	public void update() {
		for (double x = 0; x < resolution; x++) {
			for (double y = 0; y < resolution; y++) {
				double mx = mousex;
				double my = mousey;
				double realx = x * pixelwidth;
				double realy = y * pixelheight;
				int colorgradient = 0;
				colorgradient+=127*Math.sin(distance(realx-mx, realy-my)/50+time)+128;
				palette[(int) x][(int) y] = new Color(colorgradient, colorgradient, 255);
			}
		}
	}


	public static void main(String[] args) {
		test b = new test();
		new Thread(b).start();
	}
}
