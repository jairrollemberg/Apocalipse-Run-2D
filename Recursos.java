import java.awt.image.BufferedImage;
import java.awt.Font;
import javax.imageio.ImageIO;

public class Recursos {
	// atributos
	BufferedImage imgJhonRun, imgJhonPulo;
	BufferedImage spriteMorcego, spriteZumbi,spriteDog;
	BufferedImage imgPiso, imgCenario, telaGameOver, telaLoading;
	public Font fontTexto;
	public double velocidadeJogo, fatorAceleracao;
	long recorde;

	// construtor
	public Recursos() {
		velocidadeJogo = 1.0;
		fatorAceleracao = 0.08;
		fontTexto = new Font(" comic sans", Font.CENTER_BASELINE, 20);
		try {
			// carrega os sprites do jogo, personagem, cenario, inimigos etc
			imgJhonPulo = ImageIO.read(getClass().getResource("/imgs/jhonWickPulo.png"));
			imgJhonRun = ImageIO.read(getClass().getResource("/imgs/jhonWick3.png"));
			spriteDog = ImageIO.read(getClass().getResource("/imgs/blackDog.png"));
			spriteMorcego = ImageIO.read(getClass().getResource("/imgs/morcego.png"));
			spriteZumbi = ImageIO.read(getClass().getResource("/imgs/Zumbi2.png"));
			imgPiso = ImageIO.read(getClass().getResource("/imgs/piso2.png"));
			imgCenario = ImageIO.read(getClass().getResource("/imgs/fundo.png"));
			telaGameOver = ImageIO.read(getClass().getResource("/imgs/game-over.png"));
			telaLoading = ImageIO.read(getClass().getResource("/imgs/load-screen.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
