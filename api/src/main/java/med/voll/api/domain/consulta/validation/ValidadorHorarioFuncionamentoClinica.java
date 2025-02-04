package med.voll.api.domain.consulta.validation;

import med.voll.api.domain.consulta.dtos.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsultas {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaClinica || depoisDoEncerramentoClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica.");
        }
    }
}
