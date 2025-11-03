package br.com.fiap.visionhive.repository;

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
        atualizarSituacaoCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_MOTOCICLETAS")
                .withProcedureName("PRC_ATUALIZAR_SITUACAO")
                .declareParameters(
                        new SqlParameter("p_moto_id", Types.NUMERIC),
                        new SqlParameter("p_nova_situacao", Types.VARCHAR),
                        new SqlOutParameter("p_json", Types.VARCHAR),
                        new SqlOutParameter("p_mensagem", Types.VARCHAR)
                );

        associarPatioCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_MOTOCICLETAS")
                .withProcedureName("PRC_ASSOCIAR_PATIO")
                .declareParameters(
                        new SqlParameter("p_moto_id", Types.NUMERIC),
                        new SqlParameter("p_patio_id", Types.NUMERIC),
                        new SqlOutParameter("p_json", Types.VARCHAR),
                        new SqlOutParameter("p_mensagem", Types.VARCHAR)
                );

        contarPatiosCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_MOTOCICLETAS")
                .withFunctionName("FN_CONTAR_PATIOS_FILIAL")
                .declareParameters(new SqlOutParameter("return", Types.NUMERIC));
    }

    public String atualizarSituacao(Long motoId, String novaSituacao) {
        Map<String, Object> out = atualizarSituacaoCall.execute(motoId, novaSituacao);
        String json = (String) out.get("p_json");
        String msg = (String) out.get("p_mensagem");
        return msg.contains("\"erro\"") ? msg : json;
    }

    public String associarPatio(Long motoId, Long patioId) {
        Map<String, Object> out = associarPatioCall.execute(motoId, patioId);
        String json = (String) out.get("p_json");
        String msg = (String) out.get("p_mensagem");
        return msg.contains("\"erro\"") ? msg : json;
    }

    public int contarPatiosPorFilial(Long branchId) {
        try {
            BigDecimal result = contarPatiosCall.executeFunction(BigDecimal.class, branchId);
            return result != null ? result.intValue() : 0;
        } catch (Exception e) {
            System.err.println("Erro na function: " + e.getMessage());
            return 0;
        }
    }
}