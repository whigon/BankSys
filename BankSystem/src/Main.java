import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * This class is used to launch the program and describes the home GUI.
 * 
 * @author LiYuexiang
 *
 */
public class Main {

	private JFrame frmHome;
	private JTextField text;

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHome = new JFrame();
		frmHome.setTitle("HOME");
		frmHome.setBounds(100, 100, 800, 450);
		frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHome.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(300, 0, 200, 50);
		frmHome.getContentPane().add(lblNewLabel);

		text = new JTextField();
		text.setEditable(false);
		text.setFont(new Font("Arial", Font.PLAIN, 14));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setBounds(300, 60, 200, 50);
		frmHome.getContentPane().add(text);
		text.setColumns(10);
		frmHome.setVisible(true);

		// Clear fund button
		JButton btnClearFund = new JButton("CLEAR FUND");
		btnClearFund.setFont(new Font("Arial", Font.PLAIN, 14));
		btnClearFund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Clear un-cleared fund
				ClearFund cf = new ClearFund();
				// Check state
				if (cf.getClearState()) {
					text.setText("Success clear fund.");
				} else {
					text.setText("Fail to clear fund.");
				}
			}
		});
		btnClearFund.setBounds(300, 140, 200, 50);
		frmHome.getContentPane().add(btnClearFund);

		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Login
				new Login();
				frmHome.dispose();
			}
		});
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 14));
		btnLogin.setBounds(300, 220, 200, 50);
		frmHome.getContentPane().add(btnLogin);

		JButton btnNewButton = new JButton("OPEN ACCOUNT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a new account
				new CreateAccount();
				frmHome.dispose();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton.setBounds(300, 300, 200, 50);
		frmHome.getContentPane().add(btnNewButton);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Start the program
		Main start = new Main();
	}

}
