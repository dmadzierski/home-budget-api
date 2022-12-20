package pl.wallet.category;

 class CategoryMapper {

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

   public static SimpleCategoryQueryDto toQueryDto(Category category) {
      return SimpleCategoryQueryDto.builder()
         .id(category.getId())
         .build();
   }

   public static CategoryDto toDto(SimpleCategoryQueryDto simpleCategoryQueryDto) {
      return CategoryDto.builder()
         .id(simpleCategoryQueryDto.getId())
         .build();
   }
}
