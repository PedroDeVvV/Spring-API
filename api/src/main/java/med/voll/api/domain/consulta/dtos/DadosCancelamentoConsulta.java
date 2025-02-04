package med.voll.api.domain.consulta.dtos;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.enums.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}