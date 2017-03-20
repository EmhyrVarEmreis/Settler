'use strict';

var gulp = require('gulp');

var angularFileSort = require('gulp-angular-filesort');
var angularTemplateCache = require('gulp-angular-templatecache');
var browserSync = require('browser-sync');
var cleanCss = require('gulp-clean-css');
var debug = require('gulp-debug');
var del = require('del');
var filter = require('gulp-filter');
var fs = require('fs');
var gulpIf = require('gulp-if');
var inject = require('gulp-inject');
var naturalSort = require('gulp-natural-sort');
var ngAnnotate = require('gulp-ng-annotate');
var proxyMiddleware = require('http-proxy-middleware');
var rev = require('gulp-rev');
var revReplace = require('gulp-rev-replace');
var runSequence = require('run-sequence');
var uglify = require('gulp-uglify');
var useref = require('gulp-useref');
var util = require('gulp-util');
var vinylPaths = require('vinyl-paths');
var wiredep = require('wiredep').stream;

var config = {
    app:            'src/main/webapp',
    bootstrapDir:   'bower_components/bootstrap',
    fontAwesomeDir: 'bower_components/components-font-awesome',

    work: 'target/work/',
    dist: 'target/classes/static',

    port:           9000,
    apiPort:        8080,
    liveReloadPort: 35729
};

gulp.task('clean', function() {
    return gulp.src(
        [
            config.dist,
            config.work
        ],
        {
            read: false
        }
    )
        .pipe(vinylPaths(del))
});

gulp.task('copy', function() {
    return gulp.src(config.app + '/i18n/**')
        .pipe(gulp.dest(config.dist + '/i18n/'));
});

gulp.task('copy-fonts', function() {
    return gulp.src([config.bootstrapDir + '/fonts/**/*', config.fontAwesomeDir + '/fonts/**/*'])
        .pipe(gulp.dest(config.dist + '/fonts'));
});

gulp.task('copy-images', function() {
    return gulp.src(config.app + '/images/**/*')
        .pipe(gulp.dest(config.dist + '/images'));
});

gulp.task('process', function() {
    var injectStyles = gulp.src([
        config.app + '/**/*.css'
    ], {read: false});

    var injectScripts = gulp.src([
        config.app + '/scripts/**/*.js'
    ])
        .pipe(naturalSort())
        .pipe(angularFileSort());

    var injectOptions = {
        ignorePath: config.app,
        relative:   true
    };

    return gulp.src([config.app + '/**/*.html', '!' + config.app + '/scripts/**/*.html'])
        .pipe(inject(injectScripts, injectOptions))
        .pipe(inject(injectStyles, injectOptions))
        .pipe(wiredep(
            {
                'onError': function(err) {
                    util.log('err');
                }
            }
        ))
        .pipe(gulp.dest(config.work))
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('styles', function() {
    return gulp.src([config.app + '/**/*.css'])
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('templates', function() {
    return gulp.src([config.app + '/scripts/**/*.html'])
        .pipe(angularTemplateCache({
            module: 'settlerApplication',
            root:   'scripts'
        }))
        .pipe(gulp.dest(config.work));
});

gulp.task('html', ['copy', 'copy-fonts', 'copy-images', 'templates', 'process'], function() {

    var templatesInjectFile = gulp.src(config.work + '/templates.js', {read: true});
    var templatesInjectOptions = {
        starttag: '<!-- inject:templates -->',
        relative: true
    };

    var indexHtmlFilter = filter(['**/*', '!**/index.html'], {restore: true});

    return gulp.src([config.work + '/index.html'])
        .pipe(inject(templatesInjectFile, templatesInjectOptions))
        .pipe(useref({
            searchPath: [config.app, config.work]
        }))
        .pipe(gulpIf('*.js', ngAnnotate()))
        .pipe(gulpIf('*.js', uglify()))
        .pipe(gulpIf('*.css', cleanCss({compatibility: 'ie8'})))
        .pipe(indexHtmlFilter)
        .pipe(rev())
        .pipe(indexHtmlFilter.restore)
        .pipe(revReplace())
        .pipe(gulp.dest(config.dist));
});

gulp.task('build', ['html'], function() {
    return gulp.src(
        [
            config.work
        ],
        {
            read: false
        }
    )
        .pipe(vinylPaths(del))
});

gulp.task('serve', ['process'], function() {

    gulp.watch([config.app + '/**/*.html', config.app + '/**/*.js', 'bower.json'], ['process']);
    gulp.watch([config.app + '/**/*.css'], function(event) {
        if (event.type === 'changed') {
            gulp.start('styles');
        } else {
            gulp.start('process');
        }
    });

    browserSync.init({
        startPath: '/',
        server:    {
            baseDir:    [config.work, config.app],
            middleware: [
                proxyMiddleware('/api', {target: 'http://localhost:8080'})
            ],
            routes:     {
                '/bower_components': 'bower_components'
            }
        },
        browser:   []
    })
});

gulp.task('default', function() {
    return runSequence(
        'clean',
        'build');
});
