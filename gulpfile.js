'use strict';
var path = require('path');
var gulp = require('gulp');
var eslint = require('gulp-eslint');
var excludeGitignore = require('gulp-exclude-gitignore');
var mocha = require('gulp-mocha');
var istanbul = require('gulp-istanbul');
var plumber = require('gulp-plumber');
var coveralls = require('gulp-coveralls');

gulp.task('static', function () {
  return gulp
    .src('**/*.js')
    .pipe(excludeGitignore())
    .pipe(eslint())
    .pipe(eslint.format())
    .pipe(eslint.failAfterError());
});

gulp.task('pre-test', function () {
  return gulp
    .src('generators/**/index.js')
    .pipe(excludeGitignore())
    .pipe(
      istanbul({
        includeUntested: true
      })
    )
    .pipe(istanbul.hookRequire());
});

gulp.task(
  'test',
  gulp.series('pre-test', cb => {
    var mochaErr;

    gulp
      .src('test/**/*.js')
      .pipe(plumber())
      .pipe(mocha({reporter: 'spec'}))
      .on('error', function (err) {
        mochaErr = err;
      })
      .pipe(istanbul.writeReports())
      .on('end', function () {
        cb(mochaErr);
      });
  })
);

gulp.task('watch', function () {
  gulp.watch(['generators/**/*.js', 'test/**'], ['test']);
});

gulp.task(
  'coveralls',
  gulp.series('test', () => {
    if (!process.env.CI) {
      return;
    }

    return gulp
      .src(path.join(__dirname, 'coverage/lcov.info'))
      .pipe(coveralls());
  })
);

gulp.task('default', gulp.series('static', 'test'));
