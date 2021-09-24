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

    @PostMapping("/criar-produto")
    public ResponseEntity<ProdutoRepresentation.DetalhesProduto> cadastrarProduto(
            @Valid @RequestBody ProdutoRepresentation.CriarOuAtualizar criarOuAtualizar){

        Categoria categoria = this.categoriaService.getCategoria(criarOuAtualizar.getCategoria());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoRepresentation.DetalhesProduto.from(
                        this.produtoService.salvarProduto(criarOuAtualizar, categoria)));
    }

   @GetMapping("/todos-produtos")
    public ResponseEntity<List<ProdutoRepresentation.ListaDeProduto>> todosProdutos(){
       BooleanExpression filtro = QProduto.produto.status.eq(Produto.Status.ATIVO);

       return ResponseEntity.ok(ProdutoRepresentation.ListaDeProduto.from(
               this.produtoService.buscarTodosProdutos(filtro)));
    }


}
