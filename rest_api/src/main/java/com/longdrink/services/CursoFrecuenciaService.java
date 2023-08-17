import com.longdrink.dao.ICursoFrecuenciaDAO;
import com.longdrink.model.Curso;
import com.longdrink.model.CursoFrecuencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoFrecuenciaService {
    @Autowired
    private ICursoFrecuenciaDAO cursFrecuenciaDAO;

    @Autowired
    private EntityManager em;

    //Actualizar CursoFrecuencia - 1:1 (Un curso - Una frecuencia.
    public int actualizarCF(CursoFrecuencia cf){
        Query query = em.createQuery("UPDATE CursoFrecuencia c SET c.id_frecuencia = "+cf.getId_frecuencia()+" WHERE c.id_curso = "+cf.getId_curso());
        return query.executeUpdate();
    }

    public CursoFrecuencia buscarFrecuencia(int id_curso){
        try{
            Query query = em.createQuery("SELECT f FROM CursoFrecuencia f WHERE f.id_curso = "+id_curso);
            return (CursoFrecuencia) query.getSingleResult();
        }
        catch(Exception ex){
            return new CursoFrecuencia();
        }
    }
}
