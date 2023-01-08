package emlakcepte.controller;

import java.util.List;

import emlakcepte.model.enums.PaymentStatusType;
import emlakcepte.model.enums.RealtyStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import emlakcepte.model.Realty;
import emlakcepte.request.RealtyRequest;
import emlakcepte.service.RealtyService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/realtyes")
public class RealtyController {

	@Autowired
	private RealtyService realtyService;

	// GET /realtyes
	@GetMapping
	public ResponseEntity<List<Realty>> getAll() {
		return ResponseEntity.ok(realtyService.getAll());
	}

	// POST /realtyes
	@PostMapping
	public ResponseEntity<Realty> create(@RequestBody RealtyRequest realtyRequest) {
		Realty realty = realtyService.create(realtyRequest);
		return new ResponseEntity<>(realty, HttpStatus.CREATED);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<List<Realty>> getAllByUserId(@PathVariable int id) {
		List<Realty> realtyes = realtyService.getAllById(id);
		return ResponseEntity.ok(realtyes);
	}
	
	@GetMapping(value = "/status/active")
	public ResponseEntity<List<Realty>> getAllActiveRealtyes() {
		List<Realty> realtyes = realtyService.getAllActiveRealtyes();
		return ResponseEntity.ok(realtyes);
	}

	@PutMapping("/{realtyId}/status")
	public Realty updateRealtyStatusTypeById(@PathVariable int realtyId, @RequestBody RealtyStatusType realtyStatusType) {
		System.out.println("*******************"+realtyId + ":"+ realtyStatusType);
		Realty realty = realtyService.getById(realtyId);
		realty.setStatus(realtyStatusType);
		return realtyService.update(realty);
	}
}
