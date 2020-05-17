public class GameDriver {
    public static void main(String[] args) {
//        Game.Play();
        Item player=new Player();
        System.out.print(player.getClass().toString().equals(Player.class.toString()));
        System.exit(0);
    }
}
