package view;

import controller.ThreadCavaleiro;

public class Main {
	public static void main(String [] args) {
	for(int idCavaleiro=0;idCavaleiro<4;idCavaleiro++) {
		Thread cavaleiro = new ThreadCavaleiro(idCavaleiro);
		cavaleiro.start();
	}
}
}
