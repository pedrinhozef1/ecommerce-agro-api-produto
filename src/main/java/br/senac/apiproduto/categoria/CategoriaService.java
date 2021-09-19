package br.senac.apiproduto.categoria;

import br.senac.apiproduto.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria) {

        return this.categoriaRepository.save(Categoria.builder()
                .descricao(criarOuAtualizarCategoria.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());
    }

    public List<Categoria> getAllCategoria(Predicate filtro){
        return this.categoriaRepository.findAll(filtro);
    }

    public void deletaCategoria(Long id) {
        // chama o metodo getCategoria que possui vai fazer o "select" com os filtros
        Categoria categoria = this.getCategoria(id);

        // seta status para inativo
        categoria.setStatus(Categoria.Status.INATIVO);

        // salva o objeto com o status alterado para inativo
        this.categoriaRepository.save(categoria);
    }
    // metodo para trazer a categoria pelo id no parametro
    public Categoria getCategoria(Long id){ // id informado na URI
        BooleanExpression filtro = // where id = (id) and status = "ativo"
                QCategoria.categoria.id.eq(id) // traz categoria com o ID que veio por parametro
                        .and(QCategoria.categoria.status.eq(Categoria.Status.ATIVO)); // e com o  Status "Ativo"

        return this.categoriaRepository.findOne(filtro)
                // se nao, dispara um erro
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));
    }

    public Categoria atualizar(Long id, CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria) {
        Categoria categoria = this.getCategoria(id);
        categoria.setDescricao(criarOuAtualizarCategoria.getDescricao());
        return this.categoriaRepository.save(categoria);
    }
}
