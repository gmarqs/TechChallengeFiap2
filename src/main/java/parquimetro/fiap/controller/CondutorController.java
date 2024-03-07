package parquimetro.fiap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parquimetro.fiap.model.dto.CondutorDTO;
import parquimetro.fiap.service.CondutorService;

@RestController
@RequestMapping("/parquimetro")
public class CondutorController {

    @Autowired
    private CondutorService service;

    @PostMapping("/salvaCondutor")
    private ResponseEntity<String> salvaCondutor(@Valid @RequestBody CondutorDTO condutor){
        service.save(condutor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Condutor criado com sucesso");
    }

}
