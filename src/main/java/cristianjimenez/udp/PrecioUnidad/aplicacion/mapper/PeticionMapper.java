package cristianjimenez.udp.PrecioUnidad.aplicacion.mapper;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ProcesarPeticionUdpCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones.ComandoInvalidoException;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Destinatario;

import java.util.Objects;

public final class PeticionMapper {

    public Destinatario toDestinatario(final ProcesarPeticionUdpCommand comando) {
        if (Objects.isNull(comando)) {
            throw new ComandoInvalidoException("El comando de petición no puede ser nulo.");
        }
        return new Destinatario(comando.ipCliente(), comando.puertoCliente());
    }
}
