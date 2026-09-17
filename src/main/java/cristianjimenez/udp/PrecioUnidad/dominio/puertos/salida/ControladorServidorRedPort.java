package cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida;

import cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones.ServidorRedException;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.PuertoRed;

public interface ControladorServidorRedPort {
    void iniciar(PuertoRed puerto) throws ServidorRedException;

    void detener();

    boolean isActivo();

    PuertoRed getPuertoActual();
}
