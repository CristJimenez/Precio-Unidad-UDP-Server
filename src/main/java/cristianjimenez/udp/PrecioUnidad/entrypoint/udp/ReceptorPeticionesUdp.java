package cristianjimenez.udp.PrecioUnidad.entrypoint.udp;

import cristianjimenez.udp.PrecioUnidad.adaptadores.red.CanalUdp;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.ProcesarPeticionUdpInputPort;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoServidor;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoNotificacionEvento;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ReceptorPeticionesUdp {

    private static final int BUFFER_SIZE = 2048;

    private final CanalUdp canalUdp;
    private final ProcesarPeticionUdpInputPort inputPort;
    private final PuertoNotificacionEvento notificador;

    private Thread hiloEscucha;
    private volatile boolean escuchando = false;

    public ReceptorPeticionesUdp(
            final CanalUdp canalUdp,
            final ProcesarPeticionUdpInputPort inputPort,
            final PuertoNotificacionEvento notificador
    ) {
        this.canalUdp = Objects.requireNonNull(canalUdp, "El canal UDP es obligatorio.");
        this.inputPort = Objects.requireNonNull(inputPort, "El puerto de entrada es obligatorio.");
        this.notificador = Objects.requireNonNull(notificador, "El notificador es obligatorio.");
    }

    public synchronized void iniciar() {
        if (escuchando) {
            return;
        }

        escuchando = true;
        hiloEscucha = new Thread(this::cicloEscucha, "Thread-ReceptorPeticionesUdp-" + canalUdp.getPuertoActual());
        hiloEscucha.start();
    }

    private synchronized void detener() {
        escuchando = false;

        if (Objects.nonNull(hiloEscucha) && hiloEscucha.isAlive()) {
            hiloEscucha.interrupt();
        }

        hiloEscucha = null;
    }

    private boolean isEscuchando() {
        return escuchando;
    }

    private void cicloEscucha() {
        final byte[] buffer = new byte[BUFFER_SIZE];

        while (escuchando && canalUdp.isAbierto()) {
            try {
                final DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                canalUdp.recibir(paquete);

                final String texto = new String(paquete.getData(), 0, paquete.getLength(), StandardCharsets.UTF_8).trim();
                final InetAddress direccion = paquete.getAddress();
                final int puerto = paquete.getPort();
            } catch (final SocketException excepcion) {
                if (!escuchando) {
                    break;
                }

                notificarError("Error de socket al recibir paquete: " + excepcion.getMessage());
            } catch (final IOException excepcion) {
                if (escuchando) {
                    notificarError("Error I/O al recibir datagrama: " + excepcion.getMessage());
                }
            }
        }
    }

    private void notificarError(final String mensaje) {
        notificador.notificarEvento(new EventoServidor("ERROR", "SOCKET", mensaje));
    }
}
