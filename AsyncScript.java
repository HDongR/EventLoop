import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

final class AsyncScript implements Script {
  private final Script origin;
  private final ExecutorService executorService;

  AsyncScript(final Script origin, final ExecutorService executorService) {
    this.origin = origin;
    this.executorService = executorService;
  }

  @Override
  public void run(final Map<String,Object> properties, final Consumer<String> onSuccess, final Consumer<Throwable> onFailure) {
    if (!executorService.isShutdown()) {
      executorService.execute(() -> origin.run(properties, onSuccess, onFailure));
    }
  }
}