package adapterPattern.after;

import adapterPattern.before.Account;
import adapterPattern.before.Security.UserDetails;

/**
 * filename     : accountuserdetails
 * author       : jungwoo
 * description  : UserDetails 인터페이스를 구현한 엔티티 어댑터
 */

public class AccountUserDetails implements UserDetails {

  private final Account account;

  public AccountUserDetails(Account account) {
    this.account = account;
  }

  @Override
  public String getUsername() {
    return account.getName();
  }

  @Override
  public String getPassword() {
    return account.getPassword();
  }
}
