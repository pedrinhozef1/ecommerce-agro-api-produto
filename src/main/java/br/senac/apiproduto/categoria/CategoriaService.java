package br.senac.apiproduto.categoria;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoriaService {


    private CategoriaRepository categoriaRepository;

    public Categoria salvar(CategoriaRepresentation.CreateCategoria createCategoria) {

        return this.categoriaRepository.save(Categoria.builder()
                .descricao(createCategoria.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());

    }
}
