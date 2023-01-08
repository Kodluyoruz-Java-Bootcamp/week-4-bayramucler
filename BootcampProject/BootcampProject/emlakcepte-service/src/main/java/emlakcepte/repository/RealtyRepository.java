package emlakcepte.repository;

import java.util.List;

import emlakcepte.model.enums.PaymentStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import emlakcepte.model.Realty;
import emlakcepte.model.enums.RealtyStatusType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface RealtyRepository extends JpaRepository<Realty, Integer> {

	List<Realty> findAllByUserId(int id);

	List<Realty> findAllByStatus(RealtyStatusType active);

	Realty findById(int id);


}
