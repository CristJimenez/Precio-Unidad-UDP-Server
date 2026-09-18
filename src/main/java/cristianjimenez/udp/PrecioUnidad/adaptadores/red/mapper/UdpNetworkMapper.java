package cristianjimenez.udp.PrecioUnidad.adaptadores.red.mapper;

import cristianjimenez.udp.PrecioUnidad.adaptadores.red.response.UdpResponse;
import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.RespuestaInvalidaException;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.RespuestaCliente;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.Resultado;

import java.util.Objects;

public final class UdpNetworkMapper {

    public UdpResponse toNetworkResponse(final RespuestaCliente respuesta) {
        if(Objects.isNull(respuesta)) {
            throw new RespuestaInvalidaException("No se puede serializar una respuesta nula.");
        }

        final String payload = switch (respuesta.getTipo()) {
            case CONECTADO -> "CONECTADO_OK;" + respuesta.getMensaje();
            case DESCONECTADO -> "DESCONECTADO_OK;" + respuesta.getMensaje();
            case OK_CALCULO -> buildPayloadCalculo(respuesta.getResultado());
            case ERROR ->  "ERROR;" + (Objects.nonNull(respuesta.getMensaje()) ? respuesta.getMensaje() : "Error desconocido");
        };

        return new UdpResponse(payload, respuesta.getDestinatario().ip(), respuesta.getDestinatario().puerto());
    }

    private static String buildPayloadCalculo(final Resultado resultado) {
        if (Objects.isNull(resultado)) {
            throw new RespuestaInvalidaException("No se puede serializar un calculo sin resultado.");
        }

        return String.format(
                "OK_CALCULO;%s",
                resultado.getPrecioUnidadFormateado()
        );
    }
}
