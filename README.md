# Interview darbas

## Apie projekta
Projektas buvo daromas su VSCode (Extensions: Springboot, Lombok, Java).

Projektas buvo istestuotas ant `Debian GNU/Linux 12 (bookworm)` ir `Windows 11`.

__Java 17 required.__

## Kaip paleisti darba:
Isvalyti projekta: `make clean`

Subuildint projekta: `make build`

Paleisti lokalu serveri: `make run`

## Kaip istestuoti:
As naudojau Postman, galima paziureti: `Interview.postman_collection.json` faila ir ji importuoti kaip collectiona.

Arba galima naudoti CURL komandas:

### Create Payment: TYPE1
```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 100.00,
    "currency": "EUR",
    "type": "TYPE1",
    "debtorIban": "LT1234567890123456",
    "creditorIban": "LT6543210987654321",
    "details": "Invoice #123"
  }'
```

### Create Payment: TYPE2
```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 150.50,
    "currency": "USD",
    "type": "TYPE2",
    "debtorIban": "US1234567890123456",
    "creditorIban": "US6543210987654321"
  }'
```

### Create Payment: TYPE3
```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 200.00,
    "currency": "EUR",
    "type": "TYPE3",
    "debtorIban": "LT1234567890123456",
    "creditorIban": "LT6543210987654321",
    "creditorBic": "BANKLT21"
  }'
```

### List All Active Payments
Grazina visus aktyvius (ne cancelled) paymentus.
```bash
curl -X GET http://localhost:8080/payments
```

### Get Payment by ID
Gauti Payment pagal jo ID.

`{id}` = Payment identifikatorius duombazej
```bash
curl -X GET http://localhost:8080/payments/{id}
```

### Cancel Payment
Atsaukti Payment

`{paymentId}` = Payment identifikatorius duombazej
```bash
curl -X POST http://localhost:8080/payments/{paymentId}/cancel
```

### Get All Notifications
Atiduoda visus sukurtus notificationus apie sekmingai sukurtus paymentus.
```bash
curl -X GET http://localhost:8080/notify
```