package parquimetro.fiap.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CondutorExceptionHandler {
    private StandartError err = new StandartError();

    @ExceptionHandler(CondutorNotFoundException.class)
    public ResponseEntity<StandartError> entityNotFound(CondutorNotFoundException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Entity not found");

        err.setMessage(e.getMessage());

        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandartError> enumNotFound(HttpMessageNotReadableException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Enum Not Found");

        err.setMessage("Forma de pagamento invalida. Só aceitamos DÉBITO, CRÉDITO ou PIX");

        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandartError> camposIncompatibilidade(MethodArgumentNotValidException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());

        err.setMessage("Existem campos com incompatibilidade");

        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(PagamentoIncompativelException.class)
    public ResponseEntity<StandartError> formaDePagamentoIncompativel(PagamentoIncompativelException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());

        err.setMessage("O método selecionado pelo condutor foi PIX. Esse método de pagamento só está disponível para períodos por tempo fixo. Caso ainda queira iniciar o período por hora, " +
                "favor alterar o método de pagamento para DEBITO ou CREDITO");

        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(PagamentoInvalidoException.class)
    public ResponseEntity<StandartError> PagamentoInvalido(PagamentoInvalidoException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());

        err.setMessage("A tentativa de pagamento está invalida. Motivo: pagamento via PIX insuficiente.");

        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }
}
