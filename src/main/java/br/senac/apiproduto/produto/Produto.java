package br.senac.apiproduto.produto;

import br.senac.apiproduto.categoria.Categoria;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "PRODUTOS")

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 1, max = 80, message = "O nome deve conter entre 1 e 80 caracteres")
    @Column(name = "nm_produto")
    private String nome;

    @NotNull(message = "A descrição não pode ser nula")
    @Size(min = 1, max = 255, message = "A descrição deve conter entre 1 e 500 caracteres")
    @Column(name = "ds_produto")
    private String descricao;

    @Column(name = "complemento")
    private String complemento;

    @NotNull(message = "O valor do produto não pode ser nulo")
    @Column(name = "vl_produto")
    private Double valor;

    @NotNull(message = "A unidade de medida do produto não pode ser nula")
    @Column(name = "unidadeMedida")
    private UnidadeMedida unidadeMedida;

    @NotNull(message = "A quantidade do produto não pode ser nula")
    @Column(name = "qt_produto")
    private Double quantidade;

    @NotNull(message = "O fabricante do produto não pode ser nula")
    @Size(min = 1, max = 255, message = "O fabricante deve conter entre 1 e 255 caracteres")
    @Column(name = "ds_fabricante")
    private String fabricante;

    @Column(name = "ds_fornecedor")
    private String fornecedor;

    @NotNull(message = "O status não pode ser nulo")
    @Column(name = "id_status")
    private Status status;

    @NotNull(message = "A categoria é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

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

    public enum Status{
        INATIVO,
        ATIVO
    }
}
