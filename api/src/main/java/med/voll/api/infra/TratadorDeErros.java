package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
//        return ResponseEntity.notFound().build(); não retorna nenhuma mensagem
        return ResponseEntity.status(404).body("Entidade não localizada.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();

        //converte a lista de erros em uma lista com dto do tipo DadosErrosValidacao que só tem 2 campos
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());
//        return ResponseEntity.status(400).body("Os paramêtros não correspondem ao solicitado.");
    }

    private record DadosErrosValidacao(String campo, String mensagem) {
        public DadosErrosValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
