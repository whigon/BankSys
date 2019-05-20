import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Current account class inherits BankAccount class. And it overwrites some
 * methods.
 * 
 * @author LiYuexiang
 *
 */
public class CurrentAccount extends BankAccount {
	// The overdraft limit
	private double overdraft;

	/**
	 * The constructor
	 * 
	 * @param accNo
	 *            account No.
	 * @param accName
	 *            account name
	 * @param balance
	 *            account balance
	 * @param overdraft
	 *            account overdraft
	 */
	public CurrentAccount(String accNo, String accName, double balance, double overdraft) {
		super(accNo, accName, balance, "Current");
		this.overdraft = overdraft;
		repayOverdraft();
	}

	/**
	 * Deposit money into current account, then check overdraft and repay overdraft
	 */
	public String deposit(double amount, String type) {
		String str = super.deposit(amount, type);

		repayOverdraft();

		return str;
	}

	/**
	 * Withdraw money from current account, current account can use overdraft when
	 * balance is insufficient
	 */
	public int withdraw(double amount) {
		if (super.getBalance() + overdraft >= amount) {
			if (super.getBalance() >= amount) {
				super.withdraw(amount);
			} else {
				// Use balance first
				amount -= super.getBalance();
				super.withdraw(super.getBalance());

				// If use overdraft
				if (amount > 0) {
					overdraft -= amount;

					System.out.println("Using overdraft " + amount);

					rewriteOverdraft();
				}
			}
			return 0;
		}

		return 1;
	}

	/**
	 * Check balance and overdraft, if overdraft is used and balance is not 0, repay
	 * overdraft
	 */
	public void repayOverdraft() {
		if (super.getBalance() > 0 && overdraft != 200) {
			if (super.getBalance() > (200 - overdraft)) {
				super.withdraw(200 - overdraft);
				overdraft = 200;
				rewriteOverdraft();
			} else {
				overdraft += super.getBalance();
				super.withdraw(super.getBalance());
				rewriteOverdraft();
			}
		}
	}

	/***
	 * Rewrite overdraft change into file
	 */
	private void rewriteOverdraft() {
		// Write into file
		File file = new File("./Account.txt");
		ArrayList<String> arr = new ArrayList<>();

		// Read info
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;

			while ((s = br.readLine()) != null) {
				arr.add(s);
			}

			br.close();
		} catch (Exception e) {
			System.out.println("Withdraw: Fail to read account info.");
		}

		// Rewrite info
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (String a : arr) {
				// Change overdraft
				if (a.split(" [|] ")[0].equals(super.getAccountNo())) {
					String front = a.split(" [|] ")[0] + " | " + a.split(" [|] ")[1] + " | " + a.split(" [|] ")[2]
							+ " | " + a.split(" [|] ")[3];
					String back = a.split(" [|] ")[5] + " | " + a.split(" [|] ")[6] + " | " + a.split(" [|] ")[7]
							+ " | " + a.split(" [|] ")[8];
					a = front + " | " + overdraft + " | " + back;
				}

				bw.write(a);
				bw.newLine();
			}

			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("Withdraw: Fail to rewrite the information of account.");
		}
	}

	/**
	 * Close account, check overdraft use and call superclass's close function
	 */
	public int close() {
		if (this.getOverdraft() != 200) {
			return 2; // 2 means overdraft is used
		} else {
			return super.close();
		}
	}

	/**
	 * Get the account overdraft
	 * 
	 * @return account overdraft
	 */
	public double getOverdraft() {
		return overdraft;
	}

}
