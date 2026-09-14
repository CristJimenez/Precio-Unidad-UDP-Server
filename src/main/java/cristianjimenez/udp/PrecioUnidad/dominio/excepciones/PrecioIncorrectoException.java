package cristianjimenez.udp.PrecioUnidad.dominio.excepciones;

import java.io.Serial;

public class PrecioIncorrectoException extends DominioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PrecioIncorrectoException(final String mensaje) {
        super(mensaje);
    }
}
