package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ButtonHandler {
	Button[] buttons;
	public ButtonHandler(Joystick joystick) {
		buttons = new Button[]{new Button(joystick, 1), new Button(joystick, 3), new Button(joystick, 4), new Button(joystick, 2)};
	}
	
	public boolean readButton(char buttonLabel) {
		if(buttonLabel == 'A') return buttons[0].readButton();
		if(buttonLabel == 'X') return buttons[1].readButton();
		if(buttonLabel == 'Y') return buttons[2].readButton();
		if(buttonLabel == 'B') return buttons[3].readButton();
		
		return false;
	}
}
