package hepker.engine;

public final class SimulationLoop implements Runnable {
    private final int gameSpeed;

    private Engine engine;
    private Thread thread;

    private double successCount;
    private double epochsPerformed;

    public SimulationLoop(final int inputGameSpeed) {
        this.gameSpeed = inputGameSpeed;
        this.successCount = 0.0;
        this.epochsPerformed = 0.0;
    }

    @Override
    public void run() {
        this.engine = new Engine();
        try {
            while (engine.isRunning()) {
                engine.update();
                Thread.sleep(gameSpeed);
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
            ++epochsPerformed;
            if (engine.travellerArrived()) {
                System.out.printf("Success. Successrate: %.2f\n", ++successCount / epochsPerformed);
            } else {
                System.out.printf("Failure. SuccessRate: %.2f\n", successCount / epochsPerformed);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
