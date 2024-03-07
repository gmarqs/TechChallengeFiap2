package parquimetro.fiap.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEstacionamentoDTO {

    private Long idCondutor;
    @NotBlank
    private String veiculo;
    private String mensagem;
}
