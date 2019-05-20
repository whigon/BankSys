import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

/**
 * This class is used to create a new account.
 * 
 * @author LiYuexiang
 *
 */
public class CreateAccount {

	private JFrame frmCreate;
	private JTextField nameText;
	private JTextField addrText;
	private JTextField dobText;

	/**
	 * The constructor
	 */
	public CreateAccount() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreate = new JFrame();
		frmCreate.setTitle("CreateAccount");
		frmCreate.setBounds(100, 100, 800, 450);
		frmCreate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreate.getContentPane().setLayout(null);
		frmCreate.setVisible(true);

		JLabel lblCreateAccount = new JLabel("Create Account");
		lblCreateAccount.setFont(new Font("Arial", Font.BOLD, 16));
		lblCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateAccount.setBounds(300, 0, 200, 50);
		frmCreate.getContentPane().add(lblCreateAccount);

		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		nameLabel.setBounds(200, 60, 80, 30);
		frmCreate.getContentPane().add(nameLabel);

		JLabel addrLabel = new JLabel("Address:  ");
		addrLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		addrLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		addrLabel.setBounds(200, 100, 80, 30);
		frmCreate.getContentPane().add(addrLabel);

		JLabel dobLabel = new JLabel("Date of Birth: ");
		dobLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		dobLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dobLabel.setBounds(190, 140, 90, 30);
		frmCreate.getContentPane().add(dobLabel);

		JLabel typeLabel = new JLabel("Type: ");
		typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		typeLabel.setBounds(200, 180, 80, 30);
		frmCreate.getContentPane().add(typeLabel);

		nameText = new JTextField();
		nameText.setBounds(300, 60, 200, 30);
		frmCreate.getContentPane().add(nameText);
		nameText.setColumns(10);

		addrText = new JTextField();
		addrText.setColumns(10);
		addrText.setBounds(300, 100, 200, 30);
		frmCreate.getContentPane().add(addrText);

		dobText = new JTextField();
		dobText.setColumns(10);
		dobText.setBounds(300, 140, 200, 30);
		frmCreate.getContentPane().add(dobText);

		JComboBox<String> typeSelect = new JComboBox<>();
		typeSelect.setMaximumRowCount(3);
		typeSelect.addItem("Saver");
		typeSelect.addItem("Junior");
		typeSelect.addItem("Current");
		typeSelect.setBounds(300, 180, 200, 30);
		frmCreate.getContentPane().add(typeSelect);

		JLabel warning = new JLabel("");
		warning.setFont(new Font("Arial", Font.PLAIN, 14));
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		warning.setBounds(200, 250, 400, 30);
		frmCreate.getContentPane().add(warning);

		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Name
				String name = nameText.getText();
				// Address
				String addr = addrText.getText();
				// Date of birth
				Date dob = null;
				// Account type
				String type = "";
				double overdraft = 0;
				double balance = 20;
				int age = 0;
				int sel = typeSelect.getSelectedIndex();
				String s = dobText.getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				warning.setText("");

				// Convert dob input to date
				try {
					dob = sdf.parse(s);
					age = getAge(dob);
				} catch (Exception e1) {
					warning.setText("dob: YYYY-MM-DD");
				}

				// Select type
				if (sel == 0) {
					type = "Saver";
				} else if (sel == 1) {
					if (age < 16)
						type = "Junior";
					else
						warning.setText("You are beyond 16.");
				} else if (sel == 2) {
					overdraft = 200;
					type = "Current";
				}

				// Check Credit
				if (!type.equals("") && checkCredit(name)) {
					System.out.println("True");
					File file = new File("./Account.txt");

					try {
						if (!file.exists()) {
							file.getParentFile().mkdir();
							try {
								file.createNewFile();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);
						// Get bank account and PIN
						String no = BankAccount.getNo();
						String PIN = name.substring(0, 3) + "123";
						String info = no + " | " + PIN + " | " + type + " | " + balance + " | " + overdraft + " | "
								+ name + " | " + addr + " | " + dob + " | " + 1;
						// Write information into file
						bw.write(info);
						bw.newLine();

						bw.flush();
						bw.close();
						// Notice the new account no. and PIN
						warning.setText("<html>Your account is: " + no + "<br>" + "Your PIN is: " + PIN + "</html>");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					warning.setText("Fail to create account.");
				}

			}
		});
		create.setFont(new Font("Arial", Font.BOLD, 14));
		create.setBounds(300, 300, 200, 50);
		frmCreate.getContentPane().add(create);

		JLabel note = new JLabel("Note: You should credit 20\u00A3 to create a new account.");
		note.setHorizontalAlignment(SwingConstants.CENTER);
		note.setFont(new Font("Arial", Font.PLAIN, 12));
		note.setBounds(200, 350, 400, 30);
		frmCreate.getContentPane().add(note);

		JButton button = new JButton("back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Back to home
				new Main();
				frmCreate.dispose();
			}
		});
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(711, 393, 75, 20);
		frmCreate.getContentPane().add(button);
	}

	/**
	 * Check credit by name
	 * 
	 * @param name
	 *            people's name
	 * @return whether this people has good credit
	 */
	public boolean checkCredit(String name) {
		File file = new File("./BadCreditName.txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;

			while ((s = br.readLine()) != null) {
				if (s.equals(name))
					return false;
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Fail to open file");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail to read file");
		}

		return true;
	}

	/**
	 * Use date of birth to calculate age
	 * 
	 * @param dob
	 *            date of birth
	 * @return the age
	 * @throws Exception
	 *             illegal date of birth
	 */
	public int getAge(Date dob) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(dob)) {
			throw new IllegalArgumentException("The birthDay is before Now. It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(dob);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		// Calculate age
		int age = yearNow - yearBirth;

		// Check month
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}

		return age;
	}

}
