# CountriesDictionary

При написании программы используется JDK 22

Создание JAR-файла в IntelliJ IDEA
Откройте проект в IntelliJ IDEA.

Нажмите File > Project Structure (или используйте сочетание клавиш Ctrl + Alt + Shift + S).

В левой панели выберите Artifacts.

Нажмите + (плюс) и выберите JAR > From modules extensions dependencies.

В появившемся окне:

Выберите Main Class (класс с методом main).

Убедитесь, что все необходимые зависимости включены.

Нажмите OK.

В разделе Output Layout убедитесь, что все необходимые файлы и ресурсы добавлены в JAR.

Нажмите Apply, затем OK.

Теперь нажмите Build > Build Artifacts.

Выберите ваш артефакт (JAR) и нажмите Build.


Запуск программы java -jar CountriesDictionary.jar