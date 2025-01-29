package med.voll.api.domain.medico.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.dto.DadosEndereco;
import med.voll.api.domain.medico.enums.Especialidade;

public record DadosCadastroMedico(

        @NotBlank
        String nome,
        @NotBlank
        @Email(message = "O email é obrigatório")
        String email,
        @NotBlank
        String telefone,
        @NotBlank(message = "O crm é obrigatório e único") //mensagens personalizadas do bean validation
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid
        DadosEndereco endereco) {

}
