package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Agenda de consultas
@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados) {

        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get(); //sem o get, ele retorna um optional
        Medico medico = medicoRepository.findById(dados.idMedico()).get();// sem o get retorna um optional

       var consulta = new Consulta(null, medico, paciente, dados.data());
       consultaRepository.save(consulta);
    }

}
