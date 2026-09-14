package cristianjimenez.udp.PrecioUnidad.dominio.vo;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.PuertoIncorrectoException;

public record PuertoRed(int valor) {

    public PuertoRed {
        if (valor < 1024 || valor > 65535) {
            throw new PuertoIncorrectoException("El puerto debe ser un número entre 1024 y 65535.");
        }
    }
}
