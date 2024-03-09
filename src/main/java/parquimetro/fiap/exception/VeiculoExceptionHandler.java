package parquimetro.fiap.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class VeiculoExceptionHandler {

    private StandartError err = new StandartError();

    @ExceptionHandler(VeiculoJaEstacionadoException.class)
    public ResponseEntity<StandartError> veiculoFound(VeiculoJaEstacionadoException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Error: Veiculo Found");

        err.setMessage("O veiculo já está estacionado.");

        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(VeiculoNotFoundException.class)
    public ResponseEntity<StandartError> veiculoNotFound(VeiculoNotFoundException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());

        err.setMessage("Veículo informado não foi encontrado na base de dados do condutor. Verifique novamente!");

        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }
}
