package parquimetro.fiap.Validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuracaoValidator.class)
@Documented
public @interface DuracaoValida {

    String message() default "Se a opção do período for HORA, o campo duração deve ser 0 ou nulo. Se a opção do período for FIXO, deverá haver uma duração maior que 0.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
