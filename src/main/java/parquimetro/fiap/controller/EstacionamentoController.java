package parquimetro.fiap.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<Void> registrarEstacionamento(@Valid @RequestBody RegistroEstacionamentoDTO consultaEstacionamentoDTO){
        estacionamentoService.registrarEstacionamento(consultaEstacionamentoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/consultar")
    public ResponseEntity<ConsultaEstacionamentoDTO> consultarTempoRestante(@Valid @RequestBody ConsultaEstacionamentoDTO consultaEstacionamentoDTO){
        estacionamentoService.consultarTempoRestante(consultaEstacionamentoDTO);
        return ResponseEntity.ok(consultaEstacionamentoDTO);
    }


    
}
