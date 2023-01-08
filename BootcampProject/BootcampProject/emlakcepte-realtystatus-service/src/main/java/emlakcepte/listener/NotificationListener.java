package emlakcepte.listener;

import emlakcepte.client.RealtyServiceClient;
import emlakcepte.model.Realty;
import emlakcepte.model.enums.RealtyStatusType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
	@Autowired
	private RealtyServiceClient realtyServiceClient;

	@RabbitListener(queues = "emlakcepte.realtystatus")
	public void notificationListener(int realtyId) {
		System.out.println(realtyId);
		realtyServiceClient.updateRealtyStatusTypeById(realtyId, RealtyStatusType.ACTIVE);
	}

}
