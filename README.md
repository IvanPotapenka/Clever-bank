CLEVER-BANK WEB APP

Веб приложение Clever-Bank, реализующее функционал банковского приложения.

Стек технологий:
- Java 17
- Gradle
- PostgreSQL
- JDBC
- Lombok
- Servlets
- Docker

- О проекте

В данном приложении реализованы регистрация нового клиента, аунтификация, операции открытия счета, 
пополнения и снятия средств со счета, а также реализована возможность перевода средств другому клиенту
этого же банка или  клиенту другого банка.

Реализован функционал формирования выписки по транзакциям клиента банка за месяц, год или весь период 
обслуживания клиента в форматах PDF, выписка сохраняется в папке statement в корне проекта.

Регулярно, по расписанию (один раз в полминуты), происходит проверка, нужно ли начислять
проценты на остаток накопительно счета в последний день месяца.

После каждой операции(пополнение, снятие средств, перевод) формируется чек по шаблону в формате PDF,
все чеки сохраняются в папку check, в корне проекта.

Реализован web-интерфейс на все выше упомянутые возможности приложения.

- Инстркция по запуску проекта

Скачать Docker image "clever-bank" и запустить в командной строке (docker run -p 8080:8080 clever-bank) 

Пример использования:

Запустить проект

1. Вход в систему

2. Если нет аккаунта пройти регистрацию.

- выбрать один из пяти банков(Clever-bank, Iron-Bank, Alfa-Bank, VTB, Techno)
- ввести ФИО
- email
- пароль

После регистрации вернуться на страницу входа

3. После успешного входа в систему можно "Открыть счет" или посмотреть "Мои счета"

4. Открыть счет
- выбрать тип счета Расчетный или Накопительный;
- выбрать валюту счёта;

5. Мои счета 

- Тут отобразится подробная информация обо всех имеющихся счетах

         Пример отображения расчетного счета:

         Клиент	        Potapenko Ivan Urevich
         Номер счёта	BY01IRON51928966927063115235
         Дата окрытия	2023-08-31
         Тип счёта  	Расчётный
         Валюта	        BYN
         Баланс	        90.0

         Пример отображения накопительно счета:

         Клиент	         Potapenko Ivan Urevich  
         Номер счёта 	 BY25IRON31852071838162730381
         Дата окрытия	 2023-08-31
         Тип счёта	     Сберегательный
         Валюта          BYN
         Баланс	         789.92
         Сумма процентов 85.68

6. На каждом счете есть 4 операции "Пополнить", "Снять", "Перевести", "Выписка"

7. Перевод 

При переводе требуется указать:
- номер счета получателя(IBAN 28 символов);
- выбрать валюту перевода;
- указать сумму перевода;

Если средств недостаточно на счете будет выведено сообщение "Недостаточно средств"

8. Пополнение 

         Пополнение счёта
         Счёт для пополнения:    BY01IRON51928966927063115235
         Валюта текущего счета   BYN

         Введите сумму
         10.00 BYN

         Пополнить

9. Снятие средств http://localhost:8080/my-accounts/withdraw/account?id=1

         Снять средства
         Счёт для снятия:        BY01IRON51928966927063115235
         Валюта текущего счета   BYN

         Введите сумму
         5.00 BYN

         Снять

 После успешно выполненой опрации на экран выведется соответсвующие сообщение и
будет сформирован чек и сохранен в папку "Check"  в корне проекта

     Пример чека:

               Банковский чек
     Чек:              5
     2023-09-01        00:59:47
     Тип транзакции:   Снятие средств
     Банк отправителя: Iron-Bank
     Банк получателя:  Iron-Bank
     Счёт отправителя: BY01IRON51928966927063115235
     Счёт получателя:  BY01IRON51928966927063115235
     Сумма             4.0BYN

10. Выписка за выбранный период 

                     Пример выписки по расчетному счёту

                Выписка по счёту            BY01IRON51928966927063115235
                Клиент	                    Potapenko Ivan Urev
                Счёт	                    BY01IRON51928966927063115235
                Валюта	                    BYN
                Дата открытия	            2023-09-01
                Период	                    2023-08-31 - 2023-09-01
                Дата и время формирования	01-09-2023, 01:02
                Остаток	                    86.0 BYN

           Дата	                          Примечание	                       Сумма
          ----------------------------	----------------------------	----------------------------
          2023-08-31	                       Пополнение	                           100.0
          2023-08-31	                       Снятие средств	                       10.0
          2023-09-01	                       Снятие средств	                       4.0




