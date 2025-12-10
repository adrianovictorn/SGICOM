package mercado_municipal.mercado_municipal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaCreateDTO;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaListDTO;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaUpdateDTO;
import mercado_municipal.mercado_municipal.entity.TipoEmpresa;
import mercado_municipal.mercado_municipal.repository.TipoEmpresaRepository;

@Service
public class TipoEmpresaService {
    
    private final TipoEmpresaRepository tipoEmpresaRepository;

    public TipoEmpresaService(TipoEmpresaRepository tipoEmpresaRepository){
        this.tipoEmpresaRepository = tipoEmpresaRepository;
    }

    public TipoEmpresaSimpleViewDTO cadastrarTipoEmpresa(TipoEmpresaCreateDTO dto){
        TipoEmpresa tipoEmpresa = new TipoEmpresa();
        tipoEmpresa.setTipo(dto.tipo());
        
        tipoEmpresaRepository.save(tipoEmpresa);

        return TipoEmpresaSimpleViewDTO.fromEntity(tipoEmpresa);
    }

    public List<TipoEmpresaListDTO> listarTipoEmpresa(){
        List<TipoEmpresa> tipoEmpresas = tipoEmpresaRepository.findAll();
        List<TipoEmpresaListDTO> listas = tipoEmpresas.stream().map(TipoEmpresaListDTO::fromEntity).toList();
        return listas;
    }

    public TipoEmpresaSimpleViewDTO atualizarTipoEmpresa(Long id, TipoEmpresaUpdateDTO dto){
        TipoEmpresa tipoEmpresa = tipoEmpresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de Empresa não encontrado !"));
        tipoEmpresa.setTipo(dto.tipo());

        tipoEmpresaRepository.save(tipoEmpresa);

        return TipoEmpresaSimpleViewDTO.fromEntity(tipoEmpresa);
    }

    public void deletarTipoEmpresa(Long id){
        TipoEmpresa tipoEmpresaExistente = tipoEmpresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de Empresa, não encontrado ou não cadastrado !"));
        tipoEmpresaRepository.delete(tipoEmpresaExistente);
    }
}
