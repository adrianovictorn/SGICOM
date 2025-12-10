package mercado_municipal.mercado_municipal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.cidade.CidadeCreateDTO;
import mercado_municipal.mercado_municipal.dto.cidade.CidadeListDTO;
import mercado_municipal.mercado_municipal.dto.cidade.CidadeSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.cidade.CidadeUpdateDTO;
import mercado_municipal.mercado_municipal.entity.Cidade;
import mercado_municipal.mercado_municipal.repository.CidadeRepository;

@Service
public class CidadeService {
    
    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository){
        this.cidadeRepository = cidadeRepository;
    }

    public CidadeSimpleViewDTO cadastrarCidade(CidadeCreateDTO dto){
        Cidade novaCidade = new Cidade();
        novaCidade.setCodigoIbge(dto.codigoIbge());
        novaCidade.setNome(dto.nome());

        cidadeRepository.save(novaCidade);

        return CidadeSimpleViewDTO.fromEntity(novaCidade);
    }

    public List<CidadeListDTO> listarCidades(){
        List<Cidade> listaDeCidades = cidadeRepository.findAll();
        List<CidadeListDTO> cidadeViews = listaDeCidades.stream().map(CidadeListDTO::fromEntity).toList();

        return cidadeViews;
    }

    public CidadeSimpleViewDTO atualizarCidade(Long id, CidadeUpdateDTO cidadeUpdateDTO){
        Cidade cidadeExistente = cidadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cidade não cadastrada, ou não encontrada!"));
        cidadeExistente.setCodigoIbge(cidadeUpdateDTO.codigoIBGE());
        cidadeExistente.setNome(cidadeUpdateDTO.nome());

        cidadeRepository.save(cidadeExistente);

        return CidadeSimpleViewDTO.fromEntity(cidadeExistente);
    }

    public void deletarCidade(Long id){
        Cidade cidadeExistente = cidadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cidade não cadastrada, ou não encontrada !"));
        cidadeRepository.delete(cidadeExistente);
    }
}
