package ro.sisc.ttj.inttjence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sisc.ttj.inttjence.models.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findFirstByName(String name);
}
