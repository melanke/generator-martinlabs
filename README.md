# generator-martinlabs [![NPM version][npm-image]][npm-url]
> Generates a CRUD based on a MySQL database schema located on your computer, using Java and Vue, we use this generator for production comercial large systems, open a issue to suggest an improvement :)

## Installation

First, install [Yeoman](http://yeoman.io) and generator-martinlabs using [npm](https://www.npmjs.com/) (we assume you have pre-installed [node.js](https://nodejs.org/)).

```bash
npm install -g yo
npm install -g generator-martinlabs
```

Then, you only need to create a folder, open it on terminal and use this command:

```bash
yo martinlabs
```
It will ask you some questions to make the result more adequate to you

## We are working to maintain our Java dependency

Today we are using martinlabs-commons dependency, which is on MavenCentral but is a little outdated, so, really soon it will be usable again

## Features

First of all, the code is clean, you will understand what is happening, I will make a video later to show everything you need to know.
- A server that is ready for use in a Native Mobile App, no server-side rendering, it is all a Rest API
- Simple Login system with a criptographed token storing credentials
- Transactions: when something throws an exception all database rollsback to it's original state before the operation begin
- Pure MySQL connection, No ORM. Why? It's easiest to make powerful queries with high performance without ORM, and since we have a generator, we do not need an ORM to create simple queries for us
- UnitTests of EVERYTHING! 100% of code coverage, now you are sure that all methods are working, the server will not build if it reaches less than 96% of coverage of tests, you can disable this on `pom.xml`
- Validation of null, empty, length, numeric and date types, e-mail fields and more.
- Compiling Server with Maven
- Vue.JS for client because it's the cleanest solution for this kind of software, Angular and React were evaluated, but Vue won the job
- Vue plugins like: v-mask (input mask), vue-i18n (translation), vue-moment (show dates), vue-resource (fetch data to our server), vue-router (to make a single page app), vue-strap (just for alert, I am considering to remove it)
- Sass with bootstrap for general styling
- Other libraries: js-sha1 (for passwords), lodash (object manipulation), moment (working with dates), simplestorage.js (local storage)
- Compiling javascript with grunt, browserify, babel and vueify

**WOW!** Seems a lot! Well, we are working on it and are trying a lot of technologies for a long time, improving and updating this generator constantly. 

## Martinlabs
www.martinlabs.com.br

## License

Apache-2.0 © [Gil Lopes Bueno]()


[npm-image]: https://badge.fury.io/js/generator-martinlabs.svg
[npm-url]: https://npmjs.org/package/generator-martinlabs
[travis-image]: https://travis-ci.org/martinlabs/generator-martinlabs.svg?branch=master
[travis-url]: https://travis-ci.org/martinlabs/generator-martinlabs
[daviddm-image]: https://david-dm.org/martinlabs/generator-martinlabs.svg?theme=shields.io
[daviddm-url]: https://david-dm.org/martinlabs/generator-martinlabs
