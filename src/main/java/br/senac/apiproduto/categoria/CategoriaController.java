package br.senac.apiproduto.categoria;

import br.senac.apiproduto.utilitarios.Paginacao;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private CategoriaRepository categoriaRepository;

    // mapeando uma requisição do tipo POST com o caminho /
    @PostMapping("/cadastrar")

    // metodo para criar categoria
    // @valid - validar os @notnull @notempty definidos na classe
    public ResponseEntity<CategoriaRepresentation.DetalheCategoria> criarCategotia(
           @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.DetalheCategoria.from(this.categoriaService.salvarCategoria(criarOuAtualizarCategoria)));
    }

    // mapeando requisição do tipo PUT (UPDATE) passando um ID
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CategoriaRepresentation.DetalheCategoria> atualizaCategoria(@PathVariable("id") Long id,
                @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CategoriaRepresentation.DetalheCategoria.from(
                        this.categoriaService.atualizar(id, criarOuAtualizarCategoria)));
    }

    // mapeando uma requisição do tipo GET com o caminho /
    @GetMapping("/todas-categorias/")

    public ResponseEntity<Paginacao> getAll(
            // parametro passado na URI similar a: ...?filtro=teste
            // esse valor informado na URI será guardado na variavel FILTRO
            @RequestParam(name = "filtro", required = false, defaultValue = "") String filtroURI,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") Integer paginaSelecionada,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10") Integer tamanhoPagina){

        // verifica se o filtro informado na URI for VAZIO vai fazer o filtro apenas com o STATUS
        // se nao for vazio, vai fazer o filtro com o STATUS "E" a DESCRICAO = o filtro da URI
        BooleanExpression filtro = Strings.isEmpty(filtroURI) ? QCategoria.categoria.status.eq(Categoria.Status.ATIVO):
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO)
                        .and(QCategoria.categoria.descricao.containsIgnoreCase(filtroURI));

        // informando a pagina e o tamanho desejado
        Pageable paginaDesejada = PageRequest.of(paginaSelecionada, tamanhoPagina);

        // faz a consulta informando o filtro e a pagina desejada
        Page<Categoria> categoriaLista = this.categoriaRepository.findAll(filtro, paginaDesejada);

        // montando uma paginação propria customizada, para nao retornar todas as informações que o Spring retorna
        Paginacao paginacao = Paginacao.builder()
                .conteudo(CategoriaRepresentation.Lista
                        .from(categoriaLista.getContent()))
                .paginaSelecionada(paginaSelecionada)
                .proximaPagina(categoriaLista.hasNext())
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(categoriaLista.getTotalElements())
                .build();

        return ResponseEntity.ok(paginacao);
    }
    // mapeando requisição GET passando ID por variavel
    @GetMapping("/uma-categoria/{id}")
    public ResponseEntity<CategoriaRepresentation.DetalheCategoria> getCategoriaId(@PathVariable("id") Long id){
        return ResponseEntity.ok(CategoriaRepresentation.DetalheCategoria.from(
                this.categoriaService.getCategoria(id)));
    }

    // mapeando uma requisição do tipo DELETE recebendo um ID como parametro
    @DeleteMapping("/apagar/{id}")
    // metodo para deletar uma categoria passada o id por parametro
    public ResponseEntity deletaCategoria(@PathVariable("id") Long id){

        this.categoriaService.deletaCategoria(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
