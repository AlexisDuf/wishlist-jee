var gulp       = require('gulp');
require('require-dir')('./gulp-tasks');

gulp.task('default', ['serve'],function(cb){
	cb();
});



