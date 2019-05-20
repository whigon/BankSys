import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.awt.event.ActionEvent;

/**
 * This class is used to login.
 * 
 * @author LiYuexiang
 *
 */
public class Login {

	private JFrame frmLogin;
	private JTextField accountNoText;
	private JTextField PINText;
	// The login account
	private BankAccount account;

	/**
	 * The constructor
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 800, 450);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		frmLogin.setVisible(true);

		JLabel lblCreateAccount = new JLabel("LOGIN");
		lblCreateAccount.setFont(new Font("Arial", Font.BOLD, 16));
		lblCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateAccount.setBounds(300, 0, 200, 50);
		frmLogin.getContentPane().add(lblCreateAccount);

		JLabel accountNoLabel = new JLabel("AccountNo: ");
		accountNoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		accountNoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		accountNoLabel.setBounds(200, 60, 80, 30);
		frmLogin.getContentPane().add(accountNoLabel);

		JLabel PINLabel = new JLabel("PIN: ");
		PINLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		PINLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		PINLabel.setBounds(200, 90, 80, 30);
		frmLogin.getContentPane().add(PINLabel);

		accountNoText = new JTextField();
		accountNoText.setBounds(300, 60, 200, 30);
		frmLogin.getContentPane().add(accountNoText);
		accountNoText.setColumns(10);

		PINText = new JTextField();
		PINText.setColumns(10);
		PINText.setBounds(300, 95, 200, 30);
		frmLogin.getContentPane().add(PINText);

		JLabel warning = new JLabel("");
		warning.setFont(new Font("Arial", Font.PLAIN, 14));
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		warning.setBounds(200, 150, 400, 50);
		frmLogin.getContentPane().add(warning);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Account no.
				String accountNo = accountNoText.getText();
				// PIN
				String pin = PINText.getText();
				String info = "";

				File file = new File("./Account.txt");
				// Read information from file
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String s;

					while ((s = br.readLine()) != null) {
						// Check account No. and PIN
						if (s.split(" [|] ")[0].equals(accountNo) && s.split(" [|] ")[1].equals(pin)) {
							info = "" + s;
							break;
						}
					}

					br.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// If both account No. and PIN are correct, then login
				if (info.equals("")) {
					warning.setText("Fail to login.");
				} else {
					String accNo = info.split(" [|] ")[0];
					String accName = info.split(" [|] ")[5];
					double balance = Double.valueOf(info.split(" [|] ")[3]);

					System.out.println(info.split(" [|] ")[2]);

					// Select the account type to login
					if (info.split(" [|] ")[2].equals("Saver")) {
						account = new SaverAccount(accNo, accName, balance);
					} else if (info.split(" [|] ")[2].equals("Junior")) {
						account = new JuniorAccount(accNo, accName, balance);
					} else if (info.split(" [|] ")[2].equals("Current")) {
						double overdraft = Double.valueOf(info.split(" [|] ")[4]);
						account = new CurrentAccount(accNo, accName, balance, overdraft);
					}
					// Enter into user interface
					new UserInterface(account);
					frmLogin.dispose();
				}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));
		btnNewButton.setBounds(300, 210, 200, 50);
		frmLogin.getContentPane().add(btnNewButton);

		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Back to home
				new Main();
				frmLogin.dispose();
			}
		});
		btnBack.setFont(new Font("Arial", Font.PLAIN, 12));
		btnBack.setBounds(711, 393, 75, 20);
		frmLogin.getContentPane().add(btnBack);
	}

}
