package org.usfirst.frc.team5806.robot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
	private BufferedImage byteBufferToBufferedImage(ByteBuffer buff) {
		
	}
	
	private void resizeImage()
	
	public double[][] getGoalCenters() {
		System.out.println(getImage().getHeight());
		return null;
	}
}