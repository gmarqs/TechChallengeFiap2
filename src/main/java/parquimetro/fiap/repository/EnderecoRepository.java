package parquimetro.fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parquimetro.fiap.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
