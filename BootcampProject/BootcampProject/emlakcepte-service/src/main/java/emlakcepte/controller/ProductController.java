package emlakcepte.controller;

import emlakcepte.model.Product;
import emlakcepte.model.Realty;
import emlakcepte.model.enums.PaymentStatusType;
import emlakcepte.request.ProductRequest;
import emlakcepte.request.UserRequest;
import emlakcepte.request.UserUpdateRequest;
import emlakcepte.response.UserResponse;
import emlakcepte.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<Product>> getAllByUserId(@PathVariable int id) {
        List<Product> products = productService.getAllByUserId(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    // @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Product> purchase(@RequestBody ProductRequest productRequest) {
        Product productResponse = productService.purchase(productRequest);
        return ResponseEntity.ok(productResponse);
    }



    @PutMapping("/{productId}/payment")
    public ResponseEntity<Product> updatePaymentStatusType(@PathVariable int productId, @RequestBody PaymentStatusType paymentStatusType) {
        Product product = productService.updatePaymentStatusType(productId, paymentStatusType);
        return ResponseEntity.ok(product);
    }
}
