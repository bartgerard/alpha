package be.gerard.robot;

import be.gerard.robot.util.Devices;
import com.j4ev3.core.Brick;
import com.j4ev3.core.Button;
import com.j4ev3.core.LCD;
import com.j4ev3.core.LED;
import com.j4ev3.core.Motor;
import com.j4ev3.core.Sensor;
import com.j4ev3.desktop.BluetoothComm;

import javax.bluetooth.RemoteDevice;
import java.util.Optional;

/**
 * Application
 *
 * @author bartgerard
 * @version v0.0.1
 */
public class Application {

    private static final String EV3_NAME = "EV3";

    public static void main(
            final String[] args
    ) {
        final Optional<RemoteDevice> ev3 = Devices.findByName(EV3_NAME);

        ev3.map(RemoteDevice::getBluetoothAddress)
           .ifPresent(address -> {
               final Brick brick = new Brick(new BluetoothComm(address));
               brick.getButton().buttonPressed(Button.ANY_BUTTON);
               brick.getLCD().drawLine(LCD.COLOR_BLACK, 0, 0, 45, 45);
               brick.getLED().setPattern(LED.LED_GREEN_FLASH);
               brick.getMotor().turnAtPower((byte) (Motor.PORT_B + Motor.PORT_C), 100);
               brick.getSensor().getValuePercent(Sensor.PORT_3, Sensor.TYPE_COLOR, Sensor.COLOR_AMBIENT);
               brick.getSpeaker().playTone(100, 600, 1000);
               brick.getMotor().stopMotor((byte) (Motor.PORT_B + Motor.PORT_C), true);
           });

    }

}
