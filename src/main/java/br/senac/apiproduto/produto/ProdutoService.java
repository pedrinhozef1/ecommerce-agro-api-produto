package br.senac.apiproduto.produto;

import br.senac.apiproduto.categoria.Categoria;
import br.senac.apiproduto.categoria.CategoriaService;
import br.senac.apiproduto.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public Produto salvarProduto(ProdutoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                 Categoria categoria){

        Produto produto = Produto.builder() // .builder instancia um objeto novo, que nao ta no banco
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .complemento(Strings.isEmpty(criarOuAtualizar.getComplemento()) ? "" : criarOuAtualizar.getComplemento())
                .fabricante(criarOuAtualizar.getFabricante())
                .fornecedor(Strings.isEmpty(criarOuAtualizar.getFornecedor()) ? "" : criarOuAtualizar.getFornecedor())
                .quantidade(criarOuAtualizar.getQuantidade())
                .valor(criarOuAtualizar.getValor())
                .unidadeMedida(criarOuAtualizar.getUnidadeMedida())
                .categoria(categoria)
                .status(Produto.Status.ATIVO)
                .build();
        return this.produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, ProdutoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                    Categoria categoria){
        Produto produtoAntigo = this.buscarUmProduto(id);

        //toBuilder carrega o produto que ja existe no banco, sobrescrevendo e atualizando os campos que foram alterados
        Produto produtoAtualizado = produtoAntigo.toBuilder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .complemento(Strings.isEmpty(criarOuAtualizar.getComplemento()) ? "" : criarOuAtualizar.getComplemento())
                .fabricante(criarOuAtualizar.getFabricante())
                .fornecedor(Strings.isEmpty(criarOuAtualizar.getFornecedor()) ? "" : criarOuAtualizar.getFornecedor())
                .quantidade(criarOuAtualizar.getQuantidade())
                .valor(criarOuAtualizar.getValor())
                .unidadeMedida(criarOuAtualizar.getUnidadeMedida())
                .categoria(categoria)
                .status(Produto.Status.ATIVO)
                .build();

        return this.produtoRepository.save(produtoAtualizado);
    }

    public List<Produto> buscarTodosProdutos(Predicate filtro){
        return this.produtoRepository.findAll(filtro);
    }

    public Produto buscarUmProduto(Long id){
        BooleanExpression filtro = QProduto.produto.id.eq(id)
                .and(QProduto.produto.status.eq(Produto.Status.ATIVO));
        return this.produtoRepository.findOne(filtro)
                .orElseThrow(()-> new NotFoundException("Produto não encontrado."));
    }

    public void deletarProduto(Long id){
        // chama o metodo buscarUmProduto pra ver se existe um produto com o id informado
        Produto produto = this.buscarUmProduto(id);
        // seta o STATUS desse produto pra INATIVO
        produto.setStatus(Produto.Status.INATIVO);
        // salva o objetivo PRODUTO com o status atualizado
        this.produtoRepository.save(produto);
    }
}
