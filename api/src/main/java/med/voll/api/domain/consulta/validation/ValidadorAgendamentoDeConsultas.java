package med.voll.api.domain.consulta.validation;

import med.voll.api.domain.consulta.dtos.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsultas {

    void validar(DadosAgendamentoConsulta dados);

}
