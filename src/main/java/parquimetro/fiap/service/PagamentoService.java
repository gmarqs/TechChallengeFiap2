package parquimetro.fiap.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parquimetro.fiap.exception.CondutorNotFoundException;
import parquimetro.fiap.model.Condutor;
import parquimetro.fiap.model.Pagamento;
import parquimetro.fiap.model.dto.CondutorDTO;
import parquimetro.fiap.model.dto.CondutorPagamentoDTO;
import parquimetro.fiap.repository.CondutorRepository;
import parquimetro.fiap.repository.PagamentoRepository;

import java.util.Optional;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private CondutorRepository condutorRepository;

    public CondutorPagamentoDTO cadastraFormaDePagamento(CondutorPagamentoDTO condutorPagamentoDTO) {
        Condutor condutor = condutorRepository.findById(condutorPagamentoDTO.getId()).orElseThrow(() -> new CondutorNotFoundException("Condutor não encontrado"));

        condutor.setFormaDePagamento(condutorPagamentoDTO.getFormaDePagamento());
        condutorRepository.save(condutor);

        return condutorPagamentoDTO;
    }

    //TODO FAZER METODO PARA ALTERAR A FORMA DE PAGAMENTO
}
