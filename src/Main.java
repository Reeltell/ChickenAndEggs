import java.util.Random;

class Main {
    public static void main(String[] args) throws InterruptedException {
        EntityRace chicken = new EntityRace("Курица");
        EntityRace egg = new EntityRace("Яйцо");

        // Запускаем оба потока
        chicken.start();
        egg.start();

        // Определяем, какой поток завершится первым
        String winner = getFirstToFinish(chicken, egg);
        System.out.println("Первым на свет появилось: " + winner);
    }

    // Метод для определения, какой поток завершится первым
    public static String getFirstToFinish(Thread entity1, Thread entity2) throws InterruptedException {
        while (true) {
            if (!entity1.isAlive()) {
                entity2.join();  // Ждем завершения второго потока
                return entity1.getName();  // Возвращаем имя завершившегося потока
            }
            if (!entity2.isAlive()) {
                entity1.join();  // Ждем завершения первого потока
                return entity2.getName();  // Возвращаем имя завершившегося потока
            }
            Thread.sleep(50);  // Небольшая пауза для предотвращения излишней загрузки процессора
        }
    }
}

// Класс для представления участников гонки: курицы и яйца
class EntityRace extends Thread {
    private static final Random RANDOM = new Random();

    public EntityRace(String entityName) {
        super(entityName);  // Передаем имя в конструктор родительского класса Thread
    }

    @Override
    public void run() {
        try {
            // Генерируем случайное время ожидания от 1 до 5 секунд
            int sleepTime = RANDOM.nextInt(5000) + 1000;
            Thread.sleep(sleepTime);
            System.out.println(getName() + " финишировала!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
            System.out.println(getName() + " была прервана.");
        }
    }
}