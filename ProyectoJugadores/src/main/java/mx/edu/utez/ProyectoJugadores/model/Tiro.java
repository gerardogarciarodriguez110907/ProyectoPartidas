package mx.edu.utez.ProyectoJugadores.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tiros")
public class Tiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    @Column(nullable = false)
    private Integer valorDado1;

    @Column(nullable = false)
    private Integer valorDado2;

    @Column(nullable = false)
    private Integer suma;

    @Column(nullable = false)
    private Boolean esGanador;

    public Tiro() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Partida getPartida() { return partida; }
    public void setPartida(Partida partida) { this.partida = partida; }

    public Integer getValorDado1() { return valorDado1; }
    public void setValorDado1(Integer valorDado1) { this.valorDado1 = valorDado1; }

    public Integer getValorDado2() { return valorDado2; }
    public void setValorDado2(Integer valorDado2) { this.valorDado2 = valorDado2; }

    public Integer getSuma() { return suma; }
    public void setSuma(Integer suma) { this.suma = suma; }

    public Boolean getEsGanador() { return esGanador; }
    public void setEsGanador(Boolean esGanador) { this.esGanador = esGanador; }
}