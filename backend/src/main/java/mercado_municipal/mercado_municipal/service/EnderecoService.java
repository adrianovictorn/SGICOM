package mercado_municipal.mercado_municipal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoCreateDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoListDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoUpdateDTO;
import mercado_municipal.mercado_municipal.entity.Endereco;
import mercado_municipal.mercado_municipal.repository.CidadeRepository;
import mercado_municipal.mercado_municipal.repository.EnderecoRepository;

@Service
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository){
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
    }

    public EnderecoSimpleViewDTO cadastrarEndereco(EnderecoCreateDTO dto){
        
        var cidade = cidadeRepository.findById(dto.cidadeId()).orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada !"));

        Endereco novoEndereco = new Endereco();
        novoEndereco.setNomeDoLocal(dto.nomeLocal());
        novoEndereco.setLogradouro(dto.logradouroEnum());
        novoEndereco.setPontoReferencia(dto.pontoReferencia());
        novoEndereco.setCep(dto.cep());
        novoEndereco.setNumero(dto.numero());
        novoEndereco.setCidade(cidade);

        Endereco enderecoSalvo = enderecoRepository.save(novoEndereco);
        return EnderecoSimpleViewDTO.fromEntity(enderecoSalvo);
    }

    public List<EnderecoListDTO> listarEnderecos(){
        List<Endereco> enderecoLista = enderecoRepository.findAll();
        List<EnderecoListDTO> listaDTOs = enderecoLista.stream().map(EnderecoListDTO::fromEntity).toList();
        
        return listaDTOs;
    }

    public EnderecoSimpleViewDTO buscarPorId(Long id){
        Endereco enderecoExistente = enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado !"));
        return EnderecoSimpleViewDTO.fromEntity(enderecoExistente);
    }

    public EnderecoSimpleViewDTO atualizarEndereco(EnderecoUpdateDTO dto, Long id){
        
        Endereco enderecoExistente = enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Endereço não existente ou não encontrado!"));
        enderecoExistente.setCep(dto.cep());
        enderecoExistente.setLogradouro(dto.logradouroEnum());
        enderecoExistente.setNomeDoLocal(dto.nomeLocal());
        enderecoExistente.setNumero(dto.numero());
        enderecoExistente.setPontoReferencia(dto.pontoReferencia());

        enderecoRepository.save(enderecoExistente);

        return EnderecoSimpleViewDTO.fromEntity(enderecoExistente);
    }

    public void deletarEndereco(Long id){
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado !"));
        enderecoRepository.delete(endereco);
    }
}
