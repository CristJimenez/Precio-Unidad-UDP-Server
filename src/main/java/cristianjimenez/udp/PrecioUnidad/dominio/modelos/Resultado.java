package cristianjimenez.udp.PrecioUnidad.dominio.modelos;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.ResultadoInvalidoException;

public class Resultado {

    private final double precioUnidad;

    public Resultado(final double precioUnidad) {
        if (precioUnidad < 0) {
            throw new ResultadoInvalidoException("El resultado no puede ser negativo");
        }

        this.precioUnidad = precioUnidad;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }
}
