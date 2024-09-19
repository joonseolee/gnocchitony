# autobank



### application.properties values
- spring.datasource.url
- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
- spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
- spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
- auth0.audience=
- spring.security.oauth2.resourceserver.jwt.issuer-uri=
- auth0.domain=
- azure.storage.container-name=
- azure.storage.connection-string=
- environment = dev | prod
- superadmin.emails =

## Current endpoints
**Header required for all requests**

```"Authorization" "Bearer <access token>"```

### /api/auth/check
```GET```
```
{
    "success": Boolean,
    "isadmin": Boolean,
    "issuperadmin": Boolean,
}
```

### /api/receipt/create
```POST```
```
 {
    "receipt": {
        "amount": Double,
        "description": String,
        "name": String,
        "committee_id": Integer
    }, 
    "attachments": [
        base64 string, base64 string, ...
    ], 
    "receiptPaymentInformation": {
        cardnumber: String?,
        accountnumber: String?,
        usedOnlineCard: Boolean,
    }
}
```

### /api/economicrequest/create
```POST```
```
{
  "subject": String,
  "purpose": String,
  "date": "YYYY-MM-DD",
  "duration": String,
  "description": String,
  "amount": Double,
  "personCount": Integer,
  "names": String,
  "paymentDescription": String,
  "otherInformation": String,
}

```



