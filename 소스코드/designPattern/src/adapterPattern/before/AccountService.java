package adapterPattern.before;

/**
 * fileName     : AccountService
 * author       : jungwoo
 * description  :
 */
public class AccountService {
  public Account findAccountByUsername(String username) {
    Account account = new Account();
    account.setName(username);
    account.setPassword(username);
    account.setEmail(username);

    return account;
  }

  public Account createNewAccount(String username) {
    // TODO, blah blah

    return new Account();
  }
}
