package br.senac.apiproduto.produto;

import br.senac.apiproduto.categoria.Categoria;
import br.senac.apiproduto.categoria.CategoriaService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {
    // PUT, GET 1 REGISTRO, GET TODOS, DELETE
    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;

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
    public ResponseEntity<List<ProdutoRepresentation.ListaDeProduto>> buscarTodosProdutos(){
        // fltro para buscar todos produtos que estejam com status ATIVO
       BooleanExpression filtro = QProduto.produto.status.eq(Produto.Status.ATIVO);

       return ResponseEntity.ok(ProdutoRepresentation.ListaDeProduto.from(
               // chama o metodo buscarTodosProdutos do service passando o FILTRO como parametro
               this.produtoService.buscarTodosProdutos(filtro)));
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
