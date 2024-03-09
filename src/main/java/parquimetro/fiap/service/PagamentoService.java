package parquimetro.fiap.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parquimetro.fiap.exception.CondutorNotFoundException;
import parquimetro.fiap.exception.PagamentoIncompativelException;
import parquimetro.fiap.exception.VeiculoNotFoundException;
import parquimetro.fiap.model.*;
import parquimetro.fiap.model.dto.CondutorDTO;
import parquimetro.fiap.model.dto.CondutorPagamentoDTO;
import parquimetro.fiap.model.dto.PagamentoEstacionamentoDTO;
import parquimetro.fiap.model.dto.ReciboPagamentoDTO;
import parquimetro.fiap.repository.CondutorRepository;
import parquimetro.fiap.repository.EstacionamentoRepository;
import parquimetro.fiap.repository.PagamentoRepository;
import parquimetro.fiap.utils.ServiceUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private ServiceUtils serviceUtils;

    private static final Double valorTarifa = 12.0;

    public CondutorPagamentoDTO cadastraFormaDePagamento(CondutorPagamentoDTO condutorPagamentoDTO) {
        Condutor condutor = serviceUtils.getCondutor(condutorPagamentoDTO.getId());

        condutor.setFormaDePagamento(condutorPagamentoDTO.getFormaDePagamento());
        condutorRepository.save(condutor);

        return condutorPagamentoDTO;
    }

    public ReciboPagamentoDTO pagarEstacionamento(PagamentoEstacionamentoDTO pagamentoEstacionamentoDTO) {
        ReciboPagamentoDTO reciboPagamentoDTO = new ReciboPagamentoDTO();
        double valorPagar = 0;
        ControleTempoEstacionamento tempoEstacionado;
        Condutor condutor = serviceUtils.getCondutor(pagamentoEstacionamentoDTO.getIdCondutor());
        Optional<Veiculo> veiculo = serviceUtils.verificaVeiculos(pagamentoEstacionamentoDTO.getVeiculo(), condutor);

        if (veiculo.isPresent()) {
            Optional<RegistroEstacionamento> registro = estacionamentoRepository.findVeiculoStatusE("E", condutor, veiculo.get().getNome());
            if (registro.isEmpty()){
                throw new VeiculoNotFoundException("Veiculo não está estacionado");
            }

            RegistroEstacionamento registroEstacionamento = registro.get();
            registroEstacionamento.setStatus("P");

            tempoEstacionado = serviceUtils.getTempoEstacionado(registroEstacionamento, registroEstacionamento.getHorarioEntrada(), LocalDateTime.now());
            if (tempoEstacionado.getDuracao() != null && tempoEstacionado.getDuracao() > 0) {
                int duracaoDoPeriodo = registroEstacionamento.getDuracao();
                valorPagar = duracaoDoPeriodo * valorTarifa;
                registroEstacionamento.setValorPago(valorPagar);

                reciboPagamentoDTO.setValorAPagar(valorPagar);
                reciboPagamentoDTO.setValorPago(valorPagar);
                reciboPagamentoDTO.setHorasEstacionadas(Long.valueOf(tempoEstacionado.getDuracao()));
                estacionamentoRepository.save(registroEstacionamento);

            } else {
                long horarioRestante = tempoEstacionado.getHorario();
                if(horarioRestante == 0) {
                    horarioRestante = 1;
                }

                valorPagar = horarioRestante * valorTarifa;
                registroEstacionamento.setValorPago(valorPagar);

                reciboPagamentoDTO.setHorasEstacionadas(tempoEstacionado.getMinutos());
                reciboPagamentoDTO.setValorPago(valorPagar);
                estacionamentoRepository.save(registroEstacionamento);
            }
        }else{
            throw new VeiculoNotFoundException("Veiculo não encontrado");
        }
        reciboPagamentoDTO.setNomeCondutor(condutor.getNome());
        reciboPagamentoDTO.setNomeVeiculo(veiculo.get().getNome());
        reciboPagamentoDTO.setValorPorHora(valorTarifa);
        reciboPagamentoDTO.setMensagem("Recibo emitido. Agradecemos pela preferencia!");

        return reciboPagamentoDTO;
    }
}