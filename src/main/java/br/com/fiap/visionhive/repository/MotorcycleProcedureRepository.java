package br.com.fiap.visionhive.repository;

import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MotorcycleProcedureRepository {

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall atualizarSituacaoCall;
    private SimpleJdbcCall associarPatioCall;
    private SimpleJdbcCall contarPatiosCall;

    @PostConstruct
    public void init() {
        this.atualizarSituacaoCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_MOTOCICLETAS")
                .withProcedureName("PRC_ATUALIZAR_SITUACAO")
                .declareParameters(
                        new SqlParameter("p_moto_id", Types.NUMERIC),
                        new SqlParameter("p_nova_situacao", Types.VARCHAR),
                        new SqlOutParameter("p_json", Types.VARCHAR),
                        new SqlOutParameter("p_mensagem", Types.VARCHAR)
                );

        this.associarPatioCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_MOTOCICLETAS")
                .withProcedureName("PRC_ASSOCIAR_PATIO")
                .declareParameters(
                        new SqlParameter("p_moto_id", Types.NUMERIC),
                        new SqlParameter("p_patio_id", Types.NUMERIC),
                        new SqlOutParameter("p_mensagem", Types.VARCHAR)
                );

        this.contarPatiosCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_MOTOCICLETAS")
                .withFunctionName("FN_CONTAR_PATIOS_FILIAL");
    }

    public ProcedureResponse atualizarSituacao(Long motoId, String novaSituacao) {
        Map<String, Object> out = atualizarSituacaoCall.execute(
                motoId, novaSituacao
        );
        return new ProcedureResponse(
                (String) out.get("p_json"),
                (String) out.get("p_mensagem")
        );
    }

    public String associarPatio(Long motoId, Long patioId) {
        Map<String, Object> out = associarPatioCall.execute(motoId, patioId);
        return (String) out.get("p_mensagem");
    }

    public int contarPatiosPorFilial(Long branchId) {
        try {
            BigDecimal result = contarPatiosCall.executeFunction(BigDecimal.class, branchId);
            int count = result != null ? result.intValue() : 0;
            System.out.println("FN_CONTAR_PATIOS_FILIAL retornou: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Erro na function FN_CONTAR_PATIOS_FILIAL: " + e.getMessage());
            return 0;
        }
    }
}
