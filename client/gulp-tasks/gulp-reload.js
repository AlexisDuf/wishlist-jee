var gulp        = require('gulp');
var browserSync = require('browser-sync');

/**
*reload browser
**/
gulp.task('reload',['build'],function(){
  return browserSync.reload;
});









