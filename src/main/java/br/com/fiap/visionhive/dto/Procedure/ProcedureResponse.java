package br.com.fiap.visionhive.dto.Procedure;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcedureResponse {
    private String json;
    private String mensagem;

    public String json() {
        return json;
    }

    public String mensagem() {
        return mensagem;
    }
}
