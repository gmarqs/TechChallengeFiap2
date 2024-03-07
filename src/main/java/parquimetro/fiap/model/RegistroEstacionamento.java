package parquimetro.fiap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroEstacionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Condutor condutor;
    private LocalDateTime horarioEntrada;
    @ManyToOne
    private Veiculo veiculo;
    private String nomeVeiculo;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;
    private int duracao;
    private String status;
}
