package cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ProcesarPeticionUdpCommand;

public interface ProcesarPeticionUdpInputPort {
    void procesar(ProcesarPeticionUdpCommand comando);
}
