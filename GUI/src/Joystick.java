import net.java.games.input.*;

// Represents a Joystick. Used for querying a desired motor value.
public class Joystick {
	Controller controller;

	// Initializes the joystick with a Controller object
	public Joystick(Controller controller_) {
		controller = controller_;
	}

	// Gets the joystick's current x value. Ranges from -100 to 100
	public int getX() {
		controller.poll();
		Component[] components = controller.getComponents();
		for (Component component : components) {
			if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.X) {
				return (int)((double)-component.getPollData()*100);
			}
		}
	}

	// Gets the joystick's current y value. Ranges from -100 to 100
	public int getY() {
		controller.poll();
		Component[] components = controller.getComponents();
		for (Component component : components) {
			if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.Y) {
				return (int)((double)-component.getPollData()*100);
			}
		}
	}
}