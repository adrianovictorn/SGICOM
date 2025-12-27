package mercado_municipal.mercado_municipal.dto.matricula;


public record MatriculaViewDTO(
    String resultado
) {
    public static MatriculaViewDTO to(String matricula){
        return new MatriculaViewDTO(matricula);
    }
}
