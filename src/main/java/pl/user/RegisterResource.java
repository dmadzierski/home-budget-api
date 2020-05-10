package pl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class RegisterResource {

  private UserController userController;

  @Autowired
  RegisterResource (UserController userController) {
    this.userController = userController;
  }

  @PostMapping()
  public ResponseEntity<UserDto> register (@Valid @RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userController.addWithDefaultRoleAndDefaultCategory(userDto));
  }
}
