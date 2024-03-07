package parquimetro.fiap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoDTO {

    private String nome;
    private String montadora;
    private String ano;
    private String placa;
}
