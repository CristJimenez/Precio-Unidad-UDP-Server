package cristianjimenez.udp.PrecioUnidad.dominio.vo;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.PrecioIncorrectoException;

public record Precio(double valor) {

    private static final String MENSAJE_ERROR = "Precio incorrecto, debe ser mayor a 0.";

    public Precio {
        if (valor <= 0) {
            throw new PrecioIncorrectoException(MENSAJE_ERROR);
        }
    }
}
