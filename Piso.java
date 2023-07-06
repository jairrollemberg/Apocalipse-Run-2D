import java.awt.image.BufferedImage;
import java.awt.Graphics;

/* Representa o piso inicial do jogo */
public class Piso {
    public BufferedImage piso1, piso2, piso3;
    int pisoLargura, pisoAltura;
    double pisoVelX, pisoPosY;
    double piso1PosX, piso2PosX, piso3PosX;

    public Piso() {
        piso1 = Game.recursos.imgPiso;
        piso2 = Game.recursos.imgPiso;
        piso3 = Game.recursos.imgPiso;
        pisoAltura = 10;
        pisoVelX = -9;
        pisoPosY = Principal.ALTURA_TELA - 110;

        piso1PosX = 0;
        piso2PosX = piso1PosX + pisoLargura;
        piso3PosX = piso2PosX + pisoLargura;
    }

    public void update() {
        // atualiza o piso
        pisoVelX = -6 * Game.recursos.velocidadeJogo;
        piso1PosX = piso1PosX + pisoVelX;
        piso2PosX = piso2PosX + pisoVelX;
        piso3PosX = piso3PosX + pisoVelX;
        remontarPiso();
    }

    public void render(Graphics g) {
        g.drawImage(piso1, (int) piso1PosX, (int) pisoPosY, null);
        g.drawImage(piso2, (int) piso2PosX, (int) pisoPosY, null);
        g.drawImage(piso3, (int) piso3PosX, (int) pisoPosY, null);
    }

    public void remontarPiso() {
        if (piso1PosX + pisoLargura <= 0) {
            piso1PosX = piso3PosX + pisoLargura;
        } else if (piso2PosX + pisoLargura <= 0) {
            piso2PosX = piso1PosX + pisoLargura;
        } else if (piso3PosX + pisoLargura <= 0) {
            piso3PosX = piso2PosX + pisoLargura;
        }
    }

    public void reiniciaJogo() {
        piso1PosX = 0;
        piso2PosX = piso1PosX + pisoLargura;
        piso3PosX = piso2PosX + pisoLargura;
    }
}
