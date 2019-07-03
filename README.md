# Visualizator vk friends

## Quick Start
 краткая инструкция для запуска программы

## Main tasks
- [ ]  Get list of friends of user  
- [ ] Graph-visualizator
- [ ] Tests

## Rules for contributors
* After any pull request need to change [quick start](https://github.com/light5551/SummerPracticeVkFriends/new/master?readme=1#quick-start)
* Pull request Example:
```
#<number of issue>
Short test case
Expected result
```
* Any variables should be camel case. Example: `camelCase`
* **Don't** merge pull request before any checks
* Every pull request should have label of type issue. Example: `bug`, `tests`, `vk_api`, `visualizator` and so on
* Pull request shouldn't be very big!
* Everynissue should have some comment
* One pull request only **one** issue
### Tests
* There must be individual branch for tests. This branch wont be merged in master ever!
## Contributors
1. [Glazunov Sergey](https://github.com/light5551) (7382)
2. [Petrova Anna](https://github.com/aaapetrova) (7382)
3. [Tokarev Andrew](https://github.com/yawningstudent) (7382)

## Спецификация (черновой вариант)
* Задача: написать программу, которая, при получении id человека в социальной сети ВКонтакте, показывает «граф» всех его друзей.
* Индивидуализация: при нажатии на любого друга подсвечиваются их общие друзья. Исключение – приватные профили. Так же будет видно, онлайн ли пользователь на данный момент.
* Структура данных: бор. 
* Алгоритм: Ахо-Корасик.
* Входные-выходные данные: в специальную строку пользователь вводит id и получает бор из всех друзей владельца страницы. Если у пользователя приватный профиль, id был некорректный, то выводится соответствующее сообщение об ошибке. 
* GUI: диалоговое окно со строкой для ввода id пользователя, кнопка «Построить граф», окно с построенным графом, ползунок для прокрутки страницы и кнопки для управления окном.
