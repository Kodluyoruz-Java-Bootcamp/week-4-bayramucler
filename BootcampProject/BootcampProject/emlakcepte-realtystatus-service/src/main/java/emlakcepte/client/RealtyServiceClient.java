package emlakcepte.client;


import emlakcepte.model.Realty;
import emlakcepte.model.enums.RealtyStatusType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "emlakcepte-service", url = "http://localhost:9090")
public interface RealtyServiceClient {

    @PutMapping(value = "/realtyes/{realtyId}/status")
    RealtyStatusType updateRealtyStatusTypeById(@PathVariable int realtyId, @RequestBody RealtyStatusType realtyStatusType);

}
