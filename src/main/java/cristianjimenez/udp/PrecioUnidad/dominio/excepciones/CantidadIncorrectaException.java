package cristianjimenez.udp.PrecioUnidad.dominio.excepciones;

import java.io.Serial;

public class CantidadIncorrectaException extends DominioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CantidadIncorrectaException(final String mensaje) {
        super(mensaje);
    }
}
