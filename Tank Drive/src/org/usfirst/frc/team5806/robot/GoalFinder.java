package org.usfirst.frc.team5806.robot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import edu.wpi.first.wpilibj.vision.USBCamera;

public class GoalFinder {
	USBCamera camera;
	public GoalFinder(USBCamera camera) {
		this.camera = camera;
	}
	
	private BufferedImage getImage() {
		ByteBuffer byteBuffer = null;
		camera.getImageData(byteBuffer);
		try {
			return ImageIO.read(new ByteArrayInputStream(byteBuffer.array()));
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