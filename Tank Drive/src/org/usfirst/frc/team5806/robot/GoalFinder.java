package org.usfirst.frc.team5806.robot;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import edu.wpi.first.wpilibj.vision.USBCamera;

//get image data from camera via getImage(ByteBuffer buff)

public class GoalFinder {
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
	
	private BufferedImage resizeImage(BufferedImage image, double xMult, double yMult) {
		int newWidth = (int)(xMult * image.getWidth());
		int newHeight = (int)(yMult * image.getHeight());
		return (BufferedImage) image.getScaledInstance(newWidth,  newHeight, Image.SCALE_DEFAULT);
	}
	
	public double[][] getGoalCenters() {
		System.out.println(getImage().getHeight());
		return null;
	}
}