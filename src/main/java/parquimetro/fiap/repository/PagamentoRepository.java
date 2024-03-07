package parquimetro.fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parquimetro.fiap.model.Condutor;
import parquimetro.fiap.model.FormaDePagamento;
import parquimetro.fiap.model.Pagamento;

@Repository
public interface PagamentoRepository  extends JpaRepository<Pagamento, Long> {

}
