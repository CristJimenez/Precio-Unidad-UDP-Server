package cristianjimenez.udp.PrecioUnidad.dominio.modelos;

import cristianjimenez.udp.PrecioUnidad.dominio.enums.TipoRespuesta;
import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.RespuestaInvalidaException;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Destinatario;

import java.util.Objects;

public final class RespuestaCliente {

    private final Destinatario destinatario;
    private final TipoRespuesta tipo;
    private final Resultado resultado;
    private final String mensaje;

    private RespuestaCliente(
            final Destinatario destinatario,
            final TipoRespuesta tipo,
            final Resultado resultado,
            final String mensaje
    ) {
        if (Objects.isNull(destinatario) || Objects.isNull(tipo)) {
            throw new RespuestaInvalidaException("El destinatario y el tipo de respuesta son obligatorios.");
        }

        if (tipo == TipoRespuesta.CONECTADO && Objects.isNull(resultado)) {
            throw new RespuestaInvalidaException("Una respuesta de calculo debe tener un resultado.");
        }

        this.destinatario = destinatario;
        this.tipo = tipo;
        this.resultado = resultado;
        this.mensaje = mensaje;
    }

    public static RespuestaCliente conectado(final Destinatario destinatario, final String mensaje) {
        return new RespuestaCliente(destinatario, TipoRespuesta.CONECTADO, null, mensaje);
    }

    public static RespuestaCliente desconectado(final Destinatario destinatario, final String mensaje) {
        return new RespuestaCliente(destinatario, TipoRespuesta.DESCONECTADO, null, mensaje);
    }

    public static RespuestaCliente calculoExitoso(final Destinatario destinatario, final Resultado resultado) {
        return new RespuestaCliente(destinatario, TipoRespuesta.OK_CALCULO, resultado, null);
    }

    public static RespuestaCliente error(final Destinatario destinatario, final String mensajeError) {
        return new RespuestaCliente(destinatario, TipoRespuesta.ERROR, null, mensajeError);
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public TipoRespuesta getTipo() {
        return tipo;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public String getMensaje() {
        return mensaje;
    }
}
