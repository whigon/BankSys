import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * This class is used to clear all un-cleared transactions.
 * @author LiYuexiang
 *
 */
public class ClearFund {
	
	private boolean clearState = false;
	
	/**
	 * The constructor
	 */
	public ClearFund() {
		clearState = clearFund();
	}
	
	/**
	 * Clear all un-cleared fund
	 * 
	 * @return clear state
	 */
	public boolean clearFund() {
		File unclear = new File("./Uncleared.txt");
		File file = new File("./account.txt");
		ArrayList<String> arr = new ArrayList<>();

		if (!unclear.exists())
			return true;

		try {
			String s;
			BufferedReader br = new BufferedReader(new FileReader(file));

			// Read previous information
			while ((s = br.readLine()) != null) {
				arr.add(s);
			}

			br.close();

			// Change information
			br = new BufferedReader(new FileReader(unclear));

			while ((s = br.readLine()) != null) {
				for (int i=0; i < arr.size(); i++) {
					if(arr.get(i).split(" [|] ")[0].equals(s.split(" [|] ")[0])) {
						// Get the information that will be changed
						String a = arr.get(i);
						double pre = Double.valueOf(a.split(" [|] ")[3]);
						double change = Double.valueOf(s.split(" [|] ")[1]);
						String front = a.split(" [|] ")[0] + " | " + a.split(" [|] ")[1] + " | " + a.split(" [|] ")[2];
						String back = a.split(" [|] ")[4] + " | " + a.split(" [|] ")[5] + " | " + a.split(" [|] ")[6] + " | " + a.split(" [|] ")[7] + " | " + a.split(" [|] ")[8];
						
						pre += change;
						
						arr.set(i, front + " | " +  pre + " | " + back);
					}
				}
			}

			br.close();
			unclear.delete();

			// Rewrite information
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));

			for (String a : arr) {
				bw.write(a);
				bw.newLine();
			}

			bw.flush();
			bw.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	/**
	 * Get clear state
	 * @return clear state
	 */
	public boolean getClearState() {
		return clearState;
	}
}
