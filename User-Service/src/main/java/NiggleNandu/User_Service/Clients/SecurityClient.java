package NiggleNandu.User_Service.Clients;

import NiggleNandu.User_Service.Dto.SignupRequestDto;
import NiggleNandu.User_Service.Dto.SignupResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Jwt-Security", url = "http://localhost:8087/api/auth")
public interface SecurityClient {

    @PostMapping("/signup")
    SignupResponseDto registerUser(SignupRequestDto signupRequest);
}
