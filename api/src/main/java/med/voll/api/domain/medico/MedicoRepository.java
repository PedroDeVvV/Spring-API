package med.voll.api.domain.medico;

import jakarta.validation.constraints.Future;
import med.voll.api.domain.medico.enums.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao); //interface para paginação

    @Query("""
            SELECT m FROM Medico m
            WHERE m.ativo = true
            AND
            m.especialidade = :especialidade
            AND 
            m.id not in(
                SELECT c.medico.id 
                FROM Consulta c 
                WHERE c.data = :data
            )
            order by rand()
            limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
