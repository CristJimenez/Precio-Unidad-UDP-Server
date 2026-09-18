package cristianjimenez.udp.PrecioUnidad.aplicacion.servicios;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.CalcularPrecioUnidadCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ResultadoPrecioUnidadDto;
import cristianjimenez.udp.PrecioUnidad.aplicacion.mapper.CalculoMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.CalcularPrecioUnidadInputPort;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.Calculo;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.Resultado;

import java.util.Objects;

public final class CalcularPrecioUnidadService implements CalcularPrecioUnidadInputPort {

    private final CalculoMapper calculoMapper;

    public CalcularPrecioUnidadService(final CalculoMapper calculoMapper) {
        this.calculoMapper = Objects.requireNonNull(calculoMapper, "El mapper de calculo es obligatorio");
    }

    @Override
    public ResultadoPrecioUnidadDto calcular(final CalcularPrecioUnidadCommand comando) {
        final Calculo calculo = calculoMapper.toDomain(comando);
        final Resultado resultado = calculo.calcular();
        return calculoMapper.toDto(resultado);
    }
}
