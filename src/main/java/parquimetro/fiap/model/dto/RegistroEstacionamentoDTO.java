package parquimetro.fiap.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import parquimetro.fiap.model.Periodo;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroEstacionamentoDTO {


    private Long condutor;
    @NotBlank
    private String veiculo;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;
    @NotNull(message = "A duração não pode ser nula")
    @Range(min = 0, message = "A duração deve ser maior ou igual a zero")
    private int duracao;
    private String mensagem;

}
