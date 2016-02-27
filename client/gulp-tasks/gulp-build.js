var gulp        = require('gulp');

/**
* build
**/
gulp.task('build', ['clean', 'build-js', 'sass'], function (cb) {
  cb();
})