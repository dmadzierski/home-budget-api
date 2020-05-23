package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RestController
@RequestMapping(path = "/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CategoryResource {

  private CategoryController categoryController;


  @PutMapping(value = "/add")
  public ResponseEntity<CategoryDto> addCategory (Principal principal, @Valid @RequestBody CategoryDto categoryDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryController.addCategory(principal, categoryDto));
  }

  @DeleteMapping("/remove/{categoryId}")
  public ResponseEntity removeCategory (Principal principal, @PathVariable Long categoryId) {
    categoryController.removeCategory(principal, categoryId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(consumes = MediaType.ALL_VALUE)
  public ResponseEntity<List<CategoryDto>> getUserCategories (Principal principal) {
    return ResponseEntity.ok(categoryController.getCategories(principal));
  }


}
