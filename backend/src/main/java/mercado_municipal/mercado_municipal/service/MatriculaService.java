package mercado_municipal.mercado_municipal.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class MatriculaService {
    

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public String gerar(){
        Number n = (Number) em.createNativeQuery("SELECT nextval('matricula_seq')").getSingleResult();

        long seq = n.longValue();

        return String.format("F%04dCA", seq);
    }
}
