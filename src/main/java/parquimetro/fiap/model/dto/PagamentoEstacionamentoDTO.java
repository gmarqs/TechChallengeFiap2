package parquimetro.fiap.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoEstacionamentoDTO {

    private Long idCondutor;
    @NotBlank
    private String veiculo;

}
