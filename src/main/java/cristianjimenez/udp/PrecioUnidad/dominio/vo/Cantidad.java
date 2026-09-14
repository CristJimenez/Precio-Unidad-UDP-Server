package cristianjimenez.udp.PrecioUnidad.dominio.vo;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.CantidadIncorrectaException;

public record Cantidad(int valor) {

    private static final String MENSAJE_ERROR = "Cantidad incorrecta, debe ser mayor a 0";

    public Cantidad {
        if (valor <= 0) {
            throw new CantidadIncorrectaException(MENSAJE_ERROR);
        }
    }
}
