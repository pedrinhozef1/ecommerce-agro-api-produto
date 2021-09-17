package br.senac.apiproduto.categoria;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(CategoriaRepresentation.CriarCategoria criarCategoria) {

        return this.categoriaRepository.save(Categoria.builder()
                .descricao(criarCategoria.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());
    }

    public List<Categoria> getAllCategoria(Predicate filtro){
        return this.categoriaRepository.findAll(filtro);
    }

    public void deletaCategoria(Long id) {
        // buscando a categoria pelo ID no parametro
        Categoria categoria = this.categoriaRepository.findById(id).get();

        // seta status para inativo
        categoria.setStatus(Categoria.Status.INATIVO);

        // salva o objeto com o status alterado para inativo
        this.categoriaRepository.save(categoria);
    }
}
