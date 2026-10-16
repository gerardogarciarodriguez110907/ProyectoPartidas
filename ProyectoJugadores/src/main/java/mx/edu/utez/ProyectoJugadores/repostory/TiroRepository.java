package mx.edu.utez.ProyectoJugadores.repostory;

import mx.edu.utez.ProyectoJugadores.model.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {
}