package parquimetro.fiap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import parquimetro.fiap.model.FormaDePagamento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondutorPagamentoDTO {

    private Long id;
    private FormaDePagamento formaDePagamento;

}
