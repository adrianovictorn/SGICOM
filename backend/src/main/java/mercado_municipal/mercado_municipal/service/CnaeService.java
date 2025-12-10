package mercado_municipal.mercado_municipal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeCreateDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeListDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeUpdateDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeViewDTO;
import mercado_municipal.mercado_municipal.entity.Cnae;
import mercado_municipal.mercado_municipal.repository.CnaeRepository;

@Service
public class CnaeService {
    
    private final CnaeRepository cnaeRepository;

    public CnaeService(CnaeRepository cnaeRepository) {
        this.cnaeRepository = cnaeRepository;
    }


    public CnaeSimpleViewDTO criarCnae(CnaeCreateDTO dto){
        Cnae novoCnae = new Cnae();
        novoCnae.setCodigo(dto.codigo());
        novoCnae.setDescricao(dto.descricao());
        cnaeRepository.save(novoCnae);
        return CnaeSimpleViewDTO.fromEntity(novoCnae);
    }

    public List<CnaeListDTO> listarCnaes(){
        List<Cnae> cnaes = cnaeRepository.findAll();
        List<CnaeListDTO> listaCnaesDTO  = cnaes.stream().map(CnaeListDTO::fromEntity).toList();
        return listaCnaesDTO;
    }

    public Page<CnaeSimpleViewDTO> buscarAutomizada(
        String termo,
        int size,
        int page
    ){

        Pageable pageable = PageRequest.of(page, size);

        if (termo != null && !termo.isBlank()) {
            return cnaeRepository.findByCodigoContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termo, termo, pageable).map(CnaeSimpleViewDTO::fromEntity); 
        }
        return cnaeRepository.findAll(pageable).map(CnaeSimpleViewDTO::fromEntity);
    } 
    public CnaeViewDTO atualizarCnae(CnaeUpdateDTO dto, Long id){

        Cnae cnaeExistente = cnaeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CNAE não encontrado no sistema !"));
        cnaeExistente.setCodigo(dto.codigo());
        cnaeExistente.setDescricao(dto.descricao());

        return CnaeViewDTO.fromEntity(cnaeExistente);
    }

    public void deletarCnae(Long id){
        Cnae cnaeExistente = cnaeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CNAE não encontrado no sistema !"));
        cnaeRepository.delete(cnaeExistente);
    }



}
