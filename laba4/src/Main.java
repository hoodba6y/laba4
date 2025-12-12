import java.util.*;

//Задание 1. Обобщенные тип

// Класс коробки для хранения одного объекта любого типа
class Box<T> {
    private T item;

    // Конструктор создает пустую коробку
    public Box() {
        this.item = null;
    }

    // Метод для помещения объекта в коробку
    public void put(T item) throws IllegalStateException {
        if (isFull()) {
            throw new IllegalStateException("Коробка уже занята!");
        }
        this.item = item;
    }

    // Метод для извлечения объекта из коробки
    public T take() {
        T takenItem = item;
        item = null;
        return takenItem;
    }

    // Проверка, заполнена ли коробка
    public boolean isFull() {
        return item != null;
    }

    @Override
    public String toString() {
        return "Box{" + (isFull() ? item : "пусто") + '}';
    }
}

// Класс хранилища с альтернативным значением
class Storage<T> {
    private final T item;
    private final T alternative;

    // Конструктор принимает хранимое значение и альтернативу
    public Storage(T item, T alternative) {
        this.item = item;
        this.alternative = alternative;
    }

    // Возвращает хранимое значение или альтернативу, если значение null
    public T get() {
        return item != null ? item : alternative;
    }

    @Override
    public String toString() {
        return "Storage{хранится=" + item + ", альтернатива=" + alternative + '}';
    }
}

//Задание 3. Обобщенные методы

// Интерфейсы

// Интерфейс для преобразования элементов
interface ApplyFunction<T, R> {
    R apply(T value);
}

// Интерфейс для фильтрации элементов
interface TestFunction<T> {
    boolean test(T value);
}

// Интерфейс для свертки элементов
interface ReduceFunction<T> {
    T reduce(T accum, T value);
}

// Интерфейс для сбора элементов
interface CollectorFunction<T, P> {
    P createCollection(); // Создает новую коллекцию
    void addToCollection(P collection, T value); // Добавляет элемент в коллекцию
}

// Класс с обобщенными методами
class GenericMethods {

    // Метод map преобразует каждый элемент списка
    public static <T, R> List<R> map(List<T> list, ApplyFunction<T, R> func) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(func.apply(item));
        }
        return result;
    }

    // Метод filter фильтрует элементы по условию
    public static <T> List<T> filter(List<T> list, TestFunction<T> func) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (func.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // Метод reduce сводит список к одному значению
    public static <T> T reduce(List<T> list, ReduceFunction<T> func, T defaultValue) {
        if (list.isEmpty()) {
            return defaultValue;
        }
        T accum = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            accum = func.reduce(accum, list.get(i));
        }
        return accum;
    }

    // Метод collect собирает элементы в произвольную коллекцию
    public static <T, P> P collect(List<T> list, CollectorFunction<T, P> collector) {
        P result = collector.createCollection();
        for (T item : list) {
            collector.addToCollection(result, item);
        }
        return result;
    }
}

// Главный класс
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        task1(); // Демонстрация коробки
        task2(); // Демонстрация хранилища
        task3_1(); // map операции
        task3_2(); // filter операции
        task3_3(); // reduce операции
        task3_4(); // collect операции
    }

    // Демонстрация заданий

    // Демонстрация класса Box
    private static void task1() {
        System.out.println("Задание 1.1: Обобщенная коробка");
        Box<Integer> intBox = new Box<>(); // Создаем коробку для целых чисел
        System.out.println("Создана коробка: " + intBox);

        try {
            System.out.print("Введите целое число для помещения в коробку: ");
            int value = readInt(); // Чтение числа с клавиатуры
            intBox.put(value); // Помещение числа в коробку
            System.out.println("Поместили число " + value + " в коробку: " + intBox);
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("Коробка пуста? " + !intBox.isFull());

        // Передаем коробку в метод для демонстрации
        processBox(intBox);
        System.out.println("После извлечения: " + intBox + "\n");
    }

    // Вспомогательный метод для обработки коробки
    private static void processBox(Box<Integer> box) {
        if (box.isFull()) {
            Integer value = box.take(); // Извлекаем значение
            System.out.println("В методе processBox извлекли значение: " + value);
        }
    }

    // Демонстрация класса Storage
    private static void task2() {
        System.out.println("Задание 1.2: Хранилище без null");

        // Создаем различные хранилища
        Storage<Integer> numStorage1 = new Storage<>(null, 0);
        Storage<Integer> numStorage2 = new Storage<>(99, -1);
        Storage<String> strStorage1 = new Storage<>(null, "default");
        Storage<String> strStorage2 = new Storage<>("hello", "hello world");

        // Обрабатываем каждое хранилище
        processStorage(numStorage1, "Число (null → 0)");
        processStorage(numStorage2, "Число (99 → 99)");
        processStorage(strStorage1, "Строка (null → default)");
        processStorage(strStorage2, "Строка (hello → hello)");
    }

    // Обобщенный метод для обработки хранилища
    private static <T> void processStorage(Storage<T> storage, String description) {
        System.out.println(description + ": " + storage);
        System.out.println("Извлеченное значение: " + storage.get() + "\n");
    }

    // Демонстрация операции map
    private static void task3_1() {
        System.out.println("Задание 3.1: Функция (map)");

        // 1. Преобразование строк в их длины
        List<String> strings1 = Arrays.asList("qwerty", "asdfg", "zx");
        List<Integer> lengths = GenericMethods.map(strings1, new ApplyFunction<String, Integer>() {
            @Override
            public Integer apply(String value) {
                return value.length();  // Возвращаем длину строки
            }
        });
        System.out.println("Строки: " + strings1);
        System.out.println("Длины: " + lengths);

        // 2. Преобразование чисел в их абсолютные значения
        List<Integer> numbers = Arrays.asList(1, -3, 7);
        List<Integer> absolutes = GenericMethods.map(numbers, new ApplyFunction<Integer, Integer>() {
            @Override
            public Integer apply(Integer value) {
                return Math.abs(value); // Возвращаем модуль числа
            }
        });
        System.out.println("\nЧисла: " + numbers);
        System.out.println("Абсолютные значения: " + absolutes);

        // 3. Преобразование массивов в их максимальные значения
        List<int[]> arrays = Arrays.asList(
                new int[]{1, 2, 3},
                new int[]{-5, 0, 5},
                new int[]{10, 20}
        );
        List<Integer> maxValues = GenericMethods.map(arrays, new ApplyFunction<int[], Integer>() {
            @Override
            public Integer apply(int[] value) {
                return Arrays.stream(value).max().orElse(0); // Находим максимум в массиве
            }
        });
        System.out.println("\nМассивы: " + arraysToString(arrays));
        System.out.println("Максимумы: " + maxValues + "\n");
    }

    // Демонстрация операции filter
    private static void task3_2() {
        System.out.println("Задание 3.2: Фильтр");

        // 1. Фильтрация строк длиной >= 3
        List<String> strings1 = Arrays.asList("qwerty", "asdfg", "zx");
        List<String> filteredStrings = GenericMethods.filter(strings1, new TestFunction<String>() {
            @Override
            public boolean test(String value) {
                return value.length() >= 3; // Проверяем длину строки
            }
        });
        System.out.println("Строки: " + strings1);
        System.out.println("Длина >= 3: " + filteredStrings);

        // 2. Фильтрация положительных чисел
        List<Integer> numbers = Arrays.asList(1, -3, 7);
        List<Integer> positiveNumbers = GenericMethods.filter(numbers, new TestFunction<Integer>() {
            @Override
            public boolean test(Integer value) {
                return value > 0; // Проверяем, положительное ли число
            }
        });
        System.out.println("\nЧисла: " + numbers);
        System.out.println("Положительные: " + positiveNumbers);

        // 3. Фильтрация массивов без положительных элементов
        List<int[]> arrays = Arrays.asList(
                new int[]{-1, -2, -3},
                new int[]{-5, 0, 5},
                new int[]{-10, -20}
        );
        List<int[]> arraysNoPositive = GenericMethods.filter(arrays, new TestFunction<int[]>() {
            @Override
            public boolean test(int[] value) {
                return Arrays.stream(value).allMatch(v -> v <= 0); // Проверяем все элементы
            }
        });
        System.out.println("\nМассивы: " + arraysToString(arrays));
        System.out.println("Без положительных: " + arraysToString(arraysNoPositive) + "\n");
    }

    // Демонстрация операции reduce
    private static void task3_3() {
        System.out.println("Задание 3.3: Сокращение (reduce)");

        // 1. Конкатенация строк
        List<String> strings1 = Arrays.asList("qwerty", "asdfg", "zx");
        String concatenated = GenericMethods.reduce(strings1, new ReduceFunction<String>() {
            @Override
            public String reduce(String accum, String value) {
                return accum + value;  // Объединяем строки
            }
        }, "");  // Начальное значение - пустая строка
        System.out.println("Строки: " + strings1);
        System.out.println("Конкатенация: " + concatenated);

        // 2. Сумма чисел
        List<Integer> numbers = Arrays.asList(1, -3, 7);
        Integer sum = GenericMethods.reduce(numbers, new ReduceFunction<Integer>() {
            @Override
            public Integer reduce(Integer accum, Integer value) {
                return accum + value;  // Складываем числа
            }
        }, 0);  // Начальное значение - 0
        System.out.println("\nЧисла: " + numbers);
        System.out.println("Сумма: " + sum);

        // 3. Подсчет общего количества элементов в списках списков
        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8, 9)
        );
        Integer totalElements = GenericMethods.reduce(listOfLists, new ReduceFunction<List<Integer>>() {
            @Override
            public List<Integer> reduce(List<Integer> accum, List<Integer> value) {
                List<Integer> newList = new ArrayList<>(accum);
                newList.addAll(value);  // Объединяем списки
                return newList;
            }
        }, new ArrayList<Integer>()).size();  // Берем размер итогового списка
        System.out.println("\nСписок списков: " + listOfLists);
        System.out.println("Общее количество элементов: " + totalElements);

        // 4. Проверка работы с пустым списком
        List<Integer> emptyList = new ArrayList<>();
        Integer emptyResult = GenericMethods.reduce(emptyList, new ReduceFunction<Integer>() {
            @Override
            public Integer reduce(Integer accum, Integer value) {
                return accum + value;
            }
        }, 0);
        System.out.println("\nПустой список, результат с default: " + emptyResult + "\n");
    }

    // Демонстрация операции collect
    private static void task3_4() {
        System.out.println("Задание 3.4: Коллекционирование");

        // 1. Разделение чисел на положительные и отрицательные
        List<Integer> numbers = Arrays.asList(1, -3, 7, -2, 0, 4);
        Map<String, List<Integer>> numberGroups = GenericMethods.collect(numbers,
                new CollectorFunction<Integer, Map<String, List<Integer>>>() {
                    @Override
                    public Map<String, List<Integer>> createCollection() {
                        // Создаем Map с двумя списками
                        Map<String, List<Integer>> map = new HashMap<>();
                        map.put("positive", new ArrayList<>());
                        map.put("negative", new ArrayList<>());
                        return map;
                    }

                    @Override
                    public void addToCollection(Map<String, List<Integer>> collection, Integer value) {
                        // Распределяем числа по спискам
                        if (value > 0) {
                            collection.get("positive").add(value);
                        } else if (value < 0) {
                            collection.get("negative").add(value);
                        }
                        // Ноль игнорируем
                    }
                });
        System.out.println("Числа: " + numbers);
        System.out.println("Разделение: " + numberGroups);

        // 2. Группировка строк по длине
        List<String> strings = Arrays.asList("qwerty", "asdfg", "zx", "qw");
        Map<Integer, List<String>> lengthGroups = GenericMethods.collect(strings,
                new CollectorFunction<String, Map<Integer, List<String>>>() {
                    @Override
                    public Map<Integer, List<String>> createCollection() {
                        return new HashMap<>();  // Создаем пустой Map
                    }

                    @Override
                    public void addToCollection(Map<Integer, List<String>> collection, String value) {
                        int length = value.length();
                        // Если длины еще нет в Map, создаем новый список
                        if (!collection.containsKey(length)) {
                            collection.put(length, new ArrayList<>());
                        }
                        collection.get(length).add(value);
                    }
                });
        System.out.println("\nСтроки: " + strings);
        System.out.println("Группировка по длине: " + lengthGroups);

        // 3. Создание множества уникальных строк
        List<String> duplicates = Arrays.asList("qwerty", "asdfg", "qwerty", "qw");
        Set<String> uniqueStrings = GenericMethods.collect(duplicates,
                new CollectorFunction<String, Set<String>>() {
                    @Override
                    public Set<String> createCollection() {
                        return new HashSet<>(); // HashSet автоматически удаляет дубликаты
                    }

                    @Override
                    public void addToCollection(Set<String> collection, String value) {
                        collection.add(value); // Добавляем строку в множество
                    }
                });
        System.out.println("\nСтроки с дубликатами: " + duplicates);
        System.out.println("Уникальные: " + uniqueStrings + "\n");
    }

    // Вспомогательные методы

    // Метод для чтения целого числа с проверкой корректности ввода
    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ошибка! Введите целое число: ");
            }
        }
    }

    // Метод для преобразования списка массивов в строку
    private static String arraysToString(List<int[]> arrays) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arrays.size(); i++) {
            sb.append(Arrays.toString(arrays.get(i)));
            if (i < arrays.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}