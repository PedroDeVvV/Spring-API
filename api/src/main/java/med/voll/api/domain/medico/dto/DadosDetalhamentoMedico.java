package med.voll.api.domain.medico.dto;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.enums.Especialidade;
import med.voll.api.domain.medico.Medico;

public record DadosDetalhamentoMedico(Long id,
                                      String nome,
                                      String email,
                                      String crm,
                                      Especialidade especialidade,
                                      Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getTelefone(),
                medico.getEspecialidade(),
                medico.getEndereco()
        );
    }
}
