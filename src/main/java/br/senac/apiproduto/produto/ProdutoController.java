package br.senac.apiproduto.produto;

import br.senac.apiproduto.categoria.Categoria;
import br.senac.apiproduto.categoria.CategoriaService;
import br.senac.apiproduto.utilitarios.Paginacao;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {
    // PUT, GET 1 REGISTRO, GET TODOS, DELETE
    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final ProdutoRepository produtoRepository;

    @PostMapping("/criar")
    public ResponseEntity<ProdutoRepresentation.DetalhesProduto> cadastrarProduto(
            @Valid @RequestBody ProdutoRepresentation.CriarOuAtualizar criarOuAtualizar){

        Categoria categoria = this.categoriaService.getCategoria(criarOuAtualizar.getCategoria());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoRepresentation.DetalhesProduto.from(
                        this.produtoService.salvarProduto(criarOuAtualizar, categoria)));
    }
    // traz todos produtos
   @GetMapping("/todos")
    public ResponseEntity<Paginacao> buscarTodosProdutos(
            // Filtro com predicate utilizado para filtrar com qualquer atributo
           @QuerydslPredicate(root = Produto.class) Predicate filtroURI,
           @RequestParam(name = "tamanhoPagina", defaultValue = "10") int tamanhoPagina,
           @RequestParam(name = "paginaDesejada", defaultValue = "0")int pagina){

        // Criando filtro, validando se tem algum filtro informado na URI
       // Se for nulo filtra apenas por ativo se nao usa o ativo e o filtro da URI
       BooleanExpression filtro = Objects.isNull(filtroURI) ?
               QProduto.produto.status.eq(Produto.Status.ATIVO) :
               QProduto.produto.status.eq(Produto.Status.ATIVO).and(filtroURI);

       // paremetros para montar paginacao 
       Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);

       // passando para o repository, procurar pelo filtro e paginacao
       Page<Produto> listaProduto = this.produtoRepository.findAll(filtro, paginaDesejada);

       // monta estrutura de retorno da paginação no JSON
       Paginacao paginacao = Paginacao.builder()
               .paginaSelecionada(pagina)
               .tamanhoPagina(tamanhoPagina)
               .totalRegistros(listaProduto.getTotalElements())
               .proximaPagina(listaProduto.hasNext())
               .conteudo(ProdutoRepresentation.ListaDeProduto
                       .from(listaProduto.getContent()))
               .build();

       return ResponseEntity.ok(paginacao);
    }
    // traz um produto buscando pelo id que vem na URI
    @GetMapping("/um-produto/{id}")
    public ResponseEntity<ProdutoRepresentation.DetalhesProduto> buscarUmProduto(@PathVariable("id") Long id){
        return ResponseEntity.ok(ProdutoRepresentation.DetalhesProduto.from(
                this.produtoService.buscarUmProduto(id)));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ProdutoRepresentation.DetalhesProduto> atualizarProduto(@PathVariable("id") Long id,
        @Valid @RequestBody ProdutoRepresentation.CriarOuAtualizar criarOuAtualizar){

        Categoria categoria = this.categoriaService.getCategoria(criarOuAtualizar.getCategoria());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProdutoRepresentation.DetalhesProduto.from(
                        this.produtoService.atualizarProduto(id, criarOuAtualizar, categoria)));

    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarProduto(@PathVariable("id") Long id){
        this.produtoService.deletarProduto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
