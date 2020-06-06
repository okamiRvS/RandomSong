import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DialogDeletePath implements ActionListener{
	private static JDialog d;
	private boolean flag = false;
	private ArrayList<String> mem = null;
	private static JComboBox<String> comboBox = new JComboBox<String>();

	DialogDeletePath() {

		JFrame frame = new JFrame();
		d = new JDialog(frame, "Delete Path/s", true);
		d.setResizable(false);
		d.setSize(430, 150);
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		comboBox.addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent e) {
		    	System.out.println("ho cliccato enter");
		         if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		              onButtonPress();
		         }
		    } 
		 });
	
	}
	
	public void initialize() {
		comboBox.removeAllItems();
		for (int i = 0; i < mem.size(); i++) {
			comboBox.addItem(mem.get(i));
		}
		
		JLabel label1 = new JLabel("Path:");
		
		JButton deleteButton = new JButton("Delete");

		deleteButton.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(d.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(
										deleteButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(30)
										.addComponent(label1, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBox,
												GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)))
								.addGap(50)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addGap(30)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label1, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
								.addComponent(comboBox, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE).addGap(25)));
		d.getContentPane().setLayout(groupLayout);
		d.setVisible(true);

	}
	
	public void setArray(ArrayList<String> s) {
		mem = s;
	}
	public void actionPerformed (ActionEvent e) {
	    if (e.getActionCommand().equals("Delete")){
			 System.out.println("ho cliccato delete");
	        onButtonPress();
	     } 
	  }

	 private void onButtonPress(){
		 BufferedReader br = null;
			String remove = null;
			String path = "file.txt";
			try {
				br = new BufferedReader(new FileReader("file.txt"));
				String ca = comboBox.getSelectedItem().toString();
				while ((remove = br.readLine()) != null & !(remove.equalsIgnoreCase(ca))) {
				}
				if (remove.length() > 0) {
					br.close();
					comboBox.removeAllItems();
					int save = 0;
					for (int i = 0; i < mem.size(); i++) {
						if (!(mem.get(i).equalsIgnoreCase(remove))) {
							comboBox.addItem(mem.get(i));
						} else {
							save = i;
						}
					}
					mem.remove(save);
					if (removeALine(path)) {
						System.out.println("Andato tutto a buon fine");
					}
					flag = true;
				}

			} catch (Exception e1) {

			}
	 }

	public boolean getFlag() {
		return flag;
	}
	
	public void setFlag() {
		flag = false;
	}

	public ArrayList<String> song() {
		return mem;
	}

	private boolean removeALine(String path) throws FileNotFoundException {
		File inputFile = new File(path);
		BufferedWriter writer;
		new PrintWriter(path).close();
		boolean fl = false;
		try {
			writer = new BufferedWriter(new FileWriter(inputFile));
			for (int i = 0; i < mem.size(); i++) {
				if (fl) {
					writer.newLine();
				}
				writer.write(mem.get(i));
				fl = true;
			}

			writer.close();
		} catch (Exception e) {

		}
		return true;
	}
}