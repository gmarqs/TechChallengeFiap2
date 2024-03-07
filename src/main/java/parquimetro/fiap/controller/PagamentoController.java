package parquimetro.fiap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parquimetro.fiap.model.FormaDePagamento;
import parquimetro.fiap.model.Pagamento;
import parquimetro.fiap.model.dto.CondutorDTO;
import parquimetro.fiap.model.dto.CondutorPagamentoDTO;
import parquimetro.fiap.service.PagamentoService;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PatchMapping
    public ResponseEntity<String> cadastraFormaDePagamento(@Valid @RequestBody CondutorPagamentoDTO condutorPagamentoDTO){
        pagamentoService.cadastraFormaDePagamento(condutorPagamentoDTO);
        if(FormaDePagamento.PIX.equals(condutorPagamentoDTO.getFormaDePagamento()))

            return ResponseEntity.ok().body("Pagamento vinculado com sucesso. PIX só será aceito se for para períodos de estacionamento fixos.");

        return ResponseEntity.ok().body("Pagamento vinculado com sucesso.");

    }
}
