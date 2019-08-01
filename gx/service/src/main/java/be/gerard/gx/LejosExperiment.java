package be.gerard.gx;

import lejos.hardware.Audio;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.remote.ev3.RemoteEV3;

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
        a();
    }

    private static void a() throws RemoteException, NotBoundException, MalformedURLException {
        // https://sourceforge.net/p/lejos/wiki/Remote%20access%20to%20an%20EV3/

        final BrickInfo[] bricks = BrickFinder.discover();

        for (BrickInfo info : bricks) {
            final Brick brick = new RemoteEV3(info.getIPAddress());
            brick.getAudio().systemSound(0);
        }
        final RemoteEV3 ev3 = new RemoteEV3("");
        final Audio sound = ev3.getAudio();
        sound.systemSound(0);
    }

}
