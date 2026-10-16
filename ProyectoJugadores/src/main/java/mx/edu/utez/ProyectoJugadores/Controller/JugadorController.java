package mx.edu.utez.ProyectoJugadores.Controller;

import jakarta.validation.Valid;
import mx.edu.utez.ProyectoJugadores.dto.CrearJugadorDto;
import mx.edu.utez.ProyectoJugadores.dto.RecargarSaldoDto;
import mx.edu.utez.ProyectoJugadores.model.Jugador;
import mx.edu.utez.ProyectoJugadores.service.JuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JuegoService juegoService;

    public JugadorController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@Valid @RequestBody CrearJugadorDto dto) {
        return new ResponseEntity<>(juegoService.crearJugador(dto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@PathVariable Long id, @Valid @RequestBody RecargarSaldoDto dto) {
        return ResponseEntity.ok(juegoService.recargarSaldo(id, dto));
    }
}