package cristianjimenez.udp.PrecioUnidad.adaptadores.red;

import cristianjimenez.udp.PrecioUnidad.dominio.enums.EstadoServidor;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoServidor;

public interface ObservadorServidor {
    void onEvento(EventoServidor evento);

    void onCambioEstado(EstadoServidor nuevoEstado, int puerto);
}
