package br.senac.apiproduto.categoria;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
