package hepker.engine;

public final class SimulationLoop implements Runnable {
    private Engine engine;
    private Thread thread;

    public SimulationLoop() {
    }

    @Override
    public void run() {
        this.engine = new Engine();
        try {
            while (engine.isRunning()) {
                engine.update();
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void awaitCompletion() {
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
