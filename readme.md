## Что необходимо сделать

Нужно собрать бэкенд-часть сайта на Java.

## Структура проекта

    
    ├── java
      ├── ru.skypro.homework
        ├── controller
          ├── AdsController.java
          ├── AuthController.java
          ├── BasicController.java
          ├── ImageController.java
          ├── UserController.java
        ├── dto
          ├── AdsComment.java
          ├── AdsDto.java
          ├── CreateAds.java
          ├── CreateUser.java
          ├── FullAdsDto.java
          ├── LoginReq.java
          ├── NewPassword.java
          ├── RegisterReq.java
          ├── ResponseWrapper.java
          ├── Role.java
          ├── User.java
        ├── entity
          ├── Ads.java
          ├── Comment.java
          ├── FullAds.java
          ├── Image.java
          ├── UserProfile.java
        ├── exception
          ├── AccessDeniedException.java
          ├── AdsNotFoundException.java
          ├── CommentNotFoundException.java
          ├── ImageNotFoundException.java
          ├── NullEmailException.java
          ├── UserNotFoundException.java
        ├── mapper
          ├── AdsMapper.java
          ├── CommentMapper.java
          ├── UserMapper.java
        ├── repository
          ├── AdsRepository.java
          ├── CommentRepository.java
          ├── ImageRepository.java
          ├── UserProfileRepository.java
        ├── service
          ├── impl
            ├── AdsServiceImpl.java
            ├── AuthServiceImpl.java
            ├── UserServiceImpl.java
          ├── AdsService.java
          ├── AuthService.java
          ├── UserService.java
      ├── HomeworkApplication.java
      ├── WebSecurityConfig.java
   

## Бэкенд-часть проекта предполагает реализацию следующего функционала:

- Регистрация и авторизация пользователей;
- Распределение ролей между пользователями: пользователь и администратор;
- CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи — только
  свои;
- Под каждым объявлением пользователи могут оставлять отзывы;
- В заголовке сайта можно осуществлять поиск объявлений по названию;
- Показывать и сохранять картинки объявлений;

## Этапы проекта 

**Этап I.** Настройка Spring-проекта.

**Этап II.** Настройка авторизации и аутентификации.

**Этап III.** Описание моделей объявлений и отзывов.

**Этап IV.** Определение permissions к контроллерам.

**Этап V.** Сохранение и получение картинок.

**Этап VI.** Финальная доработка проекта и создание презентации.

## Исходные данные

[https://github.com/skypro-backend/example-for-graduate-work.git](https://github.com/skypro-backend/example-for-graduate-work.git)

## Фронтенд контейнер для Docker’а

https://drive.google.com/file/d/1ZoGOJaHidywKNYlvNuz6kb0KoGPbeC_b/view?usp=sharing
docker run --rm --name skypro-frontend-diploma-image-instance -p3000:3000 adsclient:v10
http://localhost:3000

## Таблицы

![Структура базы данных](https://github.com/kulich51/graduate_work_command_4/tree/dev/images/db_diagram.png)

Таблицы:
- ads (хранит объявления)
- authorities (хранит роли пользователей)
- comments (хранит комментарии пользователей)
- image (хранит картинки объявлений)
- users (хранит пароли пользователей)
- users_profiles (хранит данные пользователей)



