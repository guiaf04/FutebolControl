package model;

public class Partida {
        public int id;
        public Clube mandante;
        public Clube visitante;
        public String data;
        public String resultado;
        public int golsMandante;
        public int golsVisitante;

  public Partida(int id, Clube mandante, Clube visitante, String data, String resultado, int golsMandante, int golsVisitante) {
    this.id = id;
    this.mandante = mandante;
    this.visitante = visitante;
    this.data = data;
    this.resultado = resultado;
    this.golsMandante = golsMandante;
    this.golsVisitante = golsVisitante;
  }
}
