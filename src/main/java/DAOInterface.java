import java.util.List;

public interface DAOInterface <T>{

     T getById (int id);
     List<T> getAll();
     void insert(T t);
     void update(T t);
     void delete(int delid);

}
