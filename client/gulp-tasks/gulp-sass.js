var gulp 	= require('gulp');
var sass 	= require('gulp-sass');
var package = require('../package.json');

 
gulp.task('sass', function () {
  gulp.src(package.paths.scssIndex)
    .pipe(sass().on('error', sass.logError))
    .pipe(gulp.dest(package.dest.css));
});