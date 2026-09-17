package cristianjimenez.udp.PrecioUnidad.aplicacion.mapper;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.CalcularPrecioUnidadCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ResultadoPrecioUnidadDto;
import cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones.ComandoInvalidoException;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.Calculo;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.Resultado;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Cantidad;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Precio;

import java.util.Objects;

public final class CalculoMapper {

    public Calculo toDomain(final CalcularPrecioUnidadCommand comando){
        if (Objects.isNull(comando)) {
            throw new ComandoInvalidoException("El comando del calculo no puede ser nulo.");
        }

        final Precio precio = new Precio(comando.precio());
        final Cantidad cantidad = new Cantidad(comando.cantidad());
        return new Calculo(precio, cantidad);
    }

    public Calculo toDomain(final double valorPrecio, final int valorCantidad){
        final Precio precio = new Precio(valorPrecio);
        final Cantidad cantidad = new Cantidad(valorCantidad);
        return new Calculo(precio, cantidad);
    }

    public ResultadoPrecioUnidadDto toDto(final Resultado resultado) {
        if (Objects.isNull(resultado)) {
            throw new ComandoInvalidoException("El resultado del calculo no puede ser nulo.");
        }

        return new ResultadoPrecioUnidadDto(resultado.getPrecioUnidad());
    }
}
