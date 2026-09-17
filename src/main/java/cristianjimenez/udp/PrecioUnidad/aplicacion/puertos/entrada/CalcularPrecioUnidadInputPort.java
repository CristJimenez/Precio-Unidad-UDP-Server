package cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.CalcularPrecioUnidadCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ResultadoPrecioUnidadDto;

public interface CalcularPrecioUnidadInputPort {
    ResultadoPrecioUnidadDto calcular(CalcularPrecioUnidadCommand comando);
}
