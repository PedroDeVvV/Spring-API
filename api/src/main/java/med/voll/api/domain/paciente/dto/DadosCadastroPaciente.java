package med.voll.api.domain.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.dto.DadosEndereco;

public record DadosCadastroPaciente(

        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotNull
        @Valid
        DadosEndereco endereco) {
}
