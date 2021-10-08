// clase para implementar paginação
package br.senac.apiproduto.utilitarios;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class Paginacao {
    // quantidade de registros da pagina
    private int tamanhoPagina;
    // qual pagina esta selecionada
    private int paginaSelecionada;
    private Boolean proximaPagina;
    private Long totalRegistros;
    // ? = aceita qualquer tipo de dado na lista
    private List<?> conteudo;

}
