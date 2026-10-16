package mx.edu.utez.ProyectoJugadores.repostory;

import mx.edu.utez.ProyectoJugadores.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    // Permite obtener el historial de partidas de un jugador específico
    List<Partida> findByJugadorId(Long jugadorId);
}