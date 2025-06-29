import com.example.AgeCare.model.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalModel, Long> {

}