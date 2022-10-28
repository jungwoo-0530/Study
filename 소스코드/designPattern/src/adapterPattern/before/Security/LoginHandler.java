package adapterPattern.before.Security;

/**
 * fileName     : LoginHandler
 * author       : jungwoo
 * description  : 클라이언트 코드
 */
public class LoginHandler {
  private final UserDetailsService userDetailsService;

  public LoginHandler(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public String login(String username, String password) {
    UserDetails userDetails = userDetailsService.loadUser(username);

    if (userDetails.getPassword().equals(password)) {
      return userDetails.getUsername();
    } else {
      throw new RuntimeException();
    }
  }
}
