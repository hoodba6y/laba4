import java.util.*;
import java.util.function.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Задание 1, задача 1: Обобщенная коробка
        System.out.println("Задание 1, задача 1: Обобщенная коробка\n");
        Box<Integer> intBox = new Box<>();
        System.out.println("Создана пустая коробка для целых чисел.");
        System.out.println("Коробка пуста? " + intBox.isEmpty());

        try {
            System.out.print("Введите целое число для помещения в коробку: ");
            int number = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            intBox.put(number);
            System.out.println("Число " + number + " помещено в коробку.");
            System.out.println("Коробка пуста? " + intBox.isEmpty());

            // Извлечение через метод
            extractAndPrintValue(intBox);

            System.out.println("Коробка пуста после извлечения? " + intBox.isEmpty());

            // Попытка положить второе значение
            System.out.print("\nПробуем положить второе число в коробку: ");
            int secondNumber = scanner.nextInt();
            scanner.nextLine();
            intBox.put(secondNumber);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Задание 1, задача 2: Без null
        System.out.println("\nЗадание 1, задача 2: Хранилище");

        // Хранилище чисел с null (альтернатива 0)
        System.out.println("\n1. Хранилище чисел с null:");
        Storage<Integer> numStorageNull = new Storage<>(null, 0);
        extractAndPrintFromStorage(numStorageNull);

        // Хранилище чисел с 99 (альтернатива -1)
        System.out.println("\n2. Хранилище чисел с 99:");
        Storage<Integer> numStorage99 = new Storage<>(99, -1);
        extractAndPrintFromStorage(numStorage99);

        // Хранилище строк с null (альтернатива default)
        System.out.println("\n3. Хранилище строк с null:");
        Storage<String> strStorageNull = new Storage<>(null, "default");
        extractAndPrintFromStorage(strStorageNull);

        // Хранилище строк с hello (альтернатива hello world)
        System.out.println("\n4. Хранилище строк с hello:");
        Storage<String> strStorageHello = new Storage<>("hello", "hello world");
        extractAndPrintFromStorage(strStorageHello);

        // Задание 3, задача 1: Функция
        System.out.println("\nЗадание 3, задача 1: Функция");

        // 1. Длина строк
        System.out.println("\n1. Преобразование списка строк в список их длин:");
        List<String> stringList = Arrays.asList("qwerty", "asdfg", "zx");
        System.out.println("Исходный список: " + stringList);

        List<Integer> lengthList = GenericMethods.map(stringList, String::length);
        System.out.println("Список длин: " + lengthList);

        // 2. Положительные значения
        System.out.println("\n2. Положительные значения чисел:");
        List<Integer> numbers = Arrays.asList(1, -3, 7);
        System.out.println("Исходный список: " + numbers);

        List<Integer> absoluteNumbers = GenericMethods.map(numbers, Math::abs);
        System.out.println("Положительные значения: " + absoluteNumbers);

        // 3. Максимальные значения массивов
        System.out.println("\n3. Максимальные значения из массивов:");
        List<int[]> arraysList = Arrays.asList(
                new int[]{1, 5, 3},
                new int[]{-2, 0, 8},
                new int[]{4, 2, 6}
        );

        System.out.print("Исходные массивы: ");
        for (int[] arr : arraysList) {
            System.out.print(Arrays.toString(arr) + " ");
        }
        System.out.println();

        List<Integer> maxValues = GenericMethods.map(arraysList, arr -> {
            int max = Integer.MIN_VALUE;
            for (int num : arr) {
                if (num > max) max = num;
            }
            return max;
        });

        System.out.println("Максимальные значения: " + maxValues);

        // Задание 3, задача 2: Фильтр
        System.out.println("\nЗадание 3, задача 2: Фильтр");

        // 1. Фильтр строк: удалить строки длиной < 3
        System.out.println("\n1. Фильтрация строк: удалить строки длиной менее 3 символов:");
        List<String> stringsToFilter = Arrays.asList("qwerty", "asdfg", "zx");
        System.out.println("Исходный список: " + stringsToFilter);

        // Оставляем строки длиной >= 3
        List<String> filteredStrings = GenericMethods.filter(stringsToFilter, s -> s.length() >= 3);
        System.out.println("Отфильтрованный список: " + filteredStrings);

        // 2. Фильтр чисел: удалить положительные
        System.out.println("\n2. Фильтрация чисел: удалить положительные элементы:");
        List<Integer> numbersToFilter = Arrays.asList(1, -3, 7);
        System.out.println("Исходный список: " + numbersToFilter);

        // Оставляем неположительные числа
        List<Integer> filteredNumbers = GenericMethods.filter(numbersToFilter, n -> n <= 0);
        System.out.println("Неположительные числа: " + filteredNumbers);

        // 3. Фильтр массивов без положительных элементов
        System.out.println("\n3. Фильтрация массивов без положительных элементов:");
        List<int[]> arraysToFilter = Arrays.asList(
                new int[]{-1, -5, -3},
                new int[]{-2, 0, 8},
                new int[]{-4, -2}
        );

        System.out.print("Исходные массивы: ");
        for (int[] arr : arraysToFilter) {
            System.out.print(Arrays.toString(arr) + " ");
        }
        System.out.println();

        List<int[]> filteredArrays = GenericMethods.filter(arraysToFilter, arr -> {
            for (int num : arr) {
                if (num > 0) return false;
            }
            return true;
        });

        System.out.print("Массивы без положительных элементов: ");
        for (int[] arr : filteredArrays) {
            System.out.print(Arrays.toString(arr) + " ");
        }
        System.out.println();

        // Задание 3, задача 3: Сокращение
        System.out.println("\nЗадание 3, задача 3: Сокращение");

        // 1. Конкатенация строк
        System.out.println("\n1. Конкатенация строк:");
        List<String> stringsToReduce = Arrays.asList("qwerty", "asdfg", "zx");
        System.out.println("Исходный список: " + stringsToReduce);

        String concatenated = GenericMethods.reduce(stringsToReduce, "", (acc, s) -> acc + s);
        System.out.println("Результат конкатенации: " + concatenated);

        // 2. Сумма чисел
        System.out.println("\n2. Сумма чисел:");
        List<Integer> numbersToSum = Arrays.asList(1, -3, 7);
        System.out.println("Исходный список: " + numbersToSum);

        Integer sum = GenericMethods.reduce(numbersToSum, 0, Integer::sum);
        System.out.println("Сумма: " + sum);

        // 3. Общее количество элементов во вложенных списках
        System.out.println("\n3. Общее количество элементов во вложенных списках:");
        List<List<Integer>> nestedLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8, 9)
        );
        System.out.println("Исходный список списков: " + nestedLists);

        // Сначала преобразуем каждый список в его длину
        List<Integer> lengths = GenericMethods.map(nestedLists, List::size);
        System.out.println("Длины списков: " + lengths);

        // Затем суммируем длины
        Integer totalElements = GenericMethods.reduce(lengths, 0, Integer::sum);
        System.out.println("Общее количество элементов: " + totalElements);

        // 4. Проверка на пустой список
        System.out.println("\n4. Проверка на пустой список:");
        List<String> emptyList = new ArrayList<>();
        String result = GenericMethods.reduce(emptyList, "Список пуст", (acc, s) -> acc + s);
        System.out.println("Результат для пустого списка: " + result);

        // Задание 3, задача 4: Коллекционирование
        System.out.println("\nЗадание 3, задача 4: Коллекционирование");

        // 1. Разделение на положительные и отрицательные (список: 1, -3, 7)
        System.out.println("\n1. Разделение чисел на положительные и отрицательные:");
        List<Integer> numbersToCollect = Arrays.asList(1, -3, 7);
        System.out.println("Исходный список: " + numbersToCollect);

        Map<String, List<Integer>> numberGroups = GenericMethods.collect(
                numbersToCollect,
                () -> {
                    Map<String, List<Integer>> map = new HashMap<>();
                    map.put("positive", new ArrayList<>());
                    map.put("negative", new ArrayList<>());
                    return map;
                },
                (map, number) -> {
                    if (number > 0) {
                        map.get("positive").add(number);
                    } else if (number < 0) {
                        map.get("negative").add(number);
                    }
                }
        );

        System.out.println("Положительные числа: " + numberGroups.get("positive"));
        System.out.println("Отрицательные числа: " + numberGroups.get("negative"));

        // 2. Группировка строк по длине (список: "qwerty", "asdfg", "zx", "qw")
        System.out.println("\n2. Группировка строк по длине:");
        List<String> stringsToGroup = Arrays.asList("qwerty", "asdfg", "zx", "qw");
        System.out.println("Исходный список: " + stringsToGroup);

        Map<Integer, List<String>> lengthGroups = GenericMethods.collect(
                stringsToGroup,
                HashMap::new,
                (map, str) -> {
                    int length = str.length();
                    map.computeIfAbsent(length, k -> new ArrayList<>()).add(str);
                }
        );

        System.out.println("Группы строк по длине:");
        for (Map.Entry<Integer, List<String>> entry : lengthGroups.entrySet()) {
            System.out.println("  Длина " + entry.getKey() + ": " + entry.getValue());
        }

        // 3. Уникальные строки
        System.out.println("\n3. Получение уникальных строк:");
        List<String> stringsWithDuplicates = Arrays.asList("qwerty", "asdfg", "qwerty", "qw");
        System.out.println("Исходный список: " + stringsWithDuplicates);

        Set<String> uniqueStrings = GenericMethods.collect(
                stringsWithDuplicates,
                HashSet::new,
                Set::add
        );

        System.out.println("Уникальные строки: " + uniqueStrings);

        scanner.close();
    }

    // Метод для демонстрации работы с коробкой
    public static void extractAndPrintValue(Box<Integer> box) {
        Integer value = box.get();
        System.out.println("Извлеченное значение из коробки: " + value);
    }

    // Метод для демонстрации работы с хранилищем
    public static <T> void extractAndPrintFromStorage(Storage<T> storage) {
        T value = storage.get();
        System.out.println("Получено значение из хранилища: " + value);
    }
}

// Задание 1, задача 1: Обобщенная коробка
class Box<T> {
    private T content;

    public Box() {
        this.content = null;
    }

    public void put(T item) {
        if (!isEmpty()) {
            throw new IllegalStateException("Коробка уже содержит элемент!");
        }
        this.content = item;
    }

    public T get() {
        T item = content;
        content = null; // Обнуляем ссылку
        return item;
    }

    public boolean isEmpty() {
        return content == null;
    }

    @Override
    public String toString() {
        return "Box{" + "content=" + content + ", isEmpty=" + isEmpty() + '}';
    }
}

// Задание 1, задача 2: Хранилище
class Storage<T> {
    private final T value;
    private final T alternative;

    public Storage(T value, T alternative) {
        this.value = value;
        this.alternative = alternative;
    }

    public T get() {
        return value != null ? value : alternative;
    }

    @Override
    public String toString() {
        return "Storage{" +  "value=" + value +  ", alternative=" + alternative + '}';
    }
}

// Задание 3: Обобщенные методы
class GenericMethods {

    // Задача 1: Функция
    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(function.apply(item));
        }
        return result;
    }

    // Задача 2: Фильтр
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // Задача 3: Сокращение
    public static <T> T reduce(List<T> list, T identity, BinaryOperator<T> operator) {
        T result = identity;
        for (T item : list) {
            result = operator.apply(result, item);
        }
        return result;
    }

    // Задача 4: Коллекционирование
    public static <T, R> R collect(List<T> list, Supplier<R> supplier, BiConsumer<R, T> accumulator) {
        R result = supplier.get();
        for (T item : list) {
            accumulator.accept(result, item);
        }
        return result;
    }
}