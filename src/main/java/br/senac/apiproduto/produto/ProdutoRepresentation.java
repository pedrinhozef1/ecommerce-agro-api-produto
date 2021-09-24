package br.senac.apiproduto.produto;

import br.senac.apiproduto.categoria.CategoriaRepresentation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

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
        private Double valor;

        @NotNull(message = "A unidade de medida do produto não pode ser nula")
        private Produto.UnidadeMedida unidadeMedida;

        @NotNull(message = "A quantidade do produto não pode ser nula")
        private Double quantidade;

        @NotNull(message = "O fabricante do produto não pode ser nula")
        @Size(min = 1, max = 255, message = "O fabricante deve conter entre 1 e 255 caracteres")
        private String fabricante;

        private String fornecedor;

        @NotNull(message = "A categoria é obrigatória")
        private Long categoria;

        public enum UnidadeMedida {
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

    @Data
    @Getter
    @Setter
    @Builder
    class DetalhesProduto {
        private Long id;
        private String nome;
        private String descricao;
        private String complemento;
        private Double valor;
        private Produto.UnidadeMedida unidadeMedida;
        private Double quantidade;
        private String fabricante;
        private String fornecedor;
        private Produto.Status status;
        private CategoriaRepresentation.DetalheCategoria categoria;

        // recebe um objeto Produto como parametro
        public static DetalhesProduto from(Produto produto){
            return DetalhesProduto.builder()
                    .id(produto.getId())
                    .nome(produto.getNome())
                    .descricao(produto.getDescricao())
                    .complemento(produto.getComplemento())
                    .valor(produto.getValor())
                    .unidadeMedida(produto.getUnidadeMedida())
                    .quantidade(produto.getQuantidade())
                    .fabricante(produto.getFabricante())
                    .fornecedor(produto.getFornecedor())
                    .status(produto.getStatus())
                    .categoria(CategoriaRepresentation.DetalheCategoria.from(produto.getCategoria()))
                    .build();
        }
    }

    @Data
    @Setter
    @Getter
    @Builder
    class ListaDeProduto{
        private Long id;
        private String nome;
        private String descricao;
        private String complemento;
        private Double valor;
        private Produto.UnidadeMedida unidadeMedida;
        private Double quantidade;
        private String fabricante;
        private String fornecedor;
        private Produto.Status status;
        private CategoriaRepresentation.DetalheCategoria categoria;

        private static ListaDeProduto from(Produto produto){
            return ListaDeProduto.builder()
                    .id(produto.getId())
                    .nome(produto.getNome())
                    .descricao(produto.getDescricao())
                    .complemento(produto.getComplemento())
                    .valor(produto.getValor())
                    .unidadeMedida(produto.getUnidadeMedida())
                    .quantidade(produto.getQuantidade())
                    .fabricante(produto.getFabricante())
                    .fornecedor(produto.getFornecedor())
                    // busca a categoria do produto
                    .categoria(CategoriaRepresentation.DetalheCategoria.from(produto.getCategoria()))
                    .build();
        }

        // recebe a lista de cima como parametro
        public static List<ListaDeProduto> from(List<Produto> produto){
            return produto.stream()
                    // .map -similar a um for ou for each, percorre os itens de uma determinada lista
                    //       Nesse caso, percorre os itens da ListaDeProduto criada em cima
                    //.collect - Pega todos os itens e transforma em uma lista para mostrar na tela
                    .map(ListaDeProduto::from)
                    .collect(Collectors.toList());
        }


    }
}
