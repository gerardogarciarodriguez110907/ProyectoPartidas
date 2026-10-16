package mx.edu.utez.ProyectoJugadores.service;

import mx.edu.utez.ProyectoJugadores.dto.CrearJugadorDto;
import mx.edu.utez.ProyectoJugadores.dto.IniciarPartidaDto;
import mx.edu.utez.ProyectoJugadores.dto.RecargarSaldoDto;
import mx.edu.utez.ProyectoJugadores.Exception.BadRequestException;
import mx.edu.utez.ProyectoJugadores.Exception.ResourceNotFoundException;
import mx.edu.utez.ProyectoJugadores.model.EstadoPartida;
import mx.edu.utez.ProyectoJugadores.model.Jugador;
import mx.edu.utez.ProyectoJugadores.model.Partida;
import mx.edu.utez.ProyectoJugadores.model.Tiro;
import mx.edu.utez.ProyectoJugadores.repostory.JugadorRepository;
import mx.edu.utez.ProyectoJugadores.repostory.PartidaRepository;
import mx.edu.utez.ProyectoJugadores.repostory.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class JuegoService {

    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public JuegoService(JugadorRepository jugadorRepository,
                        PartidaRepository partidaRepository,
                        TiroRepository tiroRepository) {
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
        this.tiroRepository = tiroRepository;
    }

    // Crear un nuevo jugador
    public Jugador crearJugador(CrearJugadorDto dto) {
        Jugador jugador = new Jugador(dto.getNombre(), dto.getSaldo(), true);
        return jugadorRepository.save(jugador);
    }

    // 1. recargarSaldo (10 pts)
    @Transactional
    public Jugador recargarSaldo(Long jugadorId, RecargarSaldoDto dto) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado con ID: " + jugadorId));

        jugador.setSaldo(jugador.getSaldo() + dto.getMonto());
        return jugadorRepository.save(jugador);
    }

    // 2. iniciarPartida (35 pts)
    @Transactional
    public Partida iniciarPartida(IniciarPartidaDto dto) {
        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado con ID: " + dto.getJugadorId()));

        // Validar que el jugador esté activo
        if (!Boolean.TRUE.equals(jugador.getActivo())) {
            throw new BadRequestException("El jugador se encuentra inactivo");
        }

        // Validar saldo suficiente
        if (jugador.getSaldo() < dto.getApuesta()) {
            throw new BadRequestException("Saldo insuficiente para realizar la apuesta");
        }

        // Descontar apuesta del saldo
        jugador.setSaldo(jugador.getSaldo() - dto.getApuesta());
        jugadorRepository.save(jugador);

        // Crear partida en estado EN_JUEGO
        Partida partida = new Partida();
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(dto.getApuesta());

        return partidaRepository.save(partida);
    }

    // 3. realizarTiro (35 pts)
    @Transactional
    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida no encontrada con ID: " + partidaId));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new BadRequestException("La partida no está en curso o ya ha finalizado");
        }

        // Generar 2 valores aleatorios (1-6)
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        boolean esGanador = false;

        // Reglas del juego
        if (suma == 7 || suma == 11) {
            // Gana: duplica la apuesta y finaliza
            esGanador = true;
            partida.setEstado(EstadoPartida.FINALIZADA);

            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            // Pierde: finaliza la partida
            esGanador = false;
            partida.setEstado(EstadoPartida.FINALIZADA);
        } else {
            // Continúa en juego
            esGanador = false;
        }

        partidaRepository.save(partida);

        // Guardar tiro como último paso
        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(esGanador);

        return tiroRepository.save(tiro);
    }

    // 4. finalizarPartidaManual (15 pts)
    @Transactional
    public Partida finalizarPartidaManual(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida no encontrada con ID: " + partidaId));

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new BadRequestException("La partida ya se encuentra finalizada");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    // 5. obtenerHistorialPartida (10 pts)
    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new ResourceNotFoundException("Jugador no encontrado con ID: " + jugadorId);
        }
        return partidaRepository.findByJugadorId(jugadorId);
    }
}