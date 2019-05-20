/**
 * Junior account class inherits BankAccount class.
 * 
 * @author LiYuexiang
 *
 */
public class JuniorAccount extends BankAccount {

	/**
	 * The constructor
	 * 
	 * @param accNo
	 *            account No.
	 * @param accName
	 *            account name
	 * @param balance
	 *            account balance
	 */
	public JuniorAccount(String accNo, String accName, double balance) {
		super(accNo, accName, balance, "Junior");
	}

}
