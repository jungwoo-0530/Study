package adapterPattern.before.Security;

/**
 * fileName     : UserDetailsService
 * author       : jungwoo
 * description  : Target Interface
 */
public interface UserDetailsService {
  UserDetails loadUser(String username);
}
