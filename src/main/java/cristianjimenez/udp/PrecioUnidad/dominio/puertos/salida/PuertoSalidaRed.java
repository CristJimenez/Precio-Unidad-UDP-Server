package cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida;

import cristianjimenez.udp.PrecioUnidad.dominio.modelos.RespuestaCliente;

public interface PuertoSalidaRed {
    void enviarRespuesta(RespuestaCliente respuesta);
}
