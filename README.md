# commons

This repository contains modules that are being shared between the [Open Data Service](https://github.com/jvalue/open-data-service) (ODS) and the [Complex Event Processing Service](https://github.com/jvalue/cep-service) (CEPS). They are:

- __auth__: BasicAuth and OAuth for securing REST based services that are built using [Dropwizard](http://www.dropwizard.io/). The module uses [CouchDb](https://couchdb.apache.org/) for storing user information as CouchDb is also the primary database being used by the ODS and CEPS.
- __couchdb__: utility classes for working with [CouchDb](https://couchdb.apache.org/), such as common configuration options or Java object wrapper repositories.
- __couchdb-test__: abstract base classes for testing custom CouchDb repositories.
- __mongodb__: utility classes for working with [MongoDB](https://www.mongodb.com/), such as common configuration options or Java object wrapper repositories.
- __mongodb-test__: abstract base classes for testing custom MongoDB repositories.
- __rest__: JAX RS utils and exception classes.
- __models__: model classes used by other modules in this project. This allows other services to only have these objects as dependencies, without also having to rely on Dropwizard and CouchDb.
- __utils__: everything else, e.g. Logging.
