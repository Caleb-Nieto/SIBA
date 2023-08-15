package mx.edu.utez.siba.models.repository;
import java.util.List;

public interface DaoRepository <T>{
    List<T> findAll(int i, int l);
    T findOne(Long id);
    String save(T object);
    String update(T object);
    String delete(Long id);
}

