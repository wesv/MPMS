public class Main {
    public static void main(String[] args) {
        try {
            new Mine(Location.create()).flip();
        } catch(GameEndException e) {
            System.out.println(e.getMessage());
        }
    }
}
