import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

/* Representa um dos inimigos do jogo */
public class Dog { 

	// variáveis das imagens
	public BufferedImage[] correndoQuadros;
	public BufferedImage correndoSprite;
	public int correndoQtdQuadros, correndoLargura, correndoAltura;
	public int correndoTempoQuadro;
	public int correndoIndexAtual;

	public double posX, posY, velX; // variáveis de posição e velocidade
	public double colX, colY, colLargura, colAltura;
	Color colColor;

	long tempoDecorrido;
	
	public Dog() {
		correndoSprite = Game.recursos.spriteDog;
		correndoQtdQuadros = 12;
		correndoQuadros = new BufferedImage[correndoQtdQuadros];
		correndoLargura = 175;
		correndoAltura = 124;
		correndoIndexAtual = 0;
		correndoTempoQuadro = 30;
		
		
		

		velX=-7;
		tempoDecorrido = 0;
		
		// recorte dos sprites ..........
		for(int i=0; i<correndoQtdQuadros; i++) {
			int x1 = correndoLargura*i;
			int y1 = 0;
			correndoQuadros[i] = correndoSprite.getSubimage(x1,y1,correndoLargura, correndoAltura);
		}
	}
	

	public void update(){
		posX = 750;
        posY=325;
	}

	public void render(Graphics g){
        g.drawImage(obterQuadro(), (int)posX, (int)posY, null);
		
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
	public BufferedImage obterQuadro(){
		return correndoQuadros[correndoIndexAtual];
	}
	public void reiniciaJogo(){
		correndoIndexAtual = 0;
		update();
	}
	public enum Estado{
		CORRENDO, PULANDO;
	}
}
