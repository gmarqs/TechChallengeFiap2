package parquimetro.fiap.utils;

import org.springframework.beans.factory.annotation.Autowired;
import parquimetro.fiap.exception.CondutorNotFoundException;
import parquimetro.fiap.model.*;
import parquimetro.fiap.repository.CondutorRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class ServiceUtils {

    @Autowired
    private CondutorRepository condutorRepository;

    public Optional<Veiculo> verificaVeiculos(String nomeveiculo, Condutor condutor) {
        return condutor.getVeiculos().stream().filter(veiculo -> veiculo.getNome().equalsIgnoreCase(nomeveiculo)).findAny();
    }

    public Condutor getCondutor(Long idCondutor) {
        return condutorRepository.findById(idCondutor).orElseThrow(() -> new CondutorNotFoundException("Condutor não encontrado"));

    }


    public ControleTempoEstacionamento getTempoEstacionado(RegistroEstacionamento registroEstacionamento, LocalDateTime horarioEntrada, LocalDateTime horarioFinal) {
        ControleTempoEstacionamento controleTempoEstacionamento = new ControleTempoEstacionamento();
        Duration tempoEstacionado = Duration.between(horarioEntrada, horarioFinal);
        Duration tempoPago;
        Duration tempoRestante;
        if (Periodo.FIXO.equals(registroEstacionamento.getPeriodo())) {
            tempoPago = Duration.ofHours(registroEstacionamento.getDuracao());
            tempoRestante = tempoPago.minus(tempoEstacionado);
            controleTempoEstacionamento.setHorario(tempoRestante.toHours());
            controleTempoEstacionamento.setMinutos(tempoRestante.toMinutes() % 60);
        }else{
            controleTempoEstacionamento.setHorario(tempoEstacionado.toHours());
            controleTempoEstacionamento.setMinutos(tempoEstacionado.toMinutes() % 60);
        }

        controleTempoEstacionamento.setDuracao(registroEstacionamento.getDuracao());
        controleTempoEstacionamento.setHorarioFinal(horarioFinal);

        return controleTempoEstacionamento;

    }
}
