import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

interface EventLoop {
  void start();

  void stop();
}

interface Events {
  Event next();
}

interface Event {
  void trigger(Script script);
}

interface Script {
  void run(Map<String,Object> properties, Consumer<String> onSuccess, Consumer<Throwable> onFailure);
}