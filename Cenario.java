import java.awt.image.BufferedImage;

/* Representa o cenário do jogo */
public class Cenario {
    public BufferedImage cenario1, cenario2;
    int cenarioLargura, cenarioAltura;
    double cenarioVelX;
    double cenario1PosX, cenario2PosX, cenario1PosY;

    public Cenario() {
        cenario1 = Game.recursos.imgCenario;
        cenario2 = Game.recursos.imgCenario;
        cenarioLargura = 920;
        cenarioAltura = 480;
        cenarioVelX = -2 * Game.recursos.velocidadeJogo;

        cenario1PosX = 0;
        cenario1PosY = 0;
        cenario2PosX = cenario1PosX + cenarioLargura;
    }

    public void update() {
        // atualiza o cenario
        cenarioVelX = -1 * Game.recursos.velocidadeJogo;
        cenario1PosX = cenario1PosX + cenarioVelX;
        cenario2PosX = cenario2PosX + cenarioVelX;
        remontarCenario();
    }

    public void remontarCenario() {
        if (cenario1PosX + cenarioLargura <= 0) {
            cenario1PosX = cenario2PosX + cenarioLargura;
        } else if (cenario2PosX + cenarioLargura <= 0) {
            cenario2PosX = cenario1PosX + cenarioLargura;
        }
    }

    public void reiniciaJogo() {
        cenario1PosX = 0;
        cenario2PosX = cenario1PosX + cenarioLargura;
    }
}
