package cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida;

import cristianjimenez.udp.PrecioUnidad.dominio.enums.EstadoServidor;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoServidor;

public interface PuertoNotificacionEvento {
    void notificarEvento(EventoServidor evento);

    void notificarCambiosEstado(EstadoServidor nuevoEstado, int puerto);
}
