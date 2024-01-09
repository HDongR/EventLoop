import java.util.concurrent.atomic.AtomicBoolean;

final class BusyWaitingEventLoop implements EventLoop {
  private final Events events;
  private final Script script;
  private final AtomicBoolean alive;

  BusyWaitingEventLoop(final Events events, final Script script) {
    this(events, script, new AtomicBoolean(true));
  }
  
  BusyWaitingEventLoop(final Events events, final Script script, final AtomicBoolean alive) {
    this.events = events;
    this.script = script;
    this.alive = alive;
  }

  @Override
  public void start() {
    while (alive.get()) {
      events.next().trigger(script);
    }
  }

  @Override
  public void stop() {
    alive.set(false);
  }
}