package adapterPattern.after;

import adapterPattern.before.Account;
import adapterPattern.before.AccountService;
import adapterPattern.before.Security.UserDetails;
import adapterPattern.before.Security.UserDetailsService;

/**
 * fileName     : AccountUserDetailsService
 * author       : jungwoo
 * description  : UserDetailService를 구현한 서비스 클래스 어댑터.
 */
public class AccountUserDetailsService implements UserDetailsService {

  private final AccountService accountService;

  public AccountUserDetailsService(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public UserDetails loadUser(String username) {
    Account account = accountService.findAccountByUsername(username);
    return new AccountUserDetails(account);
  }
}
