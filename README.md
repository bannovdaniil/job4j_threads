# job4j_threads

job4j.ru middle java developer

1. Теория
2. Создание и запуск нити [ru.job4j.concurrent.ConcurrentOutput]
   В метод main класса ru.job4j.concurrent.ConcurrentOutput создайте еще один объект Thread. Присвойте имя переменной
   second. В конструкторе нового объекта задайте вывод на консоль имени новой нити. Для этого воспользуйтесь оператором.
   ``` Thread.currentThread().getName(); ```
3. Состояние нити. [ru.job4j.concurrent.ThreadState]
   Поправьте класс ThreadState там образом, чтобы в нем создавалось две нити. Каждая нить должна вывести свое имя на
   консоль.
