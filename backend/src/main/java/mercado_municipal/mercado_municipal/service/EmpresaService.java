package mercado_municipal.mercado_municipal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaCreateDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaListDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaUpdateDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaViewDTO;
import mercado_municipal.mercado_municipal.dto.empresa.indicadores.CategoriaPercentualDTO;
import mercado_municipal.mercado_municipal.dto.empresa.indicadores.CategoriaQtdProjectionDTO;
import mercado_municipal.mercado_municipal.dto.empresa.indicadores.EmpresaColaboradoresSUMDTO;
import mercado_municipal.mercado_municipal.dto.empresa.indicadores.EmpresaCountDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoCreateDTO;
import mercado_municipal.mercado_municipal.entity.Cnae;
import mercado_municipal.mercado_municipal.entity.Empresa;
import mercado_municipal.mercado_municipal.entity.Endereco;
import mercado_municipal.mercado_municipal.repository.CategoriaEmpresaRepository;
import mercado_municipal.mercado_municipal.repository.CidadeRepository;
import mercado_municipal.mercado_municipal.repository.CnaeRepository;
import mercado_municipal.mercado_municipal.repository.EmpresaRepository;
import mercado_municipal.mercado_municipal.repository.EnderecoRepository;
import mercado_municipal.mercado_municipal.repository.TipoEmpresaRepository;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EnderecoRepository enderecoRepository;
    private final CategoriaEmpresaRepository categoriaEmpresaRepository;
    private final TipoEmpresaRepository tipoEmpresaRepository;
    private final CidadeRepository cidadeRepository;
    private final CnaeRepository cnaeRepository;

    public EmpresaService(EmpresaRepository empresaRepository, EnderecoRepository enderecoRepository, CategoriaEmpresaRepository categoriaEmpresaRepository, TipoEmpresaRepository tipoEmpresaRepository, CidadeRepository cidadeRepository, CnaeRepository cnaeRepository){
        this.empresaRepository = empresaRepository;
        this.enderecoRepository = enderecoRepository;
        this.tipoEmpresaRepository = tipoEmpresaRepository;
        this.categoriaEmpresaRepository = categoriaEmpresaRepository;
        this.cidadeRepository = cidadeRepository;
        this.cnaeRepository = cnaeRepository;
    }

    @Transactional
    public EmpresaSimpleViewDTO cadastrarEmpresa(EmpresaCreateDTO dto, EnderecoCreateDTO enderecoCreateDTO){
      

        var categoria = categoriaEmpresaRepository.findById(dto.categoriaEmpresa()).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado!"));
        var tipo = tipoEmpresaRepository.findById(dto.tipoEmpresa()).orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado !"));
        var cidade = cidadeRepository.findById(enderecoCreateDTO.cidadeId()).orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada!"));
        List<Cnae> cnae = cnaeRepository.findAllById(dto.cnae());
        
        Empresa novaEmpresa = new Empresa();
        novaEmpresa.setRazaoSocial(dto.razaoSocial());
        novaEmpresa.setCnpj(dto.cnpj());
        novaEmpresa.setAtivo(true);
        novaEmpresa.setEmail(dto.email());
        novaEmpresa.setTipo(tipo);
        novaEmpresa.setCategoria(categoria);
        novaEmpresa.setCnaes(cnae);
        novaEmpresa.setNumeroDeColaboradores(dto.numeroColaboradores());
        novaEmpresa.setAtividade(dto.atividade());

        Empresa empresaSalva = empresaRepository.save(novaEmpresa);

        Endereco novoEndereco = new Endereco();
        novoEndereco.setCep(enderecoCreateDTO.cep());
        novoEndereco.setCidade(cidade);
        novoEndereco.setEmpresa(novaEmpresa);
        novoEndereco.setLogradouro(enderecoCreateDTO.logradouroEnum());
        novoEndereco.setNumero(enderecoCreateDTO.numero());
        novoEndereco.setNomeDoLocal(enderecoCreateDTO.nomeLocal());
        novoEndereco.setPontoReferencia(enderecoCreateDTO.pontoReferencia());
        novoEndereco.setEmpresa(empresaSalva);

        novaEmpresa.adicionarEndereco(novoEndereco);
        return EmpresaSimpleViewDTO.fromEntity(empresaSalva);
    }

    @Transactional
    public List<EmpresaListDTO> listarEmpresa(){
        
        List<Empresa> empresas = empresaRepository.findAll();
        List<EmpresaListDTO> listaEmpresasDTO = empresas.stream().map(EmpresaListDTO::fromEntity).toList();

        return listaEmpresasDTO;
    }


    @Transactional
    public EmpresaViewDTO atualizarEmpresa(Long id, EmpresaUpdateDTO dto, EnderecoCreateDTO enderecoCreateDTO){
        var empresa = empresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada !"));
        
        var enderecos = enderecoRepository.findAllById(dto.enderecosIds());
        if (enderecos.size() != dto.enderecosIds().size()) {
            throw new Error("Há IDs não cadastrados");
          
        }

        boolean temCampoPreenchido = enderecoCreateDTO != null && (
            (enderecoCreateDTO.cep() != null && !enderecoCreateDTO.cep().isBlank()) || 
            (enderecoCreateDTO.nomeLocal() != null && !enderecoCreateDTO.nomeLocal().isBlank()) || 
            (enderecoCreateDTO.logradouroEnum() != null) || 
            (enderecoCreateDTO.pontoReferencia() != null && !enderecoCreateDTO.pontoReferencia().isBlank()) || 
            (enderecoCreateDTO.cidadeId() != null && enderecoCreateDTO.cidadeId() > 0));


        if (temCampoPreenchido) {
            var cidade = cidadeRepository.findById(enderecoCreateDTO.cidadeId()).orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada !"));            
            Endereco novoEndereco = new Endereco();
            novoEndereco.setCep(enderecoCreateDTO.cep());
            novoEndereco.setCidade(cidade);
            novoEndereco.setLogradouro(enderecoCreateDTO.logradouroEnum());
            novoEndereco.setNomeDoLocal(enderecoCreateDTO.nomeLocal());
            novoEndereco.setNumero(enderecoCreateDTO.numero());
            novoEndereco.setPontoReferencia(enderecoCreateDTO.pontoReferencia());
            enderecoRepository.save(novoEndereco);
            enderecos.add(novoEndereco);
        }

        var categoria = categoriaEmpresaRepository.findById(dto.categoriaEmpresa()).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada !"));
        var tipo = tipoEmpresaRepository.findById(dto.tipoEmpresa()).orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado !"));
        List<Cnae> cnaes = cnaeRepository.findAllById(dto.cnae());
       
        empresa.setAtividade(dto.atividade());
        empresa.setCnpj(dto.cnpj());
        empresa.setCnaes(cnaes);
        empresa.setAtivo(dto.ativo());
        empresa.setCategoria(categoria);
        empresa.setTipo(tipo);
        empresa.setEmail(dto.email());
        empresa.setNumeroDeColaboradores(dto.numeroColaboradores());
        empresa.setRazaoSocial(dto.razaoSocial());
        enderecos.forEach(empresa::adicionarEndereco);

        return EmpresaViewDTO.fromEntity(empresa);
    }


    @Transactional
    public EmpresaCountDTO qtdEmpresas(){
       return new EmpresaCountDTO(empresaRepository.count());
    }

    @Transactional 
    public EmpresaColaboradoresSUMDTO qtdColaboradores(){
        return new EmpresaColaboradoresSUMDTO(empresaRepository.totalColaboradores());
    }

    @Transactional
    public List<CategoriaPercentualDTO> porcentagemCategorias(){
        Long totalEmpresasComcategoria = empresaRepository.qtdEmpresasDaCategoria();

        if (totalEmpresasComcategoria == null || totalEmpresasComcategoria == 0) {
            return List.of();
        }

        List<CategoriaQtdProjectionDTO> dados = empresaRepository.totalEmpresasComCategoria();

        return dados.stream()
        .map(d -> {
            double percentual = (d.getCount()* 100.0)/totalEmpresasComcategoria;
            return new CategoriaPercentualDTO( d.getCategoria(), d.getCount(), percentual
            );
        }).toList();
    }

    @Transactional(readOnly = true)
    public Page<EmpresaSimpleViewDTO> buscarAutomatizada(
        String razaoSocial,
        Long cnae,
        String cnpj,
        Long cidadeId,
        int page,
        int size,
        Long categoriaEmpresaId
    ){

        Pageable pageable = PageRequest.of(page, size);

        if (cnpj != null && !cnpj.isBlank()) {
            return empresaRepository.findByCnpj(cnpj)
            .map(emp -> new PageImpl<>(List.of(EmpresaSimpleViewDTO.fromEntity(emp)), pageable, 1))
            .orElseGet(() -> new  PageImpl<>(List.of(), pageable, 0));
        }

        if (razaoSocial != null && !razaoSocial.isBlank()) {
            return empresaRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial, pageable).map(EmpresaSimpleViewDTO::fromEntity);
        }

        if (cnae != null) {
            return empresaRepository.findByCnaesId(cnae, pageable).map(EmpresaSimpleViewDTO::fromEntity);      
        }

        if (cidadeId != null) {
            return empresaRepository.findDistinctByEnderecosCidadeId(cidadeId, pageable).map(EmpresaSimpleViewDTO::fromEntity);
        }

        if (categoriaEmpresaId != null) {
            return empresaRepository.findByCategoriaId(categoriaEmpresaId, pageable).map(EmpresaSimpleViewDTO::fromEntity);
        }
        
        return empresaRepository.findAll(pageable).map(EmpresaSimpleViewDTO::fromEntity);

    }

    @Transactional
    public EmpresaSimpleViewDTO desativarEmpresa(Long empresaId){
        Empresa empresa = empresaRepository.findById(empresaId).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada !"));
        empresa.setAtivo(false);
        return EmpresaSimpleViewDTO.fromEntity(empresa);
    }

    @Transactional
    public void deletarEmpresa(Long id){
        Empresa empresaExistente = empresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada!"));
        empresaRepository.delete(empresaExistente);
    }

    @Transactional
    public EmpresaViewDTO buscarEmpresaPorId(Long id){
        Empresa empresaExistente = empresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada !"));
        return EmpresaViewDTO.fromEntity(empresaExistente);
    }
}

