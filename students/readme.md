# Приложение "Студенты"
## Возможности:
- Выводить все имеющиеся записи о студентах
- Добавлять информацию (имя, фамилию и возраст) о новом студенте, при этом приложение выводит
в консоль информацию о созданном студенте
- Удалять информацию о студенте по определенному идентификатору, при этом приложение выводит
  в консоль идентификатор удалённого студента
- Полностью очищать список студентов.
- Есть возможность создавать студентов при старте приложения. Этой функцией можно управлять через настройку
      app.init.enabled в файле application.yaml:
  > true - создать в базе данных случайное количество студентов при старте приложения

  > false - не создавать студентов в базе данных при старте приложения

---
- Есть возможность запустить приложение, используя Docker.
  > собрать контейнер -  docker build -t students .

  > запустить контейнер - docker run students

Команды необходимо вводить в терминале из папки приложения