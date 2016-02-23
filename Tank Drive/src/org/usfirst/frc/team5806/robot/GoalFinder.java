package org.usfirst.frc.team5806.robot;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import edu.wpi.first.wpilibj.vision.USBCamera;

//get image data from camera via getImage(ByteBuffer buff)

public class GoalFinder {
	private float[] hsvBuffer = new float[3];
	
	USBCamera camera;
	public GoalFinder(USBCamera camera) {
		this.camera = camera;
	}
	
	private ByteBuffer getImage() {
		ByteBuffer buff = null;
		camera.getImageData(buff);
		return buff;
	}
	
	private ByteBuffer bufferedImageToByteBuffer(BufferedImage buff) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(buff, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return ByteBuffer.wrap(imageInByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private BufferedImage byteBufferToBufferedImage(ByteBuffer buff) {
		byte[] data = buff.array();
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		BufferedImage image = null;
		try {
			ImageIO.read(in);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	private boolean pixelMeetsHSVFilter(int red, int green, int blue) {
		Color.RGBtoHSB(red, green, blue, hsvBuffer);
		return hsvBuffer[0] > 0.5 && hsvBuffer[0] < 0.9 
				&& hsvBuffer[1] > 0.5 && hsvBuffer[1] < 0.9 
				&& hsvBuffer[2] > 0.5 && hsvBuffer[2] < 0.9;
	}
	
	private BufferedImage hsvThreshold(BufferedImage image) {
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				
				int colorValue;
				if(pixelMeetsHSVFilter(pixels[pixel + 1] & 0xff, (pixels[pixel + 2] & 0xff) << 8, (pixels[pixel + 3] & 0xff) << 16)) {
					colorValue = 255;
				} else {
					colorValue = 0;
				}
				
				pixels[pixel + 1] = (byte) (colorValue & 0xff);
				pixels[pixel + 2] = (byte) ((colorValue >> 8) & 0xff);
				pixels[pixel + 3] = (byte) ((colorValue >> 16) & 0xff);
				
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				
				int colorValue;
				if(pixelMeetsHSVFilter(pixels[pixel] & 0xff, (pixels[pixel + 1] & 0xff) << 8, (pixels[pixel + 2] & 0xff) << 16)) {
					colorValue = 255;
				} else {
					colorValue = 0;
				}
				
				pixels[pixel + 0] = (byte) (colorValue & 0xff);
				pixels[pixel + 1] = (byte) ((colorValue >> 8) & 0xff);
				pixels[pixel + 2] = (byte) ((colorValue >> 16) & 0xff);
				
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}
		
		InputStream in = new ByteArrayInputStream(pixels);
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private BufferedImage resizeImage(BufferedImage image, double xMult, double yMult) {
		int newWidth = (int)(xMult * image.getWidth());
		int newHeight = (int)(yMult * image.getHeight());
		return (BufferedImage) image.getScaledInstance(newWidth,  newHeight, Image.SCALE_DEFAULT);
	}
	
	public double[][] getGoalCenters() {
		return null;
	}
}