package br.senac.apiproduto.produto;

import br.senac.apiproduto.categoria.Categoria;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ProdutoRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{

        @NotNull(message = "O nome não pode ser nulo")
        @Size(min = 1, max = 80, message = "O nome deve conter entre 1 e 80 caracteres")
        private String nome;

        @NotNull(message = "A descrição não pode ser nula")
        @Size(min = 1, max = 255, message = "A descrição deve conter entre 1 e 500 caracteres")
        private String descricao;

        private String complemento;

        @NotNull(message = "O valor do produto não pode ser nulo")
        @NotEmpty
        private Double valor;

        @NotNull(message = "A unidade de medida do produto não pode ser nula")
        @NotEmpty
        private Produto.UnidadeMedida unidadeMedida;

        @NotNull(message = "A quantidade do produto não pode ser nula")
        @NotEmpty(message = "A quantidade do produto não pode ser vazia")
        private Double quantidade;

        @NotNull(message = "O fabricante do produto não pode ser nula")
        @Size(min = 1, max = 255, message = "O fabricante deve conter entre 1 e 255 caracteres")
        private String fabricante;

        private String fornecedor;

        @NotNull(message = "O status não pode ser nulo")
        @NotEmpty(message = "O status não pode ser vazio")
        private Produto.Status status;

        @NotNull(message = "A categoria é obrigatória")
        private Long categoria;

        public enum UnidadeMedida{
            UN,
            KG,
            ML,
            TN,
            MT,
            MT2,
            MT3,
            LT
        }
    }

}
