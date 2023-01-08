package emlakcepte.client;


import emlakcepte.model.PaymentStatusType;
import emlakcepte.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "emlakcepte-service", url = "http://localhost:9090")
public interface ProductServiceClient {

    @PutMapping(value = "/products/{productId}/payment")
    Product updatePaymentStatusType(@PathVariable int productId, @RequestBody PaymentStatusType paymentStatusType);

}
