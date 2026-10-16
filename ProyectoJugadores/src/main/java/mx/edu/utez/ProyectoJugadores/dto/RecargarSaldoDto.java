package mx.edu.utez.ProyectoJugadores.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RecargarSaldoDto {

    @NotNull(message = "El monto a recargar es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
}