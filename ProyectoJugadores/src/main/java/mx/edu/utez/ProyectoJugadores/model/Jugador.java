package mx.edu.utez.ProyectoJugadores.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jugadores")

public class Jugador {
    @Id
    @GeneratedValue    (strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double saldo;

    @Column(nullable = false)
    private Boolean activo;

    public Jugador() {}

    public Jugador(String nombre, Double saldo, Boolean activo) {
        this.nombre = nombre;
        this.saldo = saldo;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}



