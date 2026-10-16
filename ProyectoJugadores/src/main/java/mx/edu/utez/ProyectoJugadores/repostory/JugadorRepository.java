package mx.edu.utez.ProyectoJugadores.repostory;

import mx.edu.utez.ProyectoJugadores.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}