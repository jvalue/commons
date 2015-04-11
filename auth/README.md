# auth


## Setup

The `UserRepository` requires a named `CouchDbConnector` to be injected. Add the following to a guice
`AbstractModule` to setup dependency injection:

```java
CouchDbConnector userConnector = connectorFactory.createConnector(UserRepository.DATABASE_NAME, true);
bind(CouchDbConnector.class).annotatedWith(Names.named(UserRepository.DATABASE_NAME)).toInstance(userConnector);
```
