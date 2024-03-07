package parquimetro.fiap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import parquimetro.fiap.model.Condutor;
import parquimetro.fiap.model.FormaDePagamento;
import parquimetro.fiap.model.dto.CondutorDTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotBlank
    private FormaDePagamento formaDePagamento;
   // @OneToOne
    //private Condutor idCondutor;
}
