package cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones;

import java.io.Serial;

public final class ComandoInvalidoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    
    public ComandoInvalidoException(String message) {
        super(message);
    }
}
