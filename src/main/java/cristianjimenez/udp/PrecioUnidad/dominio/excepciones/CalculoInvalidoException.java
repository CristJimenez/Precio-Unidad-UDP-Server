package cristianjimenez.udp.PrecioUnidad.dominio.excepciones;

import java.io.Serial;

public class CalculoInvalidoException extends DominioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CalculoInvalidoException(final String mensaje) {
        super(mensaje);
    }
}
