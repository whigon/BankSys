import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class a super class of bank account.
 * 
 * @author LiYuexiang
 *
 */
public class BankAccount {
	// Account no.
	private String accNo;
	// Account name
	private String accName;
	// Account balance
	private double balance;
	// Account type
	private String type;
	// Generate a serial accout no.
	private static String nextNo = "100012340000001";
	// Account state, 1 means activated, 0 means suspended
	private int status;

	/**
	 * The constructor
	 * 
	 * @param accNo
	 *            account No.
	 * @param accName
	 *            account names
	 * @param balance
	 *            account balance
	 * @param type
	 *            account type
	 */
	public BankAccount(String accNo, String accName, double balance, String type) {
		this.accNo = accNo;
		this.accName = accName;
		this.balance = balance;
		this.type = type;
		checkStatus();
	}

	/**
	 * Get account status. 1 means activated. 0 means suspended.
	 */
	public void checkStatus() {
		File file = new File("./Account.txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;

			while ((s = br.readLine()) != null) {
				if (s.split(" [|] ")[0].equals(accNo))
					this.status = Integer.parseInt(s.split(" [|] ")[8]);
			}

			br.close();
		} catch (Exception e) {
			System.out.println("Fail to find the status of account.");
		}
	}

	/**
	 * Deposit money in two ways.
	 * 
	 * @param amount
	 *            the deposit amount
	 * @param type
	 *            the deposit type: cleared and un-cleared
	 * @return deposit state
	 */
	public String deposit(double amount, String type) {
		if (type.equals("Cash")) {
			balance += amount;

			// Write change into file
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
				System.out.println("Deposit: Fail to read account info.");
			}

			// Rewrite info
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));

				for (String a : arr) {
					// Change balance
					if (a.split(" [|] ")[0].equals(accNo)) {
						String front = a.split(" [|] ")[0] + " | " + a.split(" [|] ")[1] + " | " + a.split(" [|] ")[2];
						String back = a.split(" [|] ")[4] + " | " + a.split(" [|] ")[5] + " | " + a.split(" [|] ")[6]
								+ " | " + a.split(" [|] ")[7] + " | " + a.split(" [|] ")[8];
						a = front + " | " + balance + " | " + back;
					}

					bw.write(a);
					bw.newLine();
				}

				bw.flush();
				bw.close();
			} catch (Exception e) {
				System.out.println("Deposit: Fail to rewrite the information of account.");
			}

			return "Success";
		} else {
			// Store into uncleared file
			File file = new File("./Uncleared.txt");

			try {
				if (!file.exists()) {
					file.getParentFile().mkdir();
					try {
						file.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
				String s = accNo + " | " + amount;

				bw.write(s);
				bw.newLine();

				bw.flush();
				bw.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return "Wait for clear.";
		}
	}

	/**
	 * Withdraw money from account
	 * 
	 * @param amount
	 *            the withdraw money
	 * @return withdraw state
	 */
	public int withdraw(double amount) {
		if (balance >= amount) {
			balance -= amount;

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
					// Change balance
					if (a.split(" [|] ")[0].equals(accNo)) {
						String front = a.split(" [|] ")[0] + " | " + a.split(" [|] ")[1] + " | " + a.split(" [|] ")[2];
						String back = a.split(" [|] ")[4] + " | " + a.split(" [|] ")[5] + " | " + a.split(" [|] ")[6]
								+ " | " + a.split(" [|] ")[7] + " | " + a.split(" [|] ")[8];
						a = front + " | " + balance + " | " + back;
					}

					bw.write(a);
					bw.newLine();
				}

				bw.flush();
				bw.close();
			} catch (Exception e) {
				System.out.println("Withdraw: Fail to rewrite the information of account.");
			}

			return 0; // Success
		}

		return 1; // Balance is not enough
	}

	/**
	 * Suspend the account
	 */
	public void suspend() {
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
			System.out.println("Suspend: Fail to read account info.");
		}

		// Rewrite info
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (String a : arr) {
				// Change status
				if (a.split(" [|] ")[0].equals(accNo)) {
					a = a.substring(0, a.length() - 1) + 0;
				}

				bw.write(a);
				bw.newLine();
			}

			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("Fail to find the status of account.");
		}

		this.status = 0;
	}

	/**
	 * Reinstate the account
	 */
	public void reinstate() {
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
			System.out.println("Reinstate: Fail to read account info.");
		}

		// Rewrite info
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (String a : arr) {
				// Change status
				if (a.split(" [|] ")[0].equals(accNo)) {
					a = a.substring(0, a.length() - 1) + 1;
				}

				bw.write(a);
				bw.newLine();
			}

			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("Reinstate: Fail to rewrite status of account.");
		}

		this.status = 1;
	}

	/**
	 * 
	 * @return close account state, 1 means having balance, 0 means success
	 */
	public int close() {
		File file = new File("./Account.txt");
		ArrayList<String> arr = new ArrayList<>();
		// If balance is not 0, return 1
		if (this.getBalance() != 0)
			return 1;

		// Read info
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;

			while ((s = br.readLine()) != null) {
				arr.add(s);
			}

			br.close();
		} catch (Exception e) {
			System.out.println("Close: Fail to read account info.");
		}

		// Rewrite info
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			// Rewrite others
			for (String a : arr) {
				if (!a.split(" [|] ")[0].equals(accNo)) {
					bw.write(a);
					bw.newLine();
				}
			}

			bw.flush();
			bw.close();
		} catch (Exception e) {
		}

		return 0;
	}

	/**
	 * Get account No.
	 * 
	 * @return account No.
	 */
	public String getAccountNo() {
		return accNo;
	}

	/**
	 * Get account name
	 * 
	 * @return account name
	 */
	public String getAccountName() {
		return accName;
	}

	/**
	 * Get account balance
	 * 
	 * @return account balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Get account type
	 * 
	 * @return account type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get account status
	 * 
	 * @return account status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * When open a new account, generate a new account No.
	 * 
	 * @return account No.
	 */
	public static String getNo() {
		File file = new File("./Account.txt");

		// If exist account, get latest account no.
		try {
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String s;

				while ((s = br.readLine()) != null) {
					nextNo = s.split(" [|] ")[0];
				}

				nextNo = "100012340000" + String.format("%03d", (Integer.parseInt(nextNo.substring(12)) + 1));
				br.close();
			}
		} catch (IOException e) {

		}

		String no = nextNo;
		// Set account no. to next available account no.
		nextNo = "100012340000" + String.format("%03d", (Integer.parseInt(no.substring(12)) + 1));
		System.out.println(nextNo);

		return no;
	}

}
