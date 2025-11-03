package br.com.fiap.visionhive.dto.Procedure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcedureResponse {
    private String json;
    private String mensagem;

    private static final ObjectMapper mapper = new ObjectMapper();

    public String json() {
        return json;
    }

    public String mensagem() {
        return mensagem;
    }
}
