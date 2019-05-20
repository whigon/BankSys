/**
 * Saver account class inherits BankAccount class.
 * 
 * @author LiYuexiang
 *
 */
public class SaverAccount extends BankAccount {
	// Notice state
	private boolean notice = false;

	/**
	 * The constructor
	 * 
	 * @param accNo
	 *            account No.
	 * @param name
	 *            account name
	 * @param balance
	 *            account balance
	 */
	public SaverAccount(String accNo, String name, double balance) {
		super(accNo, name, balance, "Saver");
	}

	/**
	 * Make notice before withdraw
	 */
	public void makeNotice() {
		notice = true;
	}

	/**
	 * Check notice state and call superclass's withdraw function
	 * 
	 * @param amount
	 *            the withdraw amount
	 * @return withdraw state
	 */
	public int withdraw(double amount) {
		if (notice) {
			notice = false;
			return super.withdraw(amount);
		} else {
			makeNotice();
			return 2; // No notice before
		}
	}
}
