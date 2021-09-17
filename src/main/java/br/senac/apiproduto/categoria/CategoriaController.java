package br.senac.apiproduto.categoria;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
public class CategoriaController {

    private CategoriaService categoriaService;

    // mapeando uma requisição do tipo POST com o caminho /
    @PostMapping("/")

    // metodo para criar categoria
    // @valid - validar os @notnull @notempty definidos na classe
    public ResponseEntity<CategoriaRepresentation.Detalhe> criarCategotia(
           @Valid @RequestBody CategoriaRepresentation.CriarCategoria criarCategoria) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detalhe.from(this.categoriaService.salvarCategoria(criarCategoria)));
    }
    // mapeando uma requisição do tipo GET com o caminho /
    @GetMapping("/")

    public ResponseEntity<List<CategoriaRepresentation.Lista>> getAll(){
        // Filtro para trazer apenas a categoria com STATUS ATIVO
        // Como se fosse - where status = "ativo"
        BooleanExpression filtro = QCategoria.categoria.status.eq(Categoria.Status.ATIVO);

        return ResponseEntity.ok(CategoriaRepresentation.Lista
                .from(this.categoriaService.getAllCategoria(filtro)));
    }

    // mapeando uma requisição do tipo DELETE recebendo um ID como parametro
    @DeleteMapping("{id}")

    // metodo para deletar uma categoria passada o id por parametro
    public ResponseEntity deletaCategoria(@PathVariable("id") Long id){

        this.categoriaService.deletaCategoria(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
