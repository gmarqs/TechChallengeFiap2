package parquimetro.fiap.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ControleTempoEstacionamento {

    private LocalDateTime horarioAtual = LocalDateTime.now();
    private LocalDateTime horarioFinal;
    private Long horario;
    private Long minutos;
    private Integer duracao;

}
