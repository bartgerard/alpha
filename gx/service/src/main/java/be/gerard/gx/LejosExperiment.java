package be.gerard.gx;

import lejos.hardware.Audio;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * LejosExperiment
 *
 * @author bartgerard
 * @version v0.0.1
 */
public class LejosExperiment {

    public static void main(
            final String[] args
    ) throws RemoteException, NotBoundException, MalformedURLException {
        c();
    }

    private static void a() throws RemoteException, NotBoundException, MalformedURLException {
        // https://sourceforge.net/p/lejos/wiki/Remote%20access%20to%20an%20EV3/

        RemoteEV3 ev3 = new RemoteEV3("192.168.1.100");
        ev3.setDefault();
        Sound.beep();
        //final RMIRegulatedMotor m = ev3.createRegulatedMotor("B", 'L');
        //m.rotate(360);
        ev3.getPort("B");


        final BrickInfo[] bricks = BrickFinder.discover();

        for (BrickInfo info : bricks) {
            final Brick brick = new RemoteEV3(info.getIPAddress());
            brick.getAudio().systemSound(0);
        }
        //final RemoteEV3 ev3 = new RemoteEV3("");
        //final Audio sound = ev3.getAudio();
        //sound.systemSound(0);
    }

    private static void b() throws RemoteException, NotBoundException, MalformedURLException {
        // https://sourceforge.net/p/lejos/wiki/Remote%20access%20to%20an%20EV3/

        final BrickInfo[] bricks = BrickFinder.discover();

        for (BrickInfo info : bricks) {
            final Brick ev3 = new RemoteEV3(info.getIPAddress());
            Audio sound = ev3.getAudio();
            sound.systemSound(0);
        }
    }

    private static void c() throws RemoteException, NotBoundException, MalformedURLException {
        // https://sourceforge.net/p/lejos/wiki/Remote%20access%20to%20an%20EV3/

        final RemoteEV3 ev3 = new RemoteEV3("192.168.1.100");
        ev3.setDefault();
        // ev3.getLED().setPattern(EV3LED.COLOR_GREEN);

        final Audio sound = ev3.getAudio();
        sound.systemSound(0);

        final RMIRegulatedMotor motorB = ev3.createRegulatedMotor("B", 'L');
        final RMIRegulatedMotor motorC = ev3.createRegulatedMotor("C", 'L');

        final RMIRegulatedMotor[] motors = new RMIRegulatedMotor[]{motorB, motorC};

        for (final RMIRegulatedMotor motor : motors) {
            motor.rotate(360, true);
        }

        for (final RMIRegulatedMotor motor : motors) {
            motor.waitComplete();
        }

        for (final RMIRegulatedMotor motor : motors) {
            motor.close();
        }
    }

    private static void d() throws RemoteException, NotBoundException, MalformedURLException {
        final RemoteEV3 ev3 = new RemoteEV3("192.168.1.100");
        //ev3.setDefault();
        Sound.beep();

        final RegulatedMotor b = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("B"));
        final RegulatedMotor c = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("C"));

        final Wheel wheel1 = WheeledChassis.modelWheel(b, MoveController.WHEEL_SIZE_EV3).offset(-72);
        final Wheel wheel2 = WheeledChassis.modelWheel(c, MoveController.WHEEL_SIZE_EV3).offset(72);
        final Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL);
        final MovePilot pilot = new MovePilot(chassis);
        pilot.setAngularSpeed(30);  // cm per second
        pilot.travel(50);         // cm
        pilot.rotate(-90);        // degree clockwise
        pilot.travel(-50, true);  //  move backward for 50 cm
        while (pilot.isMoving()) {
            Thread.yield();
        }
        pilot.rotate(-90);
        //pilot.rotateTo(270);
        pilot.stop();

        b.close();
        c.close();
    }

}
