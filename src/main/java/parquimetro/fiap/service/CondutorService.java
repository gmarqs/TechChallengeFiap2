package parquimetro.fiap.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parquimetro.fiap.model.Condutor;
import parquimetro.fiap.model.Veiculo;
import parquimetro.fiap.model.dto.CondutorDTO;
import parquimetro.fiap.repository.EnderecoRepository;
import parquimetro.fiap.repository.CondutorRepository;
import parquimetro.fiap.repository.VeiculoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondutorService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CondutorRepository repository;
    @Autowired
    private VeiculoService veiculoService;

    @Transactional
    public void save(CondutorDTO condutorDTO) {
        Condutor condutor = modelMapper.map(condutorDTO, Condutor.class);
        List<Veiculo> veiculoList = condutorDTO.getVeiculos().stream().map(veiculo -> modelMapper.map(veiculo, Veiculo.class)).toList();

        repository.save(condutor);
        veiculoList.forEach(veiculo -> veiculo.setProprietario(condutor));
        veiculoService.save(veiculoList);




    }
}
