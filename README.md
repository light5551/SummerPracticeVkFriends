# Visualizator VK Friends

## Спецификация
* Задача: Требуется визуализировать граф общих друзей в социальной сети vk.com для заданного пользователем списка участников социальной сети. Вершины графа должны однозначно идентифицировать пользователя социальной сети.
  
  * Дополнение: При нажатии на одного из друзей из графа будут выделены общие друзья пользователя и выбранного друга. Так же будет видно пользователей, которые онлайн на данный момент.

* Структура данных: бор. 

* Алгоритм: Построение бора по списку ID клиента
  1. Корнем бора является пользователь, чей ID был получен на вход.
  2. Если буквы, на которую начинается имя друга еще не было, то добавить ребро из корня к вершине друга. Каждая вершина хранит имя и фамилию друга. 
  3. Ребро к следующему другу будет вести из вершины с одинаковой начальной буквой или из корня. Т.к. список отортирован в лексикографическом порядке, то каждая следующая вешина будет располагаться под предыдущей.
  4. Граф считается построенным, если в списке не осталось необработанных вершин.
  
  
  *Пример построенного графа*
  
  ![](https://pp.userapi.com/c849224/v849224173/1d36f1/kkeyfDUPqDI.jpg)


* Входные данные: в специальную строку пользователь вводит id клиента социальной сети ВКонтаке.

* Выходные данные: граф друзей.

* GUI: диалоговое окно со строкой для ввода id пользователя, кнопка «Построить граф», окно с построенным графом, ползунок для прокрутки страницы и кнопки для управления окном.


  *Пример GUI*

  ![](https://pp.userapi.com/c852128/v852128133/163dca/4vOi0nRy3X8.jpg)
  ![](https://cdn.discordapp.com/attachments/456423472923672577/596050993259741205/q2MrT2IY-oA.png)
  
## План разработки:
- [x] распределение ролей
- [x] получение списка ID друзей выбранного пользователя
- [x] разработка прототипа GUI
- [x] поиск библиотеки для визуализации графа
- [x] разработка начальной версии графа друзей
- [x] поиск библиотеки для тестирования
- [x] написание алгоритма построения бора по списку ID друзей
- [ ] первичная сборка проекта
- [ ] тестирование правильности построения списка ID и последующего постороения бора
- [ ] *исправление ошибок*
- [ ] добавление дополнительных опций 
- [ ] тестирование дополнительных опций

Ответственные:
1. [Глазунов Сергей](https://github.com/light5551) (7382) - VK API, логика задач.
2. [Петрова Анна](https://github.com/aaapetrova) (7382) - тестирование, написание отчетов.
3. [Токарев Андрей](https://github.com/yawningstudent) (7382) - разработка GUI, построение графа.
# 
**Первая итерация**
```
1. Получение списка ID друзей пользователя.
2. Визуализация пробной версии графа друзей.
3. Библиотека для написания юнит-тестов JUnit.
```

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
