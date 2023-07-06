import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

/* Representa um dos inimigos do jogo */
public class Morcego {

	// variáveis das imagens
	public BufferedImage[] correndoQuadros;
	public BufferedImage correndoSprite;
	public int correndoQtdQuadros, correndoLargura, correndoAltura;
	public int correndoTempoQuadro;
	public int correndoIndexAtual;

	public double posX, posY, velX; // Variáveis de posição e velocidade
	public double colX, colY, colLargura, colAltura;
	Color colColor;

	long tempoDecorrido;

	public Morcego() {
		correndoSprite = Game.recursos.spriteMorcego;
		correndoQtdQuadros = 16;
		correndoQuadros = new BufferedImage[correndoQtdQuadros];
		correndoLargura = 102;
		correndoAltura = 100;
		correndoIndexAtual = 0;
		correndoTempoQuadro = 100;

		reposicionar();

		colLargura = 72;
		colAltura = 80;
		colColor = new Color(255, 0, 0, 128); 

		velX = -9;
		tempoDecorrido = 0;

		// recorte dos sprites ....................
		for (int i = 0; i < correndoQtdQuadros; i++) {
			int x1 = correndoLargura * i;
			int y1 = 0;
			correndoQuadros[i] = correndoSprite.getSubimage(x1, y1, correndoLargura, correndoAltura);
		}
	}

	public void reposicionar() {
		posX = 1000;
		posY = 210;
		colX = posX + 13;
		colY = posY + 8;
	}

	public void update() {
		velX = -7 * Game.recursos.velocidadeJogo;
		posX = posX + velX;
		colX = colX + velX;
	}

	public void render(Graphics g) {
		g.drawImage(obterQuadro(), (int) posX, (int) posY, null);
		
	}

	public void mudarQuadro(long tempoDelta) {
		tempoDecorrido += tempoDelta;

		if (tempoDecorrido > correndoTempoQuadro) {
			correndoIndexAtual++;
			if (correndoIndexAtual >= correndoQtdQuadros)
				correndoIndexAtual = 0;
			tempoDecorrido = 0;
		}
	}

	public BufferedImage obterQuadro() {
		return correndoQuadros[correndoIndexAtual];
	}

	public void reiniciaJogo() {
		correndoIndexAtual = 0;
		reposicionar();
	}

	public enum Estado {
		CORRENDO, PULANDO;
	}
}
