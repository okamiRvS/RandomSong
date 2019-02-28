import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SongPlayer {
	// PARAMETRI
	private String PATH = "E:/Umberto/Musica/Elettronica/";
	private ArrayList<String> PATHS;
	private HashMap<Integer, String> results = new HashMap<>();
	private String currentTrack;
	private int random;
	Player player;

	public SongPlayer() {
		configu();
	}

	public SongPlayer(String path) {
		this.PATH = path;
		configu();
	}

	public SongPlayer(ArrayList<String> paths) {
		this.PATHS = paths;
		for (int i = 0; i < PATHS.size(); i++) {
			System.out.println(PATHS.get(i));
		}
		configuS();
	}

	private void configu() {
		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles();
		int cont = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() & listOfFiles[i].getName().endsWith(".mp3")) {
				results.put(cont, PATH + listOfFiles[i].getName());
				cont++;
			} else if (listOfFiles[i].isDirectory()) {
				// System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}

	private void configuS() {
		int cont = 0;
		for (int j = 0; j < PATHS.size(); j++) {
			File folder = new File(PATHS.get(j));
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() & listOfFiles[i].getName().endsWith(".mp3")) {
					results.put(cont, PATHS.get(j) + listOfFiles[i].getName());
					cont++;
				} else if (listOfFiles[i].isDirectory()) {
					// System.out.println("Directory " +
					// listOfFiles[i].getName());
				}
			}
		}
	}

	public String getPath() {
		return PATH;
	}

	public String getSongs() {
		Set<Integer> s = results.keySet();
		System.out.println(s);

		String data = "";
		Collection<String> tracksName = results.values();
		System.out.println(tracksName);

		Iterator<String> scorro = tracksName.iterator();
		while (scorro.hasNext()) {
			data = data + scorro.next() + "\n";
		}
		return data;

	}

	public String getcurrentTrack() {
		random();
		return currentTrack;
	}

	public void random() {
		random = (int) (Math.random() * results.size());
		currentTrack = "[" + random + "] - " + results.get(random).substring(results.get(random).lastIndexOf("/") + 1);
	}

	public void Stampa() {
		System.out.println("ALL SONGS OF THE DIRECTORY");
		for (int i = 0; i < results.size(); i++) {
			System.out.println("[" + i + "]: " + results.get(i));
		}
		System.out.println(" ");
	}

	public void play() {
		System.out.println("Song is playing...");
		try {
			FileInputStream fileInputStream = new FileInputStream(results.get(random));
			player = new Player(fileInputStream);
			System.out.println(currentTrack);
			player.play();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}

	}

	public void closed() {
		try {
			player.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
