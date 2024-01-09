import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public final class Main {
  public static void main(String[] args) {
    var eventLoopThreads = 2;
    var scriptThreads = 14;
    var executorService = Executors.newFixedThreadPool(eventLoopThreads + scriptThreads);
    Map<String,Object> anEventsObject = new HashMap<>();
    anEventsObject.put("a", 1);
    anEventsObject.put("b", 2);

    new MultithreadedEventLoop(
      new BusyWaitingEventLoop(new Events() {

        @Override
        public Event next() {
            // TODO Auto-generated method stub
            return new Event() {
                @Override
                public void trigger(Script script) {
                    // TODO Auto-generated method stub
                    script.run(anEventsObject, new Consumer<String>() {
                        @Override
                        public void accept(String t) {
                            // TODO Auto-generated method stub
                           System.out.println(t);
                        }
                        
                    }, new Consumer<Throwable>() {

                        @Override
                        public void accept(Throwable t) {
                            // TODO Auto-generated method stub
                             System.out.println(t);
                        }
                        
                    });
                }
                
            };
        }
        
      }
        ,
        new AsyncScript(
          new Script() {

            @Override
            public void run(Map<String, Object> properties, Consumer<String> onSuccess,
                    Consumer<Throwable> onFailure) {
                // TODO Auto-generated method stub
                System.out.println(properties.toString());

                onSuccess.accept("check");
                //onFailure.accept(new Throwable("out"));
            }
            
          },
          executorService
        )
      ),
      eventLoopThreads,
      executorService
    ).start();
  }
}