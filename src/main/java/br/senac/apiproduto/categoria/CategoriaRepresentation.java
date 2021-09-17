package br.senac.apiproduto.categoria;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface CategoriaRepresentation {

    @Data
    @Getter
    @Setter
    class CriarCategoria {

        @NotNull(message = "O campo descrição não pode ser nulo")
        @Size(max = 30, min = 1, message = "A descrição deve conter de 1 a 30 caracteres")
        private String descricao;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe {
        private Long id;
        private String descricao;
        private Categoria.Status status;

        public static Detalhe from(Categoria categoria) {
            return Detalhe.builder()
                    .id(categoria.getId())
                    .descricao(categoria.getDescricao())
                    .status(categoria.getStatus())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista {
        private Long id;
        private String descricao;

        private static Lista from(Categoria categoria){ // cria objeto tipo lista
            return Lista.builder()
                    .id(categoria.getId())
                    .descricao(categoria.getDescricao())
                    .build();
        }

        public static List<Lista> from(List<Categoria> categoria){ // recebe a lista de cima como parametro e retorna a lista
            return categoria.stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
