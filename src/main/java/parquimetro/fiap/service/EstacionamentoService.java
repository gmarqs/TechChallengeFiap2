package parquimetro.fiap.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parquimetro.fiap.exception.CondutorNotFoundException;
import parquimetro.fiap.exception.PagamentoIncompativelException;
import parquimetro.fiap.exception.VeiculoNotFoundException;
import parquimetro.fiap.model.*;
import parquimetro.fiap.model.dto.ConsultaEstacionamentoDTO;
import parquimetro.fiap.model.dto.RegistroEstacionamentoDTO;
import parquimetro.fiap.repository.CondutorRepository;
import parquimetro.fiap.repository.EstacionamentoRepository;
import parquimetro.fiap.repository.PagamentoRepository;
import parquimetro.fiap.utils.ServiceUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EstacionamentoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    public void registrarEstacionamento(RegistroEstacionamentoDTO registroEstacionamentoDTO) {
        Condutor condutor = serviceUtils.getCondutor(registroEstacionamentoDTO.getCondutor());
        verificaFormaDePagamento(condutor, registroEstacionamentoDTO.getPeriodo());

        Optional<Veiculo> veiculoEncontrado = serviceUtils.verificaVeiculos(registroEstacionamentoDTO.getVeiculo(), condutor);

        if (veiculoEncontrado.isPresent()) {
            RegistroEstacionamento registroEstacionamento = modelMapper.map(registroEstacionamentoDTO, RegistroEstacionamento.class);
            registroEstacionamento.setCondutor(condutor);
            registroEstacionamento.setVeiculo(veiculoEncontrado.get());
            registroEstacionamento.setNomeVeiculo(veiculoEncontrado.get().getNome());
            registroEstacionamento.setHorarioEntrada(LocalDateTime.now());
            registroEstacionamento.setStatus("E");
            estacionamentoRepository.save(registroEstacionamento);
        } else {
            throw new VeiculoNotFoundException("Veiculo não encontrado");
        }

    }

    private void verificaFormaDePagamento(Condutor condutor, Periodo periodo) {
        if (FormaDePagamento.PIX.equals(condutor.getFormaDePagamento()) && Periodo.HORA.equals(periodo)){
            throw new PagamentoIncompativelException("Pagamento Inválido");
        }
    }

    private static Optional<Veiculo> verificaVeiculos(String nomeveiculo, Condutor condutor) {
        return condutor.getVeiculos().stream().filter(veiculo -> veiculo.getNome().equalsIgnoreCase(nomeveiculo)).findAny();
    }


    public ConsultaEstacionamentoDTO verificaTempoRestante(ConsultaEstacionamentoDTO consultaEstacionamentoDTO) {
        Condutor condutor = serviceUtils.getCondutor(consultaEstacionamentoDTO.getIdCondutor());
        Optional<Veiculo> veiculoEncontrado = verificaVeiculos(consultaEstacionamentoDTO.getVeiculo(), condutor);

        if (veiculoEncontrado.isPresent()) {
            RegistroEstacionamento registro = estacionamentoRepository.findVeiculoStatusE("E", condutor, veiculoEncontrado.get().getNome());
            consultaEstacionamentoDTO.setMensagem(verificaPeriodo(registro));
        }else {
            throw new VeiculoNotFoundException("Veiculo não encontrado");
        }
        return consultaEstacionamentoDTO;
    }

    private String verificaPeriodo(RegistroEstacionamento registro) {
        LocalDateTime horarioEntrada = registro.getHorarioEntrada();
        ControleTempoEstacionamento controleTempoEstacionado = serviceUtils.getTempoEstacionado(registro, horarioEntrada, LocalDateTime.now());
        if (controleTempoEstacionado.getDuracao() > 0) {

            if (controleTempoEstacionado.getHorarioAtual().isAfter(controleTempoEstacionado.getHorarioFinal())) {
                return "O período já excedeu o tempo limite!!!!! Favor retirar o veículo.";
            } else {
                return String.format("O período restante de estacionamento é de: %d horas e %d minutos", controleTempoEstacionado.getHorario(), controleTempoEstacionado.getMinutos());
            }
        } else {
            return String.format("O veículo já está estacionado por %d horas e %d minutos. O sistema estenderá automaticamente o estacionamento" +
                    "por mais 1 hora, a menos que o condutor efetue o pagamento", controleTempoEstacionado.getHorario(), controleTempoEstacionado.getMinutos());
        }
    }
}