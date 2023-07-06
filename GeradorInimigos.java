import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.awt.Graphics;


/* Classe responsável por gerar monstros de forma aleatória */
public class GeradorInimigos {
    
    // Atributos ............
    public Stack<Zumbi> pilhaZumbi;
    public Stack<Morcego> pilhaMorcego;
    public ArrayList<Zumbi> listaZumbi;
    public ArrayList<Morcego> listaMorcego;
    public int qtdMonstros = 2; // para cada tipo

    long tempoDecorrido;
    Random random;
    int tempoMin, tempoMax, tempoGeracao; 

    // Construtor .............
    public GeradorInimigos(){
        pilhaZumbi = new Stack<Zumbi>();
        pilhaMorcego = new Stack<Morcego>();
        listaZumbi = new ArrayList<Zumbi>();
        listaMorcego = new ArrayList<Morcego>();
        for(int i=0;i<qtdMonstros;i++){
            Zumbi zumbi = new Zumbi();
            pilhaZumbi.push(zumbi);
            listaZumbi.add(zumbi);
            Morcego morcego = new Morcego();
            pilhaMorcego.push(morcego);
            listaMorcego.add(morcego);
        }
        listaZumbi.clear();
        listaMorcego.clear();
        tempoDecorrido = 0;
        random = new Random();
        tempoGeracao = 2000;
        tempoMin=1500;
        tempoMax=3500;
    }

    public void update(long tempoDelta){
        tempoMin=1500-(int)(Game.recursos.velocidadeJogo*100);
        tempoMax=3500-(int)(Game.recursos.velocidadeJogo*100);
        // geração de inimigos.............
        tempoDecorrido+=tempoDelta;
        if(tempoDecorrido >= tempoGeracao) { // a cada 1 segundo
            tempoGeracao = random.nextInt(tempoMax-tempoMin)+tempoMin;
            // tenta gerar um novo inimigo na tela
            gerarNovoInimigo();
            tempoDecorrido = 0;
        }

        checarSaidaInimigos(); // verifica sa saida dos monstros na tela

        // Atualização dos inimigos 
        for(Zumbi m : listaZumbi){
            m.update();
            m.mudarQuadro(tempoDelta);
        }
        for(Morcego m : listaMorcego){
            m.update();
            m.mudarQuadro(tempoDelta);
        }
    }

    public void render(Graphics g){
        for(Zumbi m : listaZumbi){
            m.render(g);
        }
        for(Morcego m : listaMorcego){
            m.render(g);
        }
	}

    // Metodos .............
    public void gerarNovoInimigo(){
        // gera um numero aleatório para saber se vai ser zumbi ou morcego
        if(random.nextInt()%2==0){ // se for par, vai ser zumbi
            if(pilhaZumbi.empty()) return; 
            Zumbi zumbi = pilhaZumbi.pop();
            zumbi.reposicionar();
            listaZumbi.add(zumbi);
        }else{// se for ímpar, vai ser morcego
            if(pilhaMorcego.empty()) return; 
            Morcego morcego = pilhaMorcego.pop();
            morcego.reposicionar();
            listaMorcego.add(morcego);
        }

        
    }
    public void checarSaidaInimigos(){
        for(int i=0;i<listaZumbi.size();i++){
            Zumbi m = listaZumbi.get(i);
            // verifica se o zumbi saiu (pela esquerda)
            if(m.posX+m.correndoLargura<=0){
                
                pilhaZumbi.push(m);
                listaZumbi.remove(m);
                Game.recursos.velocidadeJogo+=Game.recursos.fatorAceleracao;
            }
        }
        for(int i=0;i<listaMorcego.size();i++){
            Morcego m = listaMorcego.get(i);
            // verifica se o morcego saiu (pela esquerda)
            if(m.posX+m.correndoLargura<=0){
                
                pilhaMorcego.push(m);
                listaMorcego.remove(m);
                Game.recursos.velocidadeJogo+=Game.recursos.fatorAceleracao;
            }
        }
    }

    public void reiniciaJogo(){
        for(int i=0;i<listaZumbi.size();i++){
            Zumbi m = listaZumbi.get(i);
            m.reiniciaJogo();
            pilhaZumbi.push(m);
            listaZumbi.remove(m);
        }
		for(int i=0;i<listaMorcego.size();i++){
            Morcego m = listaMorcego.get(i);
            m.reiniciaJogo();
            pilhaMorcego.push(m);
            listaMorcego.remove(m);
        }
        tempoGeracao = 2000;
        tempoMin=1500;
        tempoMax=3500;
        tempoDecorrido=0;
	}
}
