import java.util.concurrent.ExecutorService;

final class MultithreadedEventLoop implements EventLoop {
  private final EventLoop origin;
  private final Integer nThreads;
  private final ExecutorService executorService;

  MultithreadedEventLoop(final EventLoop origin, final Integer nThreads, final ExecutorService executorService) {
    this.origin = origin;
    this.nThreads = nThreads;
    this.executorService = executorService;
  }

  @Override
  public void start() {
    for (var i = 0; i < nThreads; i++) {
      executorService.execute(origin::start);
    }
  }

  @Override
  public void stop() {
    origin.stop();
    shutdownExecutorService();
  }

  private void shutdownExecutorService() {
    // Java specific code
  }
}