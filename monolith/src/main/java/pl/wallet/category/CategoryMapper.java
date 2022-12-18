package pl.wallet.category;

public class CategoryMapper {

   public static Category toEntity(CategoryDto categoryDto) {
      return Category.builder()
         .name(categoryDto.getName())
         .description(categoryDto.getDescription())
         .transactionType(categoryDto.getTransactionType())
         .build();
   }

   public static CategoryDto toDto(Category category) {
      return CategoryDto.builder()
         .id(category.getId())
         .name(category.getName())
         .transactionType(category.getTransactionType())
         .description(category.getDescription())
         .build();
   }
}
