import ru.javawebinar.util.LazySingleton;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10_000;
    private static volatile int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread(){
           @Override
           public void run() {
               System.out.println(getName() + ", " + getState());
               throw new IllegalStateException();
           }
        };
        thread0.start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        // объект mainConcurrency в Heap. К нему есть очередь
                        // Механизм монитор обеспечивает синхронизацию
                        // Все потоки встанут в очередь к объекту
                        mainConcurrency.inc();
                    }
                }
            });
            thread.start();
            // Ждем пока 10000 потоков не закончат работать
            // Текущий поток (main) ждет пока не закончится данный поток - thread
            // Поток main останавливается и ждет пока thread не закончится
            //thread.join();
            threads.add(thread);
        }
        // Для каждого потока листа подконекчиваемся
        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //Thread.sleep(500);

        System.out.println(mainConcurrency.counter);
        LazySingleton.getInstance();
    }

    // Делаем так что в этот метод заходит только один единственный поток
    private void inc() {
        //double a = Math.sin(13.0);
        // Синхронизация требуется только для увеличения counter
        // Поэтому synchronized переносим. В synchronized передаем объект
        // для синхронизации
        // Все потоки встают в очередь к объекту lock
        // Монитор - механизм синхронизации
        //synchronized (this) { // this - это
            counter++;
            //wait(); // передаем управление другим потокам и выходим из этого блок и снимаем блокировку
            // ДРУГОЙ БЛОК скажет notify() - уведомляет что выйти из wait(). Т.е. после notify
            // выполняетмя следующие функции и только потом наша функция выходит из wait()
            // какой-то readFile
       // }
    }
}
