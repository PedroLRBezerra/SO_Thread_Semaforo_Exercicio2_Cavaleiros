package controller;

import java.awt.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread{

	private int idCavaleiro;
	private int possuiTocha;
	private int possuiPedra;
	private static boolean tochaFoiPega=false;
	private static boolean pedrafoiPega=false;
	private Semaphore  semaforoPorta;
	private static int idPortaCerta=(int)(Math.random()*4);
	private static ArrayList<Integer> portas;
	private int portaEscolhida;
	
	public ThreadCavaleiro(int idc) {
		this.idCavaleiro=idc;
		this.possuiTocha=0;
		this.possuiPedra=0;
//		this.tochaFoiPega=false;
//		this.pedrafoiPega=false;
//		this.idCavaleiroporta = new Semaphore[4];
//		this.idCavaleiroporta[0]=new Semaphore(1);
//		this.idCavaleiroporta[1]=new Semaphore(1);
//		this.idCavaleiroporta[2]=new Semaphore(1);
//		this.idCavaleiroporta[3]=new Semaphore(1);
		this.semaforoPorta=new Semaphore(1);
//		this.idPortaCerta=(int)(Math.random()*4);
		
		 this.portas= new ArrayList<Integer>();
		 portas.add(0);
		 portas.add(1);
		 portas.add(2);
		 portas.add(3);
		
	}
	
	@Override
	public void run() {
		cavaleiroAndando(0,500);
		System.out.println("Cavaleiro #"+idCavaleiro+" Chegou no Local da Tocha");
		pegarTocha();
		cavaleiroAndando(500,1500);
		System.out.println("Cavaleiro #"+idCavaleiro+" Chegou no Local da Pedra");
		pegarPedra();
		cavaleiroAndando(1500,2000);
		System.out.println("Cavaleiro #"+idCavaleiro+" Chegou nas Portas");
		this.portaEscolhida=escolherPorta();
		System.out.println("Cavaleiro #"+idCavaleiro+" Escolheu a porta "+portaEscolhida);
		try {
			semaforoPorta.acquire();
			EntrarPorta();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforoPorta.release();
		}
		
	}
	
	private void cavaleiroAndando(int distInicial,int distFinal) {
		int distPerocrrida=distInicial;
		
		int tempo = 50;
		while(distPerocrrida<distFinal) {
			int desloc = (((int)((Math.random()*3)+2))+(2*this.possuiTocha)+(2*this.possuiPedra));
			distPerocrrida +=desloc;
			try {
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Cavaleiro #"+idCavaleiro+" Andou "+distPerocrrida+" m.");
		}
		
	}
	
	private void pegarTocha() {
		if(this.tochaFoiPega==false) {
			this.possuiTocha++;
			this.tochaFoiPega=true;
			System.out.println("O Cavaleiro #"+idCavaleiro+" Pegou a Tocha");
		}
	}
	
	private void pegarPedra() {
		if(this.pedrafoiPega==false) {
			this.possuiPedra++;
			this.pedrafoiPega=true;
			System.out.println("O Cavaleiro #"+idCavaleiro+" Pegou a Pedra");
		}
	}
	private int escolherPorta() {
		int portaEscolhida,idPortaEscolhida=(int)(Math.random()*this.portas.size());
		portaEscolhida=this.portas.get(idPortaEscolhida);
		this.portas.remove(idPortaEscolhida);
		return portaEscolhida;
	}
	
	private void EntrarPorta() {
		if(this.portaEscolhida==this.idPortaCerta) {
			System.out.println("O Cavaleiro #"+idCavaleiro+" Escapou");
		}else {
			System.out.println("O Cavaleiro #"+idCavaleiro+" Foi Morto");
		}
	}
	
}
