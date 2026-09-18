package cristianjimenez.udp.PrecioUnidad.adaptadores.red;

import cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones.ServidorRedException;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.ControladorServidorRedPort;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.PuertoRed;

import java.net.SocketException;
import java.util.Objects;

public final class AdaptadorControlServidorRed implements ControladorServidorRedPort {

    private final CanalUdp canalUdp;
    private final Runnable accionIniciarListener;
    private final Runnable accionDetenerListener;

    public AdaptadorControlServidorRed(
            final CanalUdp canalUdp,
            final Runnable accionIniciarListener,
            final Runnable accionDetenerListener
    ) {
        this.canalUdp = Objects.requireNonNull(canalUdp, "El canal UDP es obligatorio.");
        this.accionIniciarListener = Objects.requireNonNull(accionIniciarListener, "La accion de inicio es obligatoria.");
        this.accionDetenerListener = Objects.requireNonNull(accionDetenerListener, "La accion de detencion es obligatoria.");
    }

    @Override
    public synchronized void iniciar(final PuertoRed puerto) throws ServidorRedException {
        try {
            canalUdp.abrir(Objects.requireNonNull(puerto, "El puerto es obligatorio.").valor());
            accionIniciarListener.run();
        } catch (SocketException excepcion) {
            throw new ServidorRedException("No se pudo abrir el servidor UDP en el puerto " + puerto.valor() + ".", excepcion);
        }
    }

    @Override
    public synchronized void detener() {
        accionDetenerListener.run();
        canalUdp.cerrar();
    }

    @Override
    public boolean isActivo() {
        return canalUdp.isAbierto();
    }

    @Override
    public PuertoRed getPuertoActual() {
        if (!canalUdp.isAbierto()) {
            return null;
        }

        return new PuertoRed(canalUdp.getPuertoActual());
    }
}
