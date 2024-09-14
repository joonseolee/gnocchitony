# autobank


All endpoints require are protected. Require valid access token (recieved after auth0 login) in the Authorization header. Use /api/auth/check to check if user exists (or create user from token).

### application.properties values
- spring.datasource.url
- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
- spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
- spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
- auth0.audience=
- spring.security.oauth2.resourceserver.jwt.issuer-uri=
- auth0.domain=