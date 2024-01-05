# job4j_threads

job4j.ru middle java developer

### 1, 2. Git теория и практика, экзамен.

### 3. Threads

#### 3.1.1. Threads

1. Теория многопоточность, параллелизм и многопоточность.
   a) Java 8, Полное руководство - Герберт Шилдт. Глава 11 (стр. 285)
   b) Библиотека профессионала. Java. Том 1. Основы - Кей Хостманн, Гари Корнелл. Глава 14 (стр. 753)
   c) Head First Java - Кэти Сьерра и Берт Бейтс. Глава 15 (стр. 519)
   d) Философия Java - Брюс Эккель. Глава 21 (стр. 887)

2. Создание и запуск нити [ru.job4j.concurrent.ConcurrentOutput]
   В метод main класса ru.job4j.concurrent.ConcurrentOutput создайте еще один объект Thread. Присвойте имя переменной
   second. В конструкторе нового объекта задайте вывод на консоль имени новой нити. Для этого воспользуйтесь оператором.
   ``` Thread.currentThread().getName(); ```

3. Состояние нити. [ru.job4j.concurrent.ThreadState]
   Поправьте класс ThreadState там образом, чтобы в нем создавалось две нити. Каждая нить должна вывести свое имя на
   консоль.

4. Режим ожидания. [ru.job4j.concurrent.Wget]
   Создайте нить внутри метода main. В теле метода создайте цикл от 0 до 100 %. Через 1 секунду выводите на консоль
   информацию о загрузке. Вывод должен быть с обновлением строки.

5. Прерывание нити [ru.job4j.concurrent.ConsoleProgress]
   Создайте класс ru.job4j.concurrent.ConsoleProgress. Этот класс будет использован для вывода процесса загрузки в
   консоль.
   Этот класс должен реализовывать интерфейс java.lang.Runnable. Внутри метода run нужно добавить цикл с проверкой
   флага. Внутри цикла - сделать задержку в 500 мс. В тело цикла добавьте вывод в консоль.
   "Loading ... |." Символ "палочка" каждые 500мс должен меняться на символы:
   ```var process = new char[] {'-', '\', '|', '/'};```
   Последовательная смена символов создадут динамический эффект загрузки.
6. Прерывание блокированной нити. [ru.job4j.concurrent.ConsoleProgress]
   Если используются методы sleep(), join(), wait() или аналогичные временно блокирующие поток методы, то нужно в блоке
   catch вызвать прерывание. (Thread.currentThread().interrupt();)
7. Приоритеты нитей и нити-демоны (теория).
8. Скачивание файла с ограничением. [ru.job4j.concurrent.Wget]
   Вносим изменения в класс из пункта (4) историю изменений смотрим в git.
   В этом задании нужно написать консольную программу - аналог wget. Программа должна скачивать файл из сети с
   ограничением по скорости скачки.

#### 3.1.2. Общие ресурсы

1. Синхронизация общих ресурсов. [ru.job4j.Cache]
   Исправить ошибку атомарности в коде.
2. 