package cristianjimenez.udp.PrecioUnidad.adaptadores.red;

import cristianjimenez.udp.PrecioUnidad.adaptadores.red.mapper.UdpNetworkMapper;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.RespuestaCliente;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoNotificacionEvento;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoSalidaRed;

import java.util.Objects;

public final class AdaptadorSalidaUdp implements PuertoSalidaRed {

    private final CanalUdp canalUdp;
    private final UdpNetworkMapper mapper;
    private final PuertoNotificacionEvento notificador;

    public AdaptadorSalidaUdp(
            final CanalUdp canalUdp,
            final UdpNetworkMapper mapper,
            final PuertoNotificacionEvento notificador
    ) {
        this.canalUdp = Objects.requireNonNull(canalUdp, "El canal UDP es obligatorio.");
        this.mapper = Objects.requireNonNull(mapper, "El mapper UDP es obligatorio.");
        this.notificador = Objects.requireNonNull(notificador, "El notificador es obligatorio.");
    }

    @Override
    public void enviarRespuesta(final RespuestaCliente respuesta) {}
}
