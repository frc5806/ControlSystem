import java.lang.Object;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SolenoidBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public void forward(Compressor c, Solenoid s) {
    c.setClosedLoopControl(true);
    s.set (DoubleSolenoid.value.kForward);
}

public void reverse(Compressor c, Solenoid s) {
    c.setClosedLoopControl(true);
    s.set (DoubleSolenoid.value.kReverse);
}

public void off(Compressor c, Solenoid s) {
    c.setClosedLoopControl(false);
    s.set (DoubleSolenoid.value.kOff);
}