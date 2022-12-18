package pl.user;


class UserMapper {

   private UserMapper() {
   }

   public static UserDto toDto(User user) {
      return UserDto.builder()
         .email(user.getEmail())
         .favoriteWalletId(user.getFavoriteWalletId())
         .build();
   }


   public static User toEntity(UserDto userDto) {
      return User.builder().password(userDto.getPassword()).email(userDto.getEmail()).build();
   }
}
