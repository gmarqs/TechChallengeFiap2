package parquimetro.fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parquimetro.fiap.model.Condutor;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

}
