package parquimetro.fiap.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import parquimetro.fiap.model.Periodo;
import parquimetro.fiap.model.dto.RegistroEstacionamentoDTO;

public class DuracaoValidator implements ConstraintValidator<DuracaoValida, RegistroEstacionamentoDTO> {

    @Override
    public void initialize(DuracaoValida constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegistroEstacionamentoDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getPeriodo() == null) {
            return false;
        }

        if (Periodo.FIXO.equals(dto.getPeriodo())) {
            return dto.getDuracao() != null && dto.getDuracao() > 0;
        } else if (Periodo.HORA.equals(dto.getPeriodo())) {
            return dto.getDuracao() == null;
        }

        return true;
    }
}
