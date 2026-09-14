package cristianjimenez.udp.PrecioUnidad.dominio.vo;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.DestinatarioIncorrectoException;
import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.PuertoIncorrectoException;

import java.util.Objects;

public record Destinatario(String ip, int puerto) {

    public Destinatario {
        if (Objects.isNull(ip) || ip.isBlank()) {
            throw new DestinatarioIncorrectoException("La dirección IP del destinatario no puede ser nula ni vacía.");
        }

        if (puerto <=1024 || puerto > 65535) {
            throw new PuertoIncorrectoException("El puerto del destinatario debe ser un número entre 1 y 65535.");
        }

        ip = ip.trim();
    }

    public String endpoint() {
        return ip + ":" + puerto;
    }
}
