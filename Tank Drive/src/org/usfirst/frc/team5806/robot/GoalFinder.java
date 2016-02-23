package org.usfirst.frc.team5806.robot;

import java.awt.image.BufferedImage;
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
	
	public double[][] getGoalCenters() {
		System.out.println(getImage().getHeight());
		return null;
	}
}