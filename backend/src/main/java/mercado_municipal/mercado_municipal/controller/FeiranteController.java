package mercado_municipal.mercado_municipal.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.feirante.FeiranteCreateDTO;
import mercado_municipal.mercado_municipal.dto.feirante.FeiranteSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.feirante.FeiranteViewDTO;
import mercado_municipal.mercado_municipal.dto.matricula.MatriculaViewDTO;
import mercado_municipal.mercado_municipal.service.FeiranteService;
import mercado_municipal.mercado_municipal.service.MatriculaService;

@RestController
@RequestMapping("/api/feirante")
public class FeiranteController {
    
    private final FeiranteService feiranteService;
    private final MatriculaService matriculaService;

    public FeiranteController(FeiranteService feiranteService, MatriculaService matriculaService) {
        this.feiranteService = feiranteService;
        this.matriculaService = matriculaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<FeiranteViewDTO> cadastrarFeirante(@RequestBody FeiranteCreateDTO feiranteCreateDTO){
        return ResponseEntity.ok(feiranteService.cadastrarFeirante(feiranteCreateDTO));
    }

    @GetMapping("/proxima/matricula")
    public ResponseEntity<MatriculaViewDTO> proximaMatricula(){
        String matricula = matriculaService.gerar();
        return ResponseEntity.ok(MatriculaViewDTO.to(matricula));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<FeiranteSimpleViewDTO>> listarFeirantes(
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(feiranteService.listarFeirantes(page, size));
    }

    @GetMapping("/buscar/por/matricula")
    public ResponseEntity<Page<FeiranteSimpleViewDTO>> buscarPorMatricula(
        @RequestParam(name = "matricula") String matricula,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(feiranteService.buscarPorMatricula(matricula, page, size));
    }

    @GetMapping("/buscar/por/banca")
    public ResponseEntity<Page<FeiranteSimpleViewDTO>> buscarPorBanca(
        @RequestParam(name = "banca") String banca,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(feiranteService.buscarPorBanca(page, size, banca));
    }

    @GetMapping("/buscar/por/box")
    public ResponseEntity<Page<FeiranteSimpleViewDTO>> buscarPorBox(
        @RequestParam(name = "box") Boolean box,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(feiranteService.buscarBoxPor(box, page, size));
    }

    @GetMapping("/busca/multiplos/campos")
    public ResponseEntity<Page<FeiranteSimpleViewDTO>> buscaInteligente(@RequestParam(name = "termo", required = false
    ) String termo, @RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size" ) int size){
        return ResponseEntity.ok(feiranteService.buscarMultiplosCampos(termo, page, size));
    }
}
