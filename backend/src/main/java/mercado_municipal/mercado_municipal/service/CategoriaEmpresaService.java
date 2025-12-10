package mercado_municipal.mercado_municipal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.categoria.CategoriaCreateDTO;
import mercado_municipal.mercado_municipal.dto.categoria.CategoriaEmpresaListDTO;
import mercado_municipal.mercado_municipal.dto.categoria.CategoriaEmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.categoria.CategoriaUpdateDTO;
import mercado_municipal.mercado_municipal.entity.CategoriaEmpresa;
import mercado_municipal.mercado_municipal.repository.CategoriaEmpresaRepository;

@Service
public class CategoriaEmpresaService {
    

    private final CategoriaEmpresaRepository categoriaEmpresaRepository;

    public CategoriaEmpresaService(CategoriaEmpresaRepository categoriaEmpresaRepository){
        this.categoriaEmpresaRepository = categoriaEmpresaRepository;
    }

    public CategoriaEmpresaSimpleViewDTO cadastrarCategoriaEmpresa(CategoriaCreateDTO dto){
        CategoriaEmpresa categoriaEmpresa = new CategoriaEmpresa();
        categoriaEmpresa.setCategoria(dto.categoria());
        categoriaEmpresaRepository.save(categoriaEmpresa);
        return CategoriaEmpresaSimpleViewDTO.fromEntity(categoriaEmpresa);

    }

    public List<CategoriaEmpresaListDTO> listarCategoriaEmpresas(){
        List<CategoriaEmpresa> categorias = categoriaEmpresaRepository.findAll();
        List<CategoriaEmpresaListDTO> listaCategorias = categorias.stream().map(CategoriaEmpresaListDTO::fromEntity).toList();
        return listaCategorias;
    }

    public CategoriaEmpresaSimpleViewDTO atualizarCategoriaEmpresa(Long id, CategoriaUpdateDTO dto){
        CategoriaEmpresa categoriaEmpresaExistente = categoriaEmpresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria de Empresa, não cadastra ou não encontrada !"));
        categoriaEmpresaExistente.setCategoria(dto.categoria());
        categoriaEmpresaRepository.save(categoriaEmpresaExistente);

        return CategoriaEmpresaSimpleViewDTO.fromEntity(categoriaEmpresaExistente);
    }

    public void deletarCategoriaEmpresa(Long id){
        CategoriaEmpresa categoriaEmpresa = categoriaEmpresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria empresa não encontradad !"));
        categoriaEmpresaRepository.delete(categoriaEmpresa);
    }
}
