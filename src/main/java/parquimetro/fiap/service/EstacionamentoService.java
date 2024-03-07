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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EstacionamentoService {

    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public void registrarEstacionamento(RegistroEstacionamentoDTO registroEstacionamentoDTO) {
        Condutor condutor = condutorRepository.findById(registroEstacionamentoDTO.getCondutor()).orElseThrow(() -> new CondutorNotFoundException("Condutor não encontrado"));
        verificaFormaDePagamento(condutor, registroEstacionamentoDTO.getPeriodo());

        Optional<Veiculo> veiculoEncontrado = verificaVeiculos(registroEstacionamentoDTO.getVeiculo(), condutor);

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

    @Transactional
    public ConsultaEstacionamentoDTO verificaTempoRestante(ConsultaEstacionamentoDTO consultaEstacionamentoDTO) {
        Condutor condutor = condutorRepository.findById(consultaEstacionamentoDTO.getIdCondutor()).orElseThrow(() -> new CondutorNotFoundException("Condutor não encontrado"));
        Optional<Veiculo> veiculoEncontrado = verificaVeiculos(consultaEstacionamentoDTO.getVeiculo(), condutor);

        if (veiculoEncontrado.isPresent()) {
            RegistroEstacionamento registro = estacionamentoRepository.findVeiculoStatusE("E", condutor, veiculoEncontrado.get().getNome());
            consultaEstacionamentoDTO.setMensagem(verificaPeriodo(registro));
        }else {
            throw new VeiculoNotFoundException("Veiculo não encontrado");
        }
        return consultaEstacionamentoDTO;
    }

    private static String verificaPeriodo(RegistroEstacionamento registro) {
        LocalDateTime horarioEntrada = registro.getHorarioEntrada();
        LocalDateTime horarioAtual = LocalDateTime.now();
        long horasRestantes;
        long minutosRestantes;
        if (Periodo.FIXO.equals(registro.getPeriodo())) {
            int duracao = registro.getDuracao();

            LocalDateTime horarioFinal = horarioEntrada.plusHours(duracao);

            if (horarioAtual.isAfter(horarioFinal)) {
                return "O período já excedeu o tempo limite!!!!! Favor retirar o veículo.";
            } else {
                horasRestantes = ChronoUnit.HOURS.between(horarioAtual, horarioFinal);
                minutosRestantes = ChronoUnit.MINUTES.between(horarioAtual, horarioFinal) % 60;

                return String.format("O período restante de estacionamento é de: %d horas e %d minutos", horasRestantes, minutosRestantes);
            }
        } else {
            horasRestantes = ChronoUnit.HOURS.between(horarioEntrada, horarioAtual);
            minutosRestantes = ChronoUnit.MINUTES.between(horarioEntrada, horarioAtual) % 60;
            return String.format("O veículo já está estacionado por %d horas e %d minutos. O sistema estenderá automaticamente o estacionamento" +
                    "por mais 1 hora, a menos que o condutor efetue o pagamento", horasRestantes, minutosRestantes);
        }
    }
}