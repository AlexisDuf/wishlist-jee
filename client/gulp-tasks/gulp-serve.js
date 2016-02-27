var gulp        = require('gulp');
var package     = require('../package.json');
var browserSync = require('browser-sync').create();

/**
*serve
**/
gulp.task('serve', ['clean', 'build'], function() {
	browserSync.init({
        server: {
            baseDir: "./"
        }
    });
  	gulp.watch([package.paths.js, package.paths.html, package.paths.jsx], ['clean', 'build','reload']);

})

gulp.task('reload',['clean', 'build'],function(){
	return browserSync.reload;
});
