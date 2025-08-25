package NiggleNandu.User_Service.Clients;

import NiggleNandu.User_Service.Dto.SignupRequestForSecurity;
import NiggleNandu.User_Service.Dto.SignupResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Jwt-Security", url = "http://localhost:8088/api/auth")
public interface SecurityClient {

    @PostMapping("/signup")
    @Headers("X-Gateway-Auth: true")
    String registerUser(SignupRequestForSecurity signupRequest);
}
