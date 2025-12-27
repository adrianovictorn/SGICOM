package mercado_municipal.mercado_municipal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.feirante.FeiranteCreateDTO;
import mercado_municipal.mercado_municipal.dto.feirante.FeiranteSimpleViewDTO;
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
    private final MatriculaService matriculaService;

    

    public FeiranteService(FeiranteRepository feiranteRepository, CategoriaEmpresaRepository categoriaRepository,
            PessoaRepository pessoaRepository, CidadeRepository cidadeRepository, EnderecoRepository enderecoRepository, MatriculaService matriculaService) {
        this.feiranteRepository = feiranteRepository;
        this.categoriaRepository = categoriaRepository;
        this.pessoaRepository = pessoaRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoRepository = enderecoRepository;
        this.matriculaService = matriculaService;
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
        novoFeirante.setMatricula(matriculaService.gerar());
        novoFeirante.setBanca(feiranteCreateDTO.banca());
        novoFeirante.setCategoriaEmpresa(categoria);
        novoFeirante.setBox(feiranteCreateDTO.box());
        novoFeirante.setPessoa(pessoaSalva);

        Feirante feiranteSalva = feiranteRepository.save(novoFeirante);
        pessoaSalva.addEndereco(novoEndereco);

        return FeiranteViewDTO.fromEntity(feiranteSalva);
    }

    public Page<FeiranteSimpleViewDTO> listarFeirantes(int page, int size){
        Pageable pagina = PageRequest.of(page, size, Sort.by("Matricula"));
        return feiranteRepository.findAll(pagina).map(FeiranteSimpleViewDTO::fromEntity);
    }

    public Page<FeiranteSimpleViewDTO> buscarPorMatricula(String matricula, int page, int size){
        Pageable pagina = PageRequest.of(page, size, Sort.by("Matricula"));

        if (matricula == null || matricula.isBlank()) {
            return feiranteRepository.findAll(pagina).map(FeiranteSimpleViewDTO::fromEntity);
        }
        return feiranteRepository.findByMatriculaContainingIgnoreCase(pagina, matricula).map(FeiranteSimpleViewDTO::fromEntity);
    }

    public Page<FeiranteSimpleViewDTO> buscarBoxPor(Boolean box, int page, int size){
        Pageable pagina = PageRequest.of(page, size, Sort.by("Matricula"));
        if (box == true) {
            return feiranteRepository.findByBox(pagina, true).map(FeiranteSimpleViewDTO::fromEntity);
        } else if (box == false) {
            return feiranteRepository.findByBox(pagina, false).map(FeiranteSimpleViewDTO::fromEntity);

        } else{
            return feiranteRepository.findAll(pagina).map(FeiranteSimpleViewDTO::fromEntity);
        }
    }

    public Page<FeiranteSimpleViewDTO> buscarPorBanca(int page, int size, String banca){
        Pageable pagina = PageRequest.of(page, size, Sort.by("Matricula"));

        if (banca == null || banca.isBlank()) {
            return feiranteRepository.findAll(pagina).map(FeiranteSimpleViewDTO::fromEntity);
        }

        return feiranteRepository.findByBancaContainingIgnoreCase(pagina, banca).map(FeiranteSimpleViewDTO::fromEntity);
    }

    public Page<FeiranteSimpleViewDTO> buscarMultiplosCampos(String termo, int page, int size){
        Pageable pagina = PageRequest.of(page, size, Sort.by("matricula").ascending());

        if (termo != null && !termo.isBlank()) {
           return feiranteRepository.findByMatriculaContainingIgnoreCaseOrCategoriaEmpresa_CategoriaOrBancaContainingIgnoreCase(termo, termo, termo, pagina).map(FeiranteSimpleViewDTO::fromEntity);
        }

        return feiranteRepository.findAll(pagina).map(FeiranteSimpleViewDTO::fromEntity);
    }
}
