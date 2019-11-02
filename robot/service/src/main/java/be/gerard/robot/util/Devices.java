package be.gerard.robot.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Devices
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Devices {

    public static Optional<RemoteDevice> findByName(
            final String name
    ) {
        try {
            final LocalDevice localDevice = LocalDevice.getLocalDevice();
            final DiscoveryAgent discoveryAgent = localDevice.getDiscoveryAgent();
            final RemoteDevice[] remoteDevices = discoveryAgent.retrieveDevices(DiscoveryAgent.PREKNOWN);// DiscoveryAgent.CACHED

            for (final RemoteDevice remoteDevice : remoteDevices) {
                final String friendlyName = remoteDevice.getFriendlyName(false);

                if (Objects.equals(friendlyName, name)) {
                    return Optional.of(remoteDevice);
                }
            }
        } catch (IOException exception) {
            log.error("discoveryAgent exception", exception);
        }

        return Optional.empty();
    }

}
