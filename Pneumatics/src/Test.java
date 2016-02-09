import edu.wpi.first.wpilibj.Solenoid;

public class Test {

}

public void forward(Compressor c, Solenoid s) {
    c.setClosedLoopControl(true);
    s.set (DoubleSolenoid.value.kForward);
}

public void reverse(Compressor c, Solenoid s) {
     c.setClosedLoopControl(true);
    s.set (DoubleSolenoid.value.kReverse);
}

public void reverse(Compressor c, Solenoid s) {
c.setClosedLoopControl(false);
     s.set (DoubleSolenoid.value.kOff);
}