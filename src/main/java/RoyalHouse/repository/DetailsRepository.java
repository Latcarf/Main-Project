package RoyalHouse.repository;

import RoyalHouse.model.building.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Long> {
}
