var gulp        = require('gulp');
var package     = require('../package.json');
var browserify  = require('browserify');
var babelify    = require('babelify');
var notify      = require("gulp-notify");
var source      = require('vinyl-source-stream');
var clean       = require('gulp-clean');



gulp.task('build-js',['clean'], function (cb) {
  return browserify(package.paths.app)
  .transform(babelify, {presets: ["es2015", "react"]}) // JSX and ES6 to JS
  .bundle() // Browserify bundles required files
  .pipe(source(package.dest.app))
  .pipe(gulp.dest(package.dest.js))
  .on('error', console.error.bind(console))
  .on("error", notify.onError({
    message: 'Error: <%= error.message %>',
    sound: 'Sosumi'
  }))

});













