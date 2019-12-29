package Engine;

public class GameLauncher {
    public static void main(String[] args){
        Engine game = new Engine("3D Matrix Test FPS: ", 800, 600);
        game.start();
    }
}
