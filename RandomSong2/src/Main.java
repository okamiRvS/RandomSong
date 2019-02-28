import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Main extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	static SongPlayer songplayer = new SongPlayer();
	static JButton button = new JButton("STOP");
	static JButton button2 = new JButton("NEXT");
	static JLabel lblNewLabel = new JLabel("NULL");
	static JMenuBar bar = new JMenuBar();
	static JMenu songs = new JMenu("Songs");
	static DialogAddPath s = new DialogAddPath();
	static DialogDeletePath del = new DialogDeletePath();
	static Main tk = new Main();

	public static void main(String[] args) throws InterruptedException {
		// checking for support
		if (!SystemTray.isSupported()) {
			System.out.println("System tray is not supported !!! ");
			return;
		}
		// get the systemTray of the system
		SystemTray systemTray = SystemTray.getSystemTray();

		// get default toolkit
		// Toolkit toolkit = Toolkit.getDefaultToolkit();
		// get image
		// Toolkit.getDefaultToolkit().getImage("src/resources/busylogo.jpg");
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/1.jpg");

		// popupmenu
		PopupMenu trayPopupMenu = new PopupMenu();

		// 1t menu item for popup menu
		MenuItem action = new MenuItem("Open");

		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tk.setVisible(true);
				tk.setExtendedState(JFrame.NORMAL);
				// JOptionPane.showMessageDialog(null, "Action Clicked");
			}
		});
		trayPopupMenu.add(action);

		// 2nd menu item of popup menu
		MenuItem close = new MenuItem("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		trayPopupMenu.add(close);

		// setting tray icon
		TrayIcon trayIcon = new TrayIcon(image, "Random Songs Player", trayPopupMenu);
		// adjust to default size as per system recommendation
		trayIcon.setImageAutoSize(true);
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if(me.getClickCount()>1) {
					tk.setVisible(true);
					tk.setExtendedState(JFrame.NORMAL);
				}
			}
		});

		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}

		// start music
		while (button.getText() == "STOP") {
			start();
		}
	}

	private Main() {
		super("Random Songs Player");
		setSize(500, 170);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWindowListener(new FrameListener());

		JMenu file = new JMenu("File");
		JMenuItem addPath = new JMenuItem("Add Path");
		JMenuItem deletePath = new JMenuItem("Delete Path/s");
		JMenuItem close = new JMenuItem("Close");

		file.add(addPath);
		file.add(deletePath);
		file.addSeparator();
		file.add(close);
		bar.add(file);
		setJMenuBar(bar);

		JMenuItem all = new JMenuItem("All");
		songs.add(all);
		all.addActionListener(this);

		String sCurrentLine;
		String out;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("file.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				out = sCurrentLine;
				JMenuItem newPack = new JMenuItem(out);
				newPack.addActionListener(this);
				songs.add(newPack);
			}
			br.close();
		} catch (Exception e) {

		}

		bar.add(songs);

		button.addActionListener(this);
		button2.addActionListener(this);
		addPath.addActionListener(this);
		deletePath.addActionListener(this);
		close.addActionListener(this);

		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(this.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap(90, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(button2, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))
				.addGap(90)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(5)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addComponent(button2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblNewLabel)
						.addContainerGap(26, Short.MAX_VALUE)));
		this.getContentPane().setLayout(groupLayout);
	}

	class FrameListener extends WindowAdapter {
		public void windowIconified(WindowEvent e) {
			tk.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("NEXT")) {
			songplayer.closed();
		} else if (e.getActionCommand().equals("STOP")) {
			System.out.println("You have stopped");
			button.setText("PLAY");
			songplayer.closed();

		} else if (e.getActionCommand().equals("PLAY")) {
			System.out.println("You have played");
			button.setText("STOP");
			Thread t = new Thread(() -> {
				while (button.getText() == "STOP") {
					start();
				}
			});
			t.start();
		} else if (e.getActionCommand().equals("Close")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("Add Path")) {
			s.sV();
			if (s.getFlag() == true) {
				button.setText("PLAY");
				songplayer.closed();
				String out = "";
				try {
					BufferedReader br = new BufferedReader(new FileReader("file.txt"));
					String sCurrentLine;
					while ((sCurrentLine = br.readLine()) != null) {
						// System.out.println(sCurrentLine);
						out = sCurrentLine;
					}

					br.close();

					JMenuItem newPack = new JMenuItem(out);
					songs.add(newPack);
					newPack.addActionListener(this);

					songplayer = new SongPlayer(out);
					button.setText("STOP");
					Thread t = new Thread(() -> {
						while (button.getText() == "STOP") {
							start();
						}
					});
					t.start();
					s.setFlag();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
				}
			}
		} else if (e.getActionCommand().equals("Delete Path/s")) {
			try {
				BufferedReader br = null;
				ArrayList<String> s = new ArrayList<String>();
				br = new BufferedReader(new FileReader("file.txt"));
				String sCurrentLine = "";
				while ((sCurrentLine = br.readLine()) != null) {
					// System.out.println(sCurrentLine);
					s.add(sCurrentLine);
				}
				br.close();
				del.setArray(s);
				del.initialize();
				if (del.getFlag()) {
					songs.removeAll();
					JMenuItem all = new JMenuItem("All");
					all.addActionListener(this);
					songs.add(all);
					for (int i = 0; i < del.song().size(); i++) {
						JMenuItem newPack = new JMenuItem(del.song().get(i));
						newPack.addActionListener(this);
						songs.add(newPack);
					}
				}
				del.setFlag();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("All")) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("file.txt"));
				ArrayList<String> results = new ArrayList<String>();
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					results.add(sCurrentLine);
					// System.out.println(sCurrentLine);
				}
				button.setText("PLAY");
				songplayer.closed();
				songplayer = new SongPlayer(results);
				button.setText("STOP");
				Thread t = new Thread(() -> {
					while (button.getText() == "STOP") {
						start();
					}
				});
				t.start();

				br.close();
			} catch (Exception e1) {

			}
		} else {
			try {
				BufferedReader br = new BufferedReader(new FileReader("file.txt"));
				String sCurrentLine = "";
				while ((sCurrentLine = br.readLine()) != null) {
					// System.out.println(sCurrentLine);
					if (e.getActionCommand().equals(sCurrentLine)) {
						button.setText("PLAY");
						songplayer.closed();
						songplayer = new SongPlayer(sCurrentLine);
						button.setText("STOP");
						Thread t = new Thread(() -> {
							while (button.getText() == "STOP") {
								start();
							}
						});
						t.start();
					}
				}
				br.close();
			} catch (Exception e1) {

			}
		}
	}

	public static void setSize() {
		String lab = lblNewLabel.getText().substring(0, lblNewLabel.getText().length() - 4);
		if (lab.length() > 50) {
			lblNewLabel.setText(lab.substring(0, 50) + "...");
		} else {
			lblNewLabel.setText(lab);
		}
	}

	public static void start() {
		lblNewLabel.setText(songplayer.getcurrentTrack());
		setSize();
		songplayer.play();
	}

	public void addPath() {
		try {
			songplayer.getSongs();

			BufferedReader br = new BufferedReader(new FileReader("file.txt"));
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
