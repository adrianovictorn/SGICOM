package mercado_municipal.mercado_municipal.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.pessoa.PessoaCreateDTO;
import mercado_municipal.mercado_municipal.dto.pessoa.PessoaEnderecoCreateDTO;
import mercado_municipal.mercado_municipal.dto.pessoa.PessoaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.pessoa.PessoaViewDTO;
import mercado_municipal.mercado_municipal.entity.Cidade;
import mercado_municipal.mercado_municipal.entity.Endereco;
import mercado_municipal.mercado_municipal.entity.Pessoa;
import mercado_municipal.mercado_municipal.repository.CidadeRepository;
import mercado_municipal.mercado_municipal.repository.EnderecoRepository;
import mercado_municipal.mercado_municipal.repository.PessoaRepository;

@Service
public class PessoaService {
    

    private final PessoaRepository pessoaRepository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    
    public PessoaService(PessoaRepository pessoaRepository, CidadeRepository cidadeRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public PessoaSimpleViewDTO cadastrarPessoa(PessoaCreateDTO dto){
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome(dto.nome());
        novaPessoa.setCpf(dto.cpf());
        novaPessoa.setDataDeNascimento(dto.dataDeNascimento());
        novaPessoa.setTelefone(dto.telefone());
        pessoaRepository.save(novaPessoa);

        return PessoaSimpleViewDTO.fromEntity(novaPessoa);
    }


    public PessoaViewDTO cadastrarCompleto(PessoaEnderecoCreateDTO pessoaEnderecoCreateDTO){
        Endereco novoEndereco = new Endereco();

        Cidade cidadeExistente = cidadeRepository.findById(pessoaEnderecoCreateDTO.enderecoCreateDTO().cidadeId()).orElseThrow(()-> new EntityNotFoundException("Endereço não encontrado !"));
        
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome(pessoaEnderecoCreateDTO.pessoaCreateDTO().nome());
        novaPessoa.setCpf(pessoaEnderecoCreateDTO.pessoaCreateDTO().cpf());
        novaPessoa.setDataDeNascimento(pessoaEnderecoCreateDTO.pessoaCreateDTO().dataDeNascimento());
        novaPessoa.setTelefone(pessoaEnderecoCreateDTO.pessoaCreateDTO().telefone());
        
        Pessoa pessoaSalva = pessoaRepository.save(novaPessoa);
        novoEndereco.setCidade(cidadeExistente);
        novoEndereco.setCep(pessoaEnderecoCreateDTO.enderecoCreateDTO().cep());
        novoEndereco.setNomeDoLocal(pessoaEnderecoCreateDTO.enderecoCreateDTO().nomeLocal());
        novoEndereco.setNumero(pessoaEnderecoCreateDTO.enderecoCreateDTO().numero());
        novoEndereco.setLogradouro(pessoaEnderecoCreateDTO.enderecoCreateDTO().logradouroEnum());
        novoEndereco.setPessoa(pessoaSalva);

        pessoaSalva.addEndereco(novoEndereco);
        enderecoRepository.save(novoEndereco);

        return PessoaViewDTO.fromEntity(pessoaSalva);
    }


    public Page<PessoaSimpleViewDTO> listarPessoas(int page, int size){
        Pageable pagina = PageRequest.of(page, size, Sort.by("nome").ascending());
        return pessoaRepository.findAll(pagina).map(PessoaSimpleViewDTO::fromEntity);
    }


    /// [BUSCAS]
    public PessoaSimpleViewDTO buscarPorId(Long id){
        Pessoa pessoaExistente = pessoaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada !"));
        return PessoaSimpleViewDTO.fromEntity(pessoaExistente);
    }
    
    public PessoaSimpleViewDTO buscarPorCPF(String cpf){
        Pessoa pessoaExistente =  pessoaRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("CPF inválido ou pessoa não encontrada !"));
        return PessoaSimpleViewDTO.fromEntity(pessoaExistente);
    }

    public Page<PessoaSimpleViewDTO> buscarPorNome(String nome, int size, int page){
        Pageable pagina = PageRequest.of(page, size, Sort.by("nome").ascending());

        if (nome == null || nome.isBlank()) {
            return pessoaRepository.findAll(pagina).map(PessoaSimpleViewDTO::fromEntity);
        }
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pagina).map(PessoaSimpleViewDTO::fromEntity);
    }

    public Page<PessoaSimpleViewDTO> buscarPorNomeouCPF(int size, int page, String termo){
        Pageable pagina = PageRequest.of(size, page, Sort.by("nome").ascending());

        return pessoaRepository.findByNomeContainingIgnoreCaseOrCpfContaining(termo, termo, pagina).map(PessoaSimpleViewDTO::fromEntity);
    }

    /// [DELETAR]
    public void deletarPessoa(Long id){
        pessoaRepository.deleteById(id);
    }




}
