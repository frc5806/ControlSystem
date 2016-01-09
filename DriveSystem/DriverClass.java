// Imports all of JInput
import net.java.games.input.*;

public class DriverClass {
    
    public static void main(String[] args) {
        // This clears the console of text
        System.out.print("\u000c");
        
        Controller cOne = findJoystick();
        if (cOne==null) {
            System.out.println("No joysticks found./nQuitting...");
            System.exit(0);
        }
        System.out.println("\nCo-ordinates (X, Y)\nRefreshes every three seconds.");
        while (true) {
            System.out.println(getXValue(cOne) +", " +getYValue(cOne));
            pause(3000);
        }
    }
    
    public static void pause(int i) {
        try {
            //thread to sleep for the specified number of milliseconds
            Thread.sleep(i);
        } catch ( java.lang.InterruptedException ie) {
            System.out.println(ie);
        }
    }
    
    // Finds a joystick among the available controllers
    public static Controller findJoystick() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        // If JInput cannot find any controllers, you have a linker problem
        if(controllers.length == 0) {
            System.out.println("Hmmmmm you may have a problem. Bring this to Michael Truell, and he will check your installation.");
        } else {
            System.out.println("Yay it worked");
        }
        
        for (Controller controller : controllers) {
            // Prints out the controller's name (i.e. "Logitech Joystick")
            System.out.println("\nname: " + controller.getName());

            // If controller is type stick, it is a joystick (i.e. a knob of the Logitech Controllers)
            if(controller.getType() == Controller.Type.STICK) {
                System.out.println("Connected to controller " +controller.getName() +", it is a " +controller.getType());
                Component[] components = controller.getComponents();
                System.out.println("This controller has the following components");
                for (int i=0; i< components.length ; i++) {
                    System.out.println(components[i]);
                }
                return null;//controller;
            }
            else if(controller.getType() == Controller.Type.MOUSE) {
                System.out.println("Connected to controller " +controller.getName() +", it is a " +controller.getType());
                Component[] components = controller.getComponents();
                System.out.println("This controller has the following components");
                for (int i=0; i< components.length ; i++) {
                    System.out.println(components[i]);
                }
                return null;//controller;
            }
            else {System.out.println("Fail connecting to controller " +controller.getName() +", it is of the unsupported type " +controller.getType());}
        }

        // Could not find a joystick
        return null;
    }


    // Gets the value of the x-axis of a joystick, given the joystick
    public static int getXValue(Controller controller) {
        // Refreshes the controller
        controller.poll();

        // Gets an array of the controller's components (i.e. the controller's x axis and y axis)
        Component[] components = controller.getComponents();
        
        // Go through all of the components of a joystick
        for (Component component : components) {
            // Check if x axis
            
            if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.X) {
                return (int)((double)-component.getPollData()*100);
            }
            // To get the value of the y-axis instead:
            /*
             * if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.X) {
                    return (int)((double)-component.getPollData()*100);
             * }
            */
        }

        // Could not find an X axis component of the joystick
        // Instead return a really negative number
        return -10000;
    }
    public static int getYValue(Controller controller) {
        // Refreshes the controller
        controller.poll();

        // Gets an array of the controller's components (i.e. the controller's x axis and y axis)
        Component[] components = controller.getComponents();
        
        // Go through all of the components of a joystick
        for (Component component : components) {
            // Check if x axis
            
            if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.Y) {
                return (int)((double)-component.getPollData()*100);
            }
            // To get the value of the y-axis instead:
            /*
             * if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.X) {
                    return (int)((double)-component.getPollData()*100);
             * }
            */
        }

        // Could not find an X axis component of the joystick
        // Instead return a really negative number
        return -10000;
    }
}
