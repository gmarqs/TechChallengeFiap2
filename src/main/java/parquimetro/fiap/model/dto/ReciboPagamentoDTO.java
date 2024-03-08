package parquimetro.fiap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReciboPagamentoDTO {

    private Long horasEstacionadas;
    private Double valorPorHora;
    private Double valorAPagar;
    private Double valorPago;
    private String nomeCondutor;
    private String nomeVeiculo;
    private String mensagem;

}
