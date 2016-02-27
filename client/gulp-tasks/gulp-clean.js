var gulp        = require('gulp');
var del         = require('del');
var package     = require('../package.json');

/**
 * Cleaning dist/ folder
 */
gulp.task('clean', function(cb) {
	del([package.dest.js+"**/*.*", package.dest.css+"**/*"],cb);
})
