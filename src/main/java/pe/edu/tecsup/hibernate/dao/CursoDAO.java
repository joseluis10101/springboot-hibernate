package pe.edu.tecsup.hibernate.dao;

import java.util.List;
import pe.edu.tecsup.hibernate.helper.GenericDAO;
import pe.edu.tecsup.hibernate.model.Curso;

public interface CursoDAO extends GenericDAO<Curso> {

    Curso getByCodigo(String codigo);

     List<Curso> getByNombre(String nombre);
}
