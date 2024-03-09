package parquimetro.fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parquimetro.fiap.model.Condutor;
import parquimetro.fiap.model.RegistroEstacionamento;

import java.util.Optional;

@Repository
public interface EstacionamentoRepository extends JpaRepository<RegistroEstacionamento, Long> {

    @Query("SELECT x from RegistroEstacionamento x where x.condutor = ?2 AND lower(x.nomeVeiculo) = lower(?3) and x.status = ?1")
    Optional<RegistroEstacionamento> findVeiculoStatusE(String status, Condutor condutor, String nomeveiculo);
}
