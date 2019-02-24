# pollite-backend

Backend server for the Pollite webapp.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed to manage dependencies
and building.

[leiningen]: https://github.com/technomancy/leiningen

You will also need to copy the example properties file in the `resources` directory
into a file with the same name without the `.EXAMPLE` at the end. In this file
set your database connection string, username, and password.

## Running

To start a web server for the application, run:

    lein ring server
    
Currently the server runs at `localhost:3000`.

## License

Copyright © 2019 Jon Berrend
