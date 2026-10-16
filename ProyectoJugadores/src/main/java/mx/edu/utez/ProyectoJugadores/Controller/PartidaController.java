package mx.edu.utez.ProyectoJugadores.Controller;

import jakarta.validation.Valid;
import mx.edu.utez.ProyectoJugadores.dto.IniciarPartidaDto;
import mx.edu.utez.ProyectoJugadores.model.Partida;
import mx.edu.utez.ProyectoJugadores.model.Tiro;
import mx.edu.utez.ProyectoJugadores.service.JuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final JuegoService juegoService;

    public PartidaController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciarPartida(@Valid @RequestBody IniciarPartidaDto dto) {
        return new ResponseEntity<>(juegoService.iniciarPartida(dto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/tiro")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.realizarTiro(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.finalizarPartidaManual(id));
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Partida>> obtenerHistorialPartida(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(juegoService.obtenerHistorialPartida(jugadorId));
    }
}