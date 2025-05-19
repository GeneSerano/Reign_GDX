package com.southcentralpositronics.reign_gdx.util;

import java.awt.*;
import java.awt.image.*;

import static com.southcentralpositronics.reign_gdx.util.MathUtils.clamp;

public class ImageUtil {

	public static Color getAverageColor(int[] pixels) {
		if (pixels == null || pixels.length == 0) {
			throw new IllegalArgumentException("Pixel array cannot be null or empty");
		}

		long sumRed   = 0;
		long sumGreen = 0;
		long sumBlue  = 0;

		for (int pixel : pixels) {
			Color color = new Color(pixel);
			sumRed += color.getRed();
			sumGreen += color.getGreen();
			sumBlue += color.getBlue();
		}

		int numPixels = pixels.length;
		int avgRed    = (int) (sumRed / numPixels);
		int avgGreen  = (int) (sumGreen / numPixels);
		int avgBlue   = (int) (sumBlue / numPixels);

		return new Color(avgRed, avgGreen, avgBlue);
	}

	public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
		var           imageFilter   = makeImageFilter(im, color);
		Image         filteredImage = Toolkit.getDefaultToolkit().createImage(imageFilter);
//		filteredImage = Toolkit.getDefaultToolkit().
		BufferedImage bufferedImage = new BufferedImage(filteredImage.getWidth(null), filteredImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bufferedImage.createGraphics();
		bGr.drawImage(filteredImage, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bufferedImage;
	}

	private static ImageProducer makeImageFilter(BufferedImage im, Color color) {
		ImageFilter filter = new RGBImageFilter() {

			// the color we are looking for... Alpha bits are set to opaque
			public final int markerRGB = color.getRGB() | 0xFF000000;

			public int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}
			}
		};

		return new FilteredImageSource(im.getSource(), filter);
	}

	public static BufferedImage changeBrightness(BufferedImage original, int amount) {
		BufferedImage result       = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
		byte[]        pixels       = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
		int[]         resultPixels = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();

		int offset = 0;
		for (int yy = 0; yy < original.getHeight(); yy++) {
			for (int xx = 0; xx < original.getWidth(); xx++) {
				int a = Byte.toUnsignedInt(pixels[offset++]);
				int b = Byte.toUnsignedInt(pixels[offset++]);
				int g = Byte.toUnsignedInt(pixels[offset++]);
				int r = Byte.toUnsignedInt(pixels[offset++]);

				r = clamp(r + amount, 0, 255);
				g = clamp(g + amount, 0, 255);
				b = clamp(b + amount, 0, 255);

				resultPixels[xx + yy * result.getWidth()] = a << 24 | r << 16 | g << 8 | b;
			}
		}
		return result;
	}
}
