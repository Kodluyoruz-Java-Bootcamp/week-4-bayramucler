package emlakcepte.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import emlakcepte.configuration.RabbitMQConfiguration;
import emlakcepte.model.enums.PaymentStatusType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emlakcepte.client.Banner;
import emlakcepte.client.BannerServiceClient;
import emlakcepte.controller.UserController;
import emlakcepte.model.Realty;
import emlakcepte.model.User;
import emlakcepte.model.enums.RealtyStatusType;
import emlakcepte.model.enums.UserType;
import emlakcepte.repository.RealtyRepository;
import emlakcepte.request.RealtyRequest;

@Service
public class RealtyService {

	private static final int MAX_INDIVICUAL_REALTY_SIZE = 5;

	@Autowired
	private UserService userService;

	@Autowired
	private RealtyRepository realtyRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitMQConfiguration rabbitMQConfiguration;


	@Autowired
	private BannerServiceClient bannerServiceClient;

	public void prepareRabbitConfToRealtyStatus(){
		rabbitMQConfiguration.setQueueName("emlakcepte.realtystatus");
		rabbitMQConfiguration.setExchange("emlakcepte.realtystatus");
	}

	public Realty create(RealtyRequest realtyRequest) {
		Logger logger = Logger.getLogger(UserController.class.getName());
		Optional<User> foundUser = userService.getById(realtyRequest.getUserId());
		if (UserType.INDIVIDUAL.equals(foundUser.get().getType())) { // en fazla 5 ilan girebilir.
			List<Realty> realtyList = realtyRepository.findAllByUserId(foundUser.get().getId());
			if (MAX_INDIVICUAL_REALTY_SIZE == realtyList.size()) {
				logger.log(Level.WARNING, "Bireysel kullanıcı en fazla 5 ilan girebilir. userID : {0}",
						foundUser.get().getId());
				throw new IllegalStateException("Bireysel kullanıcı en fazla 5 ilan girebilir!");
			}
		}

		if(!productService.canPublishRealtyByUserId(foundUser.get().getId()))
			throw new IllegalStateException("Kullanıcının ilan yayınlama hakkı olmadığından ilan yayınlayamaz!");

		if (!foundUser.isPresent())
			throw new IllegalStateException("Kullanıcı bulunamadı!");

		Realty realty = convert(realtyRequest);
		realty.setUser(foundUser.get());
		Realty savedRealty = realtyRepository.save(realty);

		System.out.println("createRealty :: " + realty);

		// TODO :: banner-service çağır ve banner siparişi ver

		Banner bannerRequest = new Banner(String.valueOf(realty.getNo()), 1, "123123", "");

		Banner bannerResponse = bannerServiceClient.create(bannerRequest);

		if (bannerResponse.getAdet() > 1) {
			System.out.println("hata verilsin");
		}
		System.out.println("bannerResponse :" + bannerResponse.getAdet());

		productService.decreaseRealtyLimitAndValidityPeriodByUserId(foundUser.get().getId());

		prepareRabbitConfToRealtyStatus();
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), savedRealty.getId());

		return savedRealty;
	}

	private Realty convert(RealtyRequest realtyRequest) {
		Realty realty = new Realty();
		realty.setNo(realtyRequest.getNo());
		realty.setCreateDate(LocalDateTime.now());
		realty.setStatus(RealtyStatusType.IN_REVIEW);
		realty.setTitle(realtyRequest.getTitle());
		realty.setProvince(realtyRequest.getProvince());
		return realty;
	}

	public List<Realty> getAll() {
		return realtyRepository.findAll();
	}

	public void getAllByProvince(String province) {

		getAll().stream().filter(realty -> realty.getProvince().equals(province)) //
				// .count();
				.forEach(realty -> System.out.println(realty));

	}
//
//	public List<Realty> getAllByUserName(User user) {
//		return getAll().stream().filter(realty -> realty.getUser().getMail().equals(user.getMail())).toList();
//	}

	public List<Realty> getActiveRealtyByUserName(User user) {
		return getAll().stream().filter(realty -> realty.getUser().getName().equals(user.getName()))
				.filter(realty -> RealtyStatusType.ACTIVE.equals(realty.getStatus())).toList();
	}

	public List<Realty> getAllById(int id) {
		return realtyRepository.findAllByUserId(id);
	}

	public List<Realty> getAllActiveRealtyes() {
		return realtyRepository.findAllByStatus(RealtyStatusType.ACTIVE);
	}

	public Realty getById(int id){
		return realtyRepository.findById(id);
	}

	public Realty update(Realty realty) {
		return realtyRepository.save(realty);
	}
}
