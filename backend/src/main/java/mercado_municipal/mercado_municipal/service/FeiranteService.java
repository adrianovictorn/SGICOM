package mercado_municipal.mercado_municipal.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.feirante.FeiranteCreateDTO;
import mercado_municipal.mercado_municipal.dto.feirante.FeiranteViewDTO;
import mercado_municipal.mercado_municipal.entity.CategoriaEmpresa;
import mercado_municipal.mercado_municipal.entity.Cidade;
import mercado_municipal.mercado_municipal.entity.Endereco;
import mercado_municipal.mercado_municipal.entity.Feirante;
import mercado_municipal.mercado_municipal.entity.Pessoa;
import mercado_municipal.mercado_municipal.repository.CategoriaEmpresaRepository;
import mercado_municipal.mercado_municipal.repository.CidadeRepository;
import mercado_municipal.mercado_municipal.repository.EnderecoRepository;
import mercado_municipal.mercado_municipal.repository.FeiranteRepository;
import mercado_municipal.mercado_municipal.repository.PessoaRepository;

@Service
public class FeiranteService {
    

    private final FeiranteRepository feiranteRepository;
    private final CategoriaEmpresaRepository categoriaRepository;
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;

    

    public FeiranteService(FeiranteRepository feiranteRepository, CategoriaEmpresaRepository categoriaRepository,
            PessoaRepository pessoaRepository, CidadeRepository cidadeRepository, EnderecoRepository enderecoRepository) {
        this.feiranteRepository = feiranteRepository;
        this.categoriaRepository = categoriaRepository;
        this.pessoaRepository = pessoaRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoRepository = enderecoRepository;
    }



    public FeiranteViewDTO cadastrarFeirante (FeiranteCreateDTO feiranteCreateDTO){
        
        CategoriaEmpresa categoria = categoriaRepository.findById(feiranteCreateDTO.categoriaId()).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada !"));
        Cidade cidade = cidadeRepository.findById(feiranteCreateDTO.pessoaEndereco().enderecoCreateDTO().cidadeId()).orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada !"));

        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCpf(feiranteCreateDTO.pessoaEndereco().pessoaCreateDTO().cpf());
        novaPessoa.setDataDeNascimento(feiranteCreateDTO.pessoaEndereco().pessoaCreateDTO().dataDeNascimento());
        novaPessoa.setNome(feiranteCreateDTO.pessoaEndereco().pessoaCreateDTO().nome());
        novaPessoa.setTelefone(feiranteCreateDTO.pessoaEndereco().pessoaCreateDTO().telefone());
        
        Pessoa pessoaSalva = pessoaRepository.save(novaPessoa);

        Endereco novoEndereco = new Endereco();
        novoEndereco.setCep(feiranteCreateDTO.pessoaEndereco().enderecoCreateDTO().cep());
        novoEndereco.setNomeDoLocal(feiranteCreateDTO.pessoaEndereco().enderecoCreateDTO().nomeLocal());
        novoEndereco.setNumero(feiranteCreateDTO.pessoaEndereco().enderecoCreateDTO().numero());
        novoEndereco.setPontoReferencia(feiranteCreateDTO.pessoaEndereco().enderecoCreateDTO().pontoReferencia());
        novoEndereco.setLogradouro(feiranteCreateDTO.pessoaEndereco().enderecoCreateDTO().logradouroEnum());
        novoEndereco.setCidade(cidade);
        novoEndereco.setPessoa(novaPessoa);

        enderecoRepository.save(novoEndereco);

        Feirante novoFeirante = new Feirante();
        novoFeirante.setMatricula(feiranteCreateDTO.matricula());
        novoFeirante.setBanca(feiranteCreateDTO.banca());
        novoFeirante.setCategoriaEmpresa(categoria);
        novoFeirante.setBox(feiranteCreateDTO.box());
        novoFeirante.setPessoa(pessoaSalva);

        Feirante feiranteSalva = feiranteRepository.save(novoFeirante);
        pessoaSalva.addEndereco(novoEndereco);

        return FeiranteViewDTO.fromEntity(feiranteSalva);
    }
}
