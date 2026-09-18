package cristianjimenez.udp.PrecioUnidad.aplicacion.servicios;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ProcesarPeticionUdpCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.mapper.CalculoMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.mapper.PeticionMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.ProcesarPeticionUdpInputPort;
import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.DominioException;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.Calculo;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoServidor;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.RespuestaCliente;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.Resultado;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoNotificacionEvento;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoSalidaRed;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Destinatario;

import java.util.Locale;
import java.util.Objects;

public final class ProcesarPeticionUdpService implements ProcesarPeticionUdpInputPort {

    private final PuertoSalidaRed puertoSalidaRed;
    private final PuertoNotificacionEvento puertoNotificacion;
    private final PeticionMapper peticionMapper;
    private final CalculoMapper calculoMapper;

    public ProcesarPeticionUdpService(
            final PuertoSalidaRed puertoSalidaRed,
            final PuertoNotificacionEvento puertoNotificacion,
            final PeticionMapper peticionMapper,
            final CalculoMapper calculoMapper
    ) {
        this.puertoSalidaRed = Objects.requireNonNull(puertoSalidaRed, "El puerto de salida es obligatorio.");
        this.puertoNotificacion = Objects.requireNonNull(puertoNotificacion, "El puerto de notificación es obligatorio.");
        this.peticionMapper = Objects.requireNonNull(peticionMapper, "El mapper de petición es obligatorio.");
        this.calculoMapper = Objects.requireNonNull(calculoMapper, "El mapper de cálculo es obligatorio.");
    }

    @Override
    public void procesar(final ProcesarPeticionUdpCommand comando) {
        Objects.requireNonNull(comando, "El comando es obligatorio.");

        final Destinatario destinatario = peticionMapper.toDestinatario(comando);
        final String mensaje = comando.mensaje();

        if (Objects.isNull(mensaje) || mensaje.isBlank()) {
            puertoSalidaRed.enviarRespuesta(RespuestaCliente.error(destinatario, "Mensaje vacío recibido."));
            notificarEvento(destinatario.endpoint(), "datos recibidos --> [Vacío]");
            return;
        }

        final String comandoTexto = mensaje.trim();

        if (comandoTexto.equalsIgnoreCase("CONECTAR")) {
            RespuestaCliente.conectado(destinatario, "Servidor UDP listo para recibir calculos");
            notificarEvento(destinatario.endpoint(),  "conectado --> Solicitud de verificacion recibida y aceptada");
            return;
        }

        if (comandoTexto.equalsIgnoreCase("DESCONECTAR")) {
            RespuestaCliente.desconectado(destinatario, "Sesion finalizada");
            notificarEvento(destinatario.endpoint(), "desconectado --> Cliente ha cerrado la sesion");
            return;
        }

        if (comandoTexto.toUpperCase(Locale.ROOT).startsWith("CALCULAR;")) {
            procesarCalculo(destinatario, comandoTexto);
            return;
        }

        puertoSalidaRed.enviarRespuesta(RespuestaCliente.error(destinatario, "Comando no reconocido por el servidor UDP."));
        notificarEvento(destinatario.endpoint(), "datos recibidos --> Comando desconocido: [" + comandoTexto + "]");
    }

    private void procesarCalculo(final Destinatario destinatario, final String comandoTexto) {
        final String[] partes = comandoTexto.split(";", -1);

        if (partes.length != 3) {
            puertoSalidaRed.enviarRespuesta(RespuestaCliente.error(destinatario, "Formato invalido. Se esperaba CALCULAR;precio;cantidad"));
            notificarEvento(destinatario.endpoint(), "datos recibidos --> Formato incorrecto: " + comandoTexto);
            return;
        }

        try {
            final double valorPrecio = Double.parseDouble(partes[1].trim().replace(",", "."));
            final int valorCantidad = Integer.parseInt(partes[2].trim().replace(",", "."));

            final Calculo calculo = calculoMapper.toDomain(valorPrecio, valorCantidad);
            final Resultado resultado = calculo.calcular();

            puertoSalidaRed.enviarRespuesta(RespuestaCliente.calculoExitoso(destinatario, resultado));

            final String logInfo = String.format(
                    Locale.US,
                    "datos recibidos (Precio: $%.2f, Cantidad: %d) --> datos enviados (Precio por Unidad: %s)",
                    calculo.getPrecio(),
                    calculo.getCantidad(),
                    resultado.getPrecioUnidad()
            );

            notificarEvento(destinatario.endpoint(), logInfo);
        } catch (final NumberFormatException exception) {
            puertoSalidaRed.enviarRespuesta(RespuestaCliente.error(destinatario, "Los parametros de precio y cantidad deben ser numericos."));
            notificarEvento(destinatario.endpoint(), "datos recibios --> Error de formato numerico: " + comandoTexto);
        } catch (final DominioException exception) {
            puertoSalidaRed.enviarRespuesta(RespuestaCliente.error(destinatario, exception.getMessage()));
            notificarEvento(destinatario.endpoint(), exception.getMessage());
        }
    }

    public void notificarEvento(final String endpoint, final String descripcion) {
        puertoNotificacion.notificarEvento(
                new EventoServidor("EVENTO", endpoint, descripcion)
        );
    }
}
