package cristianjimenez.udp.PrecioUnidad.dominio.modelos;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.ResultadoInvalidoException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Resultado {

    private final double precioUnidad;

    public Resultado(final double precioUnidad) {
        if (precioUnidad < 0) {
            throw new ResultadoInvalidoException("El resultado no puede ser negativo");
        }

        this.precioUnidad = precioUnidad;
    }

    public String getPrecioUnidadFormateado() {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        final DecimalFormat df = new DecimalFormat("#0.00", symbols);
        return df.format(precioUnidad);
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }
}
