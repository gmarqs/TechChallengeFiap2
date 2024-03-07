package parquimetro.fiap.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import parquimetro.fiap.model.Endereco;
import parquimetro.fiap.model.Genero;
import parquimetro.fiap.model.Veiculo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondutorDTO{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank
        private String nome;
        private String endereco;
        @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}|\\d{11}|\\(\\d{2}\\)\\s\\d{5}-\\d{4}")
        @NotBlank(message = "Telefone inválido")
        private String telefone;
        @Range(min = 18, message = "Idade inválida!")
        int idade;
        @Email
        private String email;
        @Enumerated(EnumType.STRING)
        private Genero genero;
        private List<VeiculoDTO> veiculos;



}
