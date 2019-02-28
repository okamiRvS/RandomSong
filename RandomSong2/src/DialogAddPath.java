import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DialogAddPath implements ActionListener {
	private static JDialog d;
	private boolean flag = false;
	private static JTextField textField = new JTextField();

	DialogAddPath() {
		JFrame f = new JFrame();
		d = new JDialog(f, "Add Path", true);
		d.setSize(450, 150);
		d.setResizable(false);
		d.setLocationRelativeTo(null);
		d.setLayout(new FlowLayout());
		d.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Set Path");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		textField.setColumns(10);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(this);

		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					onButtonPress();
				}
			}
		});

		GroupLayout groupLayout = new GroupLayout(d.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnNewButton))
						.addGroup(groupLayout.createSequentialGroup().addGap(48)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)))
						.addGap(52)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(47)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton).addGap(26)));
		d.getContentPane().setLayout(groupLayout);

	}

	public void sV() {
		d.setVisible(true);
	}
	public boolean getFlag() {
		return flag;
	}
	public void setFlag() {
		flag = false;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add")) {
			onButtonPress();
		}
	}

	public void onButtonPress() {
		try {
			BufferedReader rw = new BufferedReader(new FileReader("file.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("file.txt", true));
			String a = textField.getText();
			String transfromed = a.replace('\\', '/');
			if (textField.getText().length() > 0 & check(transfromed + "/")) {
				if (rw.readLine() != null) {
					bw.newLine();
				}
				rw.close();
				bw.write(transfromed + "/");
				bw.close();
				flag = true;
				d.dispose();
			} else {
				System.out.println("It already exists");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public boolean check(String s) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("file.txt"));
			String currentLine = null;
			while ((currentLine = br.readLine()) != null) {
				if (currentLine.equalsIgnoreCase(s)) {
					br.close();
					return false;
				}
			}
			br.close();

		} catch (Exception e) {

		}
		return true;
	}
}