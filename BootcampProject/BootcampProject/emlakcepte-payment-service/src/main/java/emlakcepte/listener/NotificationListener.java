package emlakcepte.listener;

import emlakcepte.client.ProductServiceClient;
import emlakcepte.model.PaymentStatusType;
import emlakcepte.model.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import emlakcepte.model.User;

@Component
public class NotificationListener {
	@Autowired
	private ProductServiceClient productServiceClient;

	@RabbitListener(queues = "emlakcepte.payment")
	public void notificationListener(Product product) {
		productServiceClient.updatePaymentStatusType(product.getId(), PaymentStatusType.COMPLETED);

	}

}
