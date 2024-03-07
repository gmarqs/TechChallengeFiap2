package parquimetro.fiap.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parquimetro.fiap.model.RegistroEstacionamento;
import parquimetro.fiap.model.dto.ConsultaEstacionamentoDTO;
import parquimetro.fiap.model.dto.RegistroEstacionamentoDTO;
import parquimetro.fiap.service.EstacionamentoService;

@RestController
@RequestMapping("/estacionar")
public class EstacionamentoController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @PostMapping
    public void registrarEstacionamento(@Valid @RequestBody RegistroEstacionamentoDTO consultaEstacionamentoDTO){
        estacionamentoService.registrarEstacionamento(consultaEstacionamentoDTO);

        //TODO CRIAR AS EXCEPTIONS NECESSÁRIAS PARA ERROS
    }

    @GetMapping
    public ResponseEntity<ConsultaEstacionamentoDTO> verificaTempoRestante(@Valid @RequestBody ConsultaEstacionamentoDTO consultaEstacionamentoDTO){
        estacionamentoService.verificaTempoRestante(consultaEstacionamentoDTO);
        return ResponseEntity.ok(consultaEstacionamentoDTO);
    }


    
}
