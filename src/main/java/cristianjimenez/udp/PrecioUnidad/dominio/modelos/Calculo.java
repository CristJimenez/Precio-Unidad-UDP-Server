package cristianjimenez.udp.PrecioUnidad.dominio.modelos;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.CalculoInvalidoException;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Cantidad;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Precio;

import java.util.Objects;

public final class Calculo {

    private final Precio precio;
    private final Cantidad cantidad;

    public Calculo(final Precio precio, final Cantidad cantidad) {
        if (Objects.isNull(precio) || Objects.isNull(cantidad)) {
            throw new CalculoInvalidoException("El peso y la altura son obligatorios para calcular el IMC.");
        }
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Resultado calcular() {
        final double precioUnidad = precio.valor() / cantidad.valor();

        return new Resultado(precioUnidad);
    }

    public Precio getPrecio() {
        return precio;
    }

    public Cantidad getCantidad() {
        return cantidad;
    }
}
