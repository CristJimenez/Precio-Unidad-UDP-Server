package cristianjimenez.udp;

import cristianjimenez.udp.PrecioUnidad.adaptadores.red.AdaptadorControlServidorRed;
import cristianjimenez.udp.PrecioUnidad.adaptadores.red.AdaptadorNotificacionEvento;
import cristianjimenez.udp.PrecioUnidad.adaptadores.red.AdaptadorSalidaUdp;
import cristianjimenez.udp.PrecioUnidad.adaptadores.red.CanalUdp;
import cristianjimenez.udp.PrecioUnidad.adaptadores.red.mapper.UdpNetworkMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.mapper.CalculoMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.mapper.PeticionMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.GestionarServidorInputPort;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.ProcesarPeticionUdpInputPort;
import cristianjimenez.udp.PrecioUnidad.aplicacion.servicios.GestionarServidorService;
import cristianjimenez.udp.PrecioUnidad.aplicacion.servicios.ProcesarPeticionUdpService;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.ControladorServidorRedPort;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoSalidaRed;
import cristianjimenez.udp.PrecioUnidad.entrypoint.gui.ServidorFrame;
import cristianjimenez.udp.PrecioUnidad.entrypoint.udp.ReceptorPeticionesUdp;

import javax.swing.*;

public class Main {

    private static final System.Logger LOG = System.getLogger(Main.class.getName());

    private Main() {}

    public static void main(final String[] args) {
        aplicarLookAndFeel();

        final CanalUdp canalUdp = new CanalUdp();
        final AdaptadorNotificacionEvento notificador = new AdaptadorNotificacionEvento();
        final UdpNetworkMapper redMapper = new UdpNetworkMapper();
        final PuertoSalidaRed puertoSalidaRed = new AdaptadorSalidaUdp(canalUdp, redMapper, notificador);

        final PeticionMapper peticionMapper = new PeticionMapper();
        final CalculoMapper calculoMapper = new CalculoMapper();

        final ProcesarPeticionUdpInputPort procesarPeticionPort = new ProcesarPeticionUdpService(
                puertoSalidaRed,
                notificador,
                peticionMapper,
                calculoMapper
        );

        final ReceptorPeticionesUdp receptorUdp = new ReceptorPeticionesUdp(canalUdp, procesarPeticionPort, notificador);

        final ControladorServidorRedPort controladorRed = new AdaptadorControlServidorRed(
                canalUdp,
                receptorUdp::iniciar,
                receptorUdp::detener
        );

        final GestionarServidorInputPort gestionarServidorPort = new GestionarServidorService(controladorRed, notificador);

        SwingUtilities.invokeLater(() -> {
                    final ServidorFrame frame = new ServidorFrame(gestionarServidorPort);
                    notificador.registrarObservador(frame);
                    frame.setVisible(true);
                });
    }

    private static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException
                       | InstantiationException
                       | IllegalAccessException
                       | UnsupportedLookAndFeelException excepcion) {
            LOG.log(System.Logger.Level.DEBUG,
                    "No fue posible aplicar la apariencia del sistema; se usará la predeterminada.", excepcion);
        }
    }
}
