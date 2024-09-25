# caloricity_backend

Расчёт калорийности по "МЕТОДИЧЕСКИЕ УКАЗАНИЯ ПО ГИГИЕНИЧЕСКОМУ КОНТРОЛЮ ЗА ПИТАНИЕМ В ОРГАНИЗОВАННЫХ КОЛЛЕКТИВАХ (УТВ. МИНЗДРАВОМ СССР 29.12.1986 N 4237-86): https://otr-soft.ru/org/zakony_pitanie/mu_4237-86/"

## Modules diagram

![Modules diagram](./components-CaloricityApplication.png)

## Docker compose

```bash
docker compose -f ./docker-compose.dev.yml -p caloricity up -d
```

## Test database

```sql
CREATE DATABASE caloricity_test WITH OWNER caloricity ENCODING 'UTF-8';
```

## TODO

- Масса сухого остатка = масса до высушивания минус масса после высушивания (Для исследований)
- Add error descriptions to swagger https://habr.com/ru/articles/814061/
- Add protocols module
- Add users module
- Add AuditEvents https://docs.spring.io/spring-boot/api/java/org/springframework/boot/actuate/audit/AuditEvent.html
