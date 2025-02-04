package med.voll.api.domain.consulta.services;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.dtos.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.repository.ConsultaRepository;
import med.voll.api.domain.consulta.dtos.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dtos.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.validation.ValidadorAgendamentoDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.repository.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.repository.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

//Agenda de consultas
@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsultas> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe");
        }

        validadores.forEach(v -> v.validar(dados));

        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente()); //.get(); //sem o get, ele retorna um optional
        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("A especialidade é obrigatória quando o médico não for selecionado.");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }


    public void cancelar(DadosCancelamentoConsulta dados) {
        LocalDateTime date = LocalDateTime.now();

        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());

        Duration duracao = Duration.between(consulta.getData(), date);
        if (duracao.toHours() > -24) {
            throw new ValidacaoException("Uma consulta deve ser desmarcada com 24H de antecedência.");
        }
        consulta.cancelar(dados.motivo());
    }
}
