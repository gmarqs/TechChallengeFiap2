package parquimetro.fiap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parquimetro.fiap.model.Veiculo;
import parquimetro.fiap.repository.VeiculoRepository;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public void save(List<Veiculo> veiculos){
        veiculos.forEach(veiculo -> veiculoRepository.save(veiculo));
    }
}
