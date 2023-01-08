package emlakcepte.service;

import emlakcepte.configuration.RabbitMQConfiguration;
import emlakcepte.model.Product;
import emlakcepte.model.User;
import emlakcepte.model.enums.PaymentStatusType;
import emlakcepte.repository.ProductRepository;
import emlakcepte.request.ProductRequest;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfiguration rabbitMQConfiguration;

    public void prepareRabbitConfToPayment(){
        rabbitMQConfiguration.setQueueName("emlakcepte.payment");
        rabbitMQConfiguration.setExchange("emlakcepte.payment");
    }

    public Product purchase(ProductRequest productRequest) {
        Optional<User> foundUser = userService.getById(productRequest.getUserId());

        if (!foundUser.isPresent())
            throw new IllegalStateException("Kullanıcı bulunamadı.");

        Product product = convert(productRequest);
        product.setUser(foundUser.get());
        product.setPaymentStatusType(PaymentStatusType.WAITING);
        Product savedProduct = productRepository.save(product);

        prepareRabbitConfToPayment();
        rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), savedProduct);

        return savedProduct;
    }

    private Product convert(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setRealtyLimit(productRequest.getRealtyLimit());
        product.setPrice(productRequest.getPrice());
        product.setValidityPeriod(productRequest.getValidityPeriod());
        return product;
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public List<Product> getAllByUserId(int id) {
        return productRepository.findAllByUserId(id);
    }

    public List<Product> getAllByUserIdAndPaymentStatusType(int id, PaymentStatusType paymentStatusType) {
        return productRepository.findAllByUserIdAndPaymentStatusType(id, paymentStatusType);
    }

    public boolean canPublishRealtyByUserId(int id) {
        List<Product> productList = getAllByUserIdAndPaymentStatusType(id, PaymentStatusType.COMPLETED);
        int realtyLimit = 0;
        int validityPeriod = 0;
        for (Product p : productList) {
            realtyLimit += p.getRealtyLimit();
            validityPeriod += p.getValidityPeriod();
        }
        System.out.println(realtyLimit+":"+validityPeriod);
        System.out.println(realtyLimit > 0 && validityPeriod >0);
        return realtyLimit > 0 && validityPeriod >0;
    }

    public Product updatePaymentStatusType(int productId, PaymentStatusType paymentStatusType) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
        product.setPaymentStatusType(paymentStatusType);
        return productRepository.save(product);
    }

    public void decreaseRealtyLimitAndValidityPeriodByUserId(int id){
        productRepository.decreaseRealtyLimitAndValidityPeriod(id);
    }


}