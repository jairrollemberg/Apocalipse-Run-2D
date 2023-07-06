import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JPanel;

public class Game extends JPanel {

	// ATRIBUTOS..................
	public static Recursos recursos;

	public Jhon jhon;
	public Dog dog;
	public GeradorInimigos geradorInimigos;
	public Piso piso;
	public Cenario cenario;

	// os estados de pressionamento das teclas direcionais
	public boolean k_cima = false;
	

	// variáveis de tempo
	public long tempoAtual, tempoAnterior, tempoDelta;

	// Estado do jogo
	public Estado ESTADO;
	long tempoDecorridoGameOver, tempoJogo, tempoJogoFinal, tempoGameOver, tempoCarregando;

	// CONSTRUTOR ---------------------------------------------------------
	public Game() {
		ESTADO = Estado.CARREGANDO;
		recursos = new Recursos();
		// adiciona listener de eventos de pressionamento do teclado
		requestFocus();
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_SPACE: // tecla espaço
						k_cima = true;
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) { 
				switch (e.getKeyCode()) {
					case KeyEvent.VK_SPACE: // tecla espaço
						k_cima = false;
						break;
					case KeyEvent.VK_Q:
						ESTADO = Estado.EXECUTANDO;
						break;
				}
			}
		});

		jhon = new Jhon();
		dog = new Dog();
		geradorInimigos = new GeradorInimigos();
		piso = new Piso();
		cenario = new Cenario();

		tempoDecorridoGameOver = 0;
		tempoJogo = 0;
		tempoGameOver = 2000;
		tempoCarregando = 2000;
		// para poder tratar eventos
		setFocusable(true); 
		setLayout(null);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				gameloop(); // dispara o gameloop do jogo
			}
		});
		thread.start();
	}

	// Metodod do GameLoop ........
	public void gameloop() {
		tempoAnterior = System.currentTimeMillis();
		while (true) {
			tempoAtual = System.currentTimeMillis(); // tempo inicial desse quadro
			tempoDelta = tempoAtual - tempoAnterior; // quanto tempo se passou desde o ultimo quadro
			tempoJogo += tempoDelta;

			handlerEvent();
			update(tempoDelta);
			render();
			// tempo inicial do quadro anterior
			tempoAnterior = tempoAtual; 

			try {//uma pausa de 17 milisegundos (60FPS)
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void handlerEvent() {
		if (ESTADO == Estado.EXECUTANDO) {
			if (k_cima && jhon.estado != Jhon.Estado.PULANDO) {
				jhon.iniciarPulo();
			}
		}
	}

	public void update(long tempoDelta) {
		if (ESTADO == Estado.EXECUTANDO) {
			piso.update();
			// atualiza o cenario
			cenario.update();
			// atualiza Jhon
			jhon.update();
			jhon.mudarQuadro(tempoDelta);
			// atualiza o Dog
			dog.update();
			dog.mudarQuadro(tempoDelta);
			// atualiza os inimigos
			geradorInimigos.update(tempoDelta);

			testeColisoes();
		} else if (ESTADO == Estado.GAMEOVER) {
			tempoDecorridoGameOver += tempoDelta;
			if (tempoDecorridoGameOver >= tempoGameOver) {
				tempoDecorridoGameOver = 0;
				reiniciaJogo();
			}
		} else if (ESTADO == Estado.CARREGANDO) {
			if (tempoJogo > tempoCarregando) {
				reiniciaJogo();
			}
		}
	}

	public void render() {
		repaint(); 
	}
	// METODOS .........
	public void testeColisoes() {
		// verifica a colisão de jhon com o chão
		if (jhon.estado == Jhon.Estado.PULANDO &&
				(jhon.posY + jhon.pulandoAltura) >= jhon.posY_inicial + jhon.pulandoAltura) {
			jhon.pararPulo();
		}

		// verifica a colisão de jhon com o morcego
		for (Morcego m : geradorInimigos.listaMorcego) {
			if (jhon.colX + jhon.colLargura >= m.colX // lado esquerdo do morcego
					&& jhon.colX <= m.colX + m.colLargura // lado direito do morcego
					&& jhon.colY + jhon.colAltura >= m.colY // lado superior do morcego
					&& jhon.colY <= m.colY + m.colAltura // lado inferior do morcego
			) {
				ESTADO = Estado.GAMEOVER;
				jhon.estado = Jhon.Estado.MORTO;
				tempoJogoFinal = tempoJogo;
			}
		}

		// verifica a colisão de jhon com o zumbi
		for (Zumbi m : geradorInimigos.listaZumbi) {
			if (jhon.colX + jhon.colLargura >= m.colX // lado esquerdo do zumbi
					&& jhon.colX <= m.colX + m.colLargura // lado direito do zumbi
					&& jhon.colY + jhon.colAltura >= m.colY // lado superior do zumbi
					&& jhon.colY <= m.colY + m.colAltura // lado inferior do zumbi
			) {
				ESTADO = Estado.GAMEOVER;
				jhon.estado = Jhon.Estado.MORTO;
				tempoJogoFinal = tempoJogo;

			}
		}
	}

	// METODOS ESPECIAIS ..........

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(recursos.fontTexto);
		g.setColor(Color.WHITE);

		if (ESTADO == Estado.CARREGANDO) {
			g.drawImage(recursos.telaLoading, 0, 0, null);
			return;
		}

		
		g.drawImage(cenario.cenario1, (int) cenario.cenario1PosX, (int) cenario.cenario1PosY, null);
		g.drawImage(cenario.cenario2, (int) cenario.cenario2PosX, (int) cenario.cenario1PosY, null);
		geradorInimigos.render(g); 
		jhon.render(g);
		dog.render(g);
		piso.render(g);

		if (ESTADO == Estado.GAMEOVER) {
			g.drawImage(recursos.telaGameOver, 0, 0, null);
			g.drawString("Tempo: " + (tempoJogoFinal / 1000.00) + "s", (int) (Principal.LARGURA_TELA * 0.36),
					(int) (Principal.ALTURA_TELA * 0.75));
			if (tempoJogoFinal > recursos.recorde) {
				g.drawString("Maior Pontuação! Parabéns!!", (int) (Principal.LARGURA_TELA * 0.36), (int) (Principal.ALTURA_TELA * 0.85));
			}
		} else {
			g.drawString("Tempo: " + tempoJogo / 1000.0, 10, 30);
			g.drawString("Recorde: " + (recursos.recorde / 1000.0) + "s", 10, 70);
		}

	}

	public void reiniciaJogo() {
		ESTADO = Estado.EXECUTANDO;
		verificaRecorde();
		Game.recursos.velocidadeJogo = 1.0;
		tempoJogo = 0;
		tempoJogoFinal = 0;
		jhon.reiniciaJogo();
		dog.reiniciaJogo();
		geradorInimigos.reiniciaJogo();
		piso.reiniciaJogo();
		cenario.reiniciaJogo();
	}
	//Faz a verificaçao se o recorde foi batido
	public void verificaRecorde() {
		if (tempoJogoFinal > recursos.recorde) {
			recursos.recorde = tempoJogoFinal;
			salvarRecorde();
		}
	}
	//Salva o recorde em arquivo txt
	public void salvarRecorde() {
		BufferedWriter writer;
		try {
			String arquivo = "recorde.txt";
			writer = new BufferedWriter(new FileWriter(arquivo));
			writer.write("" + recursos.recorde);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public enum Estado {
		CARREGANDO, EXECUTANDO, GAMEOVER;
	}
}
