package cristianjimenez.udp.PrecioUnidad.aplicacion.servicios;

import cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones.ServidorRedException;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.GestionarServidorInputPort;
import cristianjimenez.udp.PrecioUnidad.dominio.enums.EstadoServidor;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoServidor;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.ControladorServidorRedPort;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoNotificacionEvento;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.PuertoRed;

import java.util.Objects;

public final class GestionarServidorService implements GestionarServidorInputPort {

    private final ControladorServidorRedPort controladorRed;
    private final PuertoNotificacionEvento notificador;

    public GestionarServidorService(
            final ControladorServidorRedPort  controladorRed,
            final PuertoNotificacionEvento notificador
    ) {
        this.controladorRed = Objects.requireNonNull(controladorRed, "El controlador de red es obligatorio.");
        this.notificador = Objects.requireNonNull(notificador, "El notificador es obligatorio.");
    }

    @Override
    public void iniciarServidor(final int puerto) throws ServidorRedException {
        final PuertoRed puertoVo = new PuertoRed(puerto);
        controladorRed.iniciar(puertoVo);

        notificador.notificarCambiosEstado(EstadoServidor.EN_LINEA, puerto);
        notificador.notificarEvento(
                new EventoServidor("SERVICIO", "LOCAL" + puerto, "Servidor UDP iniciado en el puerto " + puerto + ".")
        );
    }

    @Override
    public void detenerServidor() {
        controladorRed.detener();

        notificador.notificarCambiosEstado(EstadoServidor.DETENIDO, 0);
        notificador.notificarEvento(
                new EventoServidor("SERVICIO", "LOCAL", "Servidor UDP detenido por el usuario")
        );
    }

    @Override
    public boolean estaCorriendo() {
        return controladorRed.isActivo();
    }

    @Override
    public int getPuertoActual() {
        final PuertoRed puerto = controladorRed.getPuertoActual();
        return Objects.nonNull(puerto) ? puerto.valor() : 0;
    }
}
