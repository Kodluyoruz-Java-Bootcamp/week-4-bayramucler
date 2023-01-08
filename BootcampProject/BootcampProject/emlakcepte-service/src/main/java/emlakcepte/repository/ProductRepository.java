package emlakcepte.repository;

import emlakcepte.model.Product;
import emlakcepte.model.enums.PaymentStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByUserId(int id);


    List<Product> findAllByUserIdAndPaymentStatusType(int id, PaymentStatusType paymentStatusType);

    @Transactional
    @Modifying
    @Query(value = " update                            " +
            "	product p                      " +
            "set                               " +
            "	realty_limit = realty_limit -1 " +
            "where                             " +
            "	id in                          " +
            "	(                              " +
            "		select                     " +
            "			id                     " +
            "		from                       " +
            "			product p              " +
            "		where                      " +
            "			payment_status_type =2 " +
            "			and user_id =:userId   " +
            "			and realty_limit > 0   " +
            "			and validity_period >0 " +
            "			order by id desc       " +
            "			limit 1                " +
            "	)							   ", nativeQuery = true)
    void decreaseRealtyLimitAndValidityPeriod(@Param("userId") int userId);
}
