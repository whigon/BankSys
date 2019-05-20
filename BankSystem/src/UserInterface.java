import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class is used to describe user interface.
 * 
 * @author LiYuexiang
 *
 */
public class UserInterface {

	private JFrame frmAccount;
	// Logged in account
	private BankAccount account;
	private JTextField textField;
	private JLabel warning;

	/**
	 * The constructor 
	 * 
	 * @param account
	 *            logged in account
	 */
	public UserInterface(BankAccount account) {
		this.account = account;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAccount = new JFrame();
		frmAccount.setTitle("Account");
		frmAccount.setBounds(100, 100, 800, 450);
		frmAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAccount.getContentPane().setLayout(null);

		JLabel accNoLabel = new JLabel("" + account.getAccountNo());
		accNoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		accNoLabel.setBounds(120, 10, 100, 30);
		frmAccount.getContentPane().add(accNoLabel);

		JLabel nameLabel = new JLabel("" + account.getAccountName());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(230, 10, 100, 30);
		frmAccount.getContentPane().add(nameLabel);

		JLabel balanceLabel = new JLabel("" + account.getBalance());
		balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		balanceLabel.setBounds(340, 10, 100, 30);
		frmAccount.getContentPane().add(balanceLabel);

		JLabel overdraftLabel = new JLabel();
		if (account.getType().equals("Current")) {
			CurrentAccount ca = (CurrentAccount) account;
			overdraftLabel.setText("" + ca.getOverdraft());
		} else
			overdraftLabel.setText("0");
		overdraftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		overdraftLabel.setBounds(450, 10, 100, 30);
		frmAccount.getContentPane().add(overdraftLabel);

		JLabel statusLabel = new JLabel();
		if (account.getStatus() == 1)
			statusLabel.setText("Activated");
		else
			statusLabel.setText("Suspended");
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setBounds(560, 10, 100, 30);
		frmAccount.getContentPane().add(statusLabel);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(300, 80, 200, 70);
		frmAccount.getContentPane().add(textField);
		textField.setColumns(10);

		// Withdraw button
		JButton btnWithdraw = new JButton("withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				warning.setText("");
				// Check account status first
				if (account.getStatus() == 0) {
					warning.setText("Your account is suspended.");
				} else {
					double amount;
					int state;
					try {
						amount = Double.valueOf(textField.getText());
						state = account.withdraw(amount);
						// Check withdraw state
						if (state == 0) {
							warning.setText("Success.");
							// Change balance display
							balanceLabel.setText("" + account.getBalance());
							// Change overdraft display
							if (account.getType().equals("Current")) {
								CurrentAccount ca = (CurrentAccount) account;
								overdraftLabel.setText("" + ca.getOverdraft());
							}
						} else if (state == 1) {
							warning.setText("Insufficient balance.");
						} else if (state == 2) {
							warning.setText("Make a notice.");
						}
					} catch (Exception e1) {
						warning.setText("Please enter a number.");
					}

				}
			}
		});
		btnWithdraw.setFont(new Font("Arial", Font.PLAIN, 14));
		btnWithdraw.setBounds(60, 220, 150, 60);
		frmAccount.getContentPane().add(btnWithdraw);

		// Deposit button
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				warning.setText("");
				// Check account state first
				if (account.getStatus() == 0) {
					warning.setText("Your account is suspended.");
				} else {
					// Select a way to deposit money
					String type;
					Object[] possibleValues = { "Cash", "Cheque" };
					Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Deposit",
							JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
					type = (String) selectedValue;

					System.out.println(type);

					if (type != null) {
						try {
							// Deposit money
							double amount = Double.valueOf(textField.getText());
							// Set result
							warning.setText(account.deposit(amount, type));
							// Change balance display
							balanceLabel.setText("" + account.getBalance());
							if (account.getType().equals("Current")) {
								CurrentAccount ca = (CurrentAccount) account;
								overdraftLabel.setText("" + ca.getOverdraft());
							}
						} catch (Exception e1) {
							warning.setText("Please enter a number.");
						}
					}
				}
			}
		});
		btnDeposit.setFont(new Font("Arial", Font.PLAIN, 14));
		btnDeposit.setBounds(590, 220, 150, 60);
		frmAccount.getContentPane().add(btnDeposit);

		// Change status button
		JButton changeStatus = new JButton();
		if (account.getStatus() == 1)
			changeStatus.setText("suspend account");
		else
			changeStatus.setText("activat account");
		changeStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Change account status and button display
				if (account.getStatus() == 1) {
					account.suspend();
					statusLabel.setText("Suspended");
					changeStatus.setText("activat account");
				} else {
					account.reinstate();
					statusLabel.setText("Activated");
					changeStatus.setText("suspend account");
				}
			}
		});
		changeStatus.setFont(new Font("Arial", Font.PLAIN, 14));
		changeStatus.setBounds(300, 220, 200, 60);
		frmAccount.getContentPane().add(changeStatus);

		warning = new JLabel("");
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		warning.setBounds(560, 80, 180, 70);
		frmAccount.getContentPane().add(warning);

		JButton button = new JButton("back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Back to home
				new Main();
				frmAccount.dispose();
			}
		});
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(711, 393, 75, 20);
		frmAccount.getContentPane().add(button);

		JButton closeButton = new JButton("close account");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Close account state
				int state = account.close();
				// Check state
				if (state == 0) {
					// Show message
					JOptionPane.showMessageDialog(null, "Success", "CloseAccount", JOptionPane.DEFAULT_OPTION);

					// Back to home
					new Main();
					frmAccount.dispose();
				} else if (state == 1) {
					warning.setText("<html>Please withdraw your balance first.</html>");
				} else if (state == 2) {
					warning.setText("<html>Please pay your overdraft first.</html>");
				}
			}
		});
		closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
		closeButton.setBounds(300, 314, 200, 70);
		frmAccount.getContentPane().add(closeButton);
		frmAccount.setVisible(true);
	}
}
