package cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada;

import cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones.ServidorRedException;

public interface GestionarServidorInputPort {
    void iniciarServidor(int puerto) throws ServidorRedException;

    void detenerServidor();

    boolean estaCorriendo();

    int getPuertoActual();
}
