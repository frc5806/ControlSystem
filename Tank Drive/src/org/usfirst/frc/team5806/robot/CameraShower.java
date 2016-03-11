package org.usfirst.frc.team5806.robot;

public class CameraShower implements Runnable {
	TargetTracker tracker;
	public CameraShower(TargetTracker tracker) {
		this.tracker = tracker;
	}
	@Override
	public void run() {
		while(true) {
			tracker.showImage();
		}
	}
}
