package br.senac.apiproduto.produto;

import br.senac.apiproduto.categoria.Categoria;
import br.senac.apiproduto.categoria.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

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
}
