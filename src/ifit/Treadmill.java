package ifit;

public class Treadmill {
    private float speed;
    private float incline;
    private Player player;

    public Treadmill() {
        player = new Player();
    }

    public void set(float incline, float speed) {
        this.speed = speed;
        this.incline = incline;
        sendMessage();
    }

    private void sendMessage() {
        Generator generator = new Generator(this.incline, this.speed);
        generator.generateSignal();
        player.play(generator);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        sendMessage();
    }

    public void setIncline(float incline) {
        this.incline = incline;
        sendMessage();
    }

    public float incline() {
        return this.incline;
    }

    public float speed() {
        return this.speed;
    }

    public void stop() {
        player.stop();
    }
}
