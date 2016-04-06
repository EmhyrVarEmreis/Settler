'use strict';

var gulp = require('gulp'),
    debug = require('gulp-debug'),
    inject = require('gulp-inject'),
    gulpif = require('gulp-if'),
    angularFilesort = require('gulp-angular-filesort'),
    angularTemplatecache = require('gulp-angular-templatecache'),
    runSequence = require('run-sequence'),
    browserSync = require('browser-sync'),
    minifyCss = require('gulp-minify-css'),
    useref = require('gulp-useref'),
    uglify = require('gulp-uglify'),
    ngAnnotate = require('gulp-ng-annotate'),
    rev = require('gulp-rev'),
    revReplace = require('gulp-rev-replace'),
    clean = require('gulp-clean'),
    wiredep = require('wiredep').stream,
    fs = require('fs'),
    proxyMiddleware = require('http-proxy-middleware');

var karma = require('karma').server;

var config = {
    app:          'src/main/web/',
    scss:         'src/main/scss/',
    test:         'src/test/javascript/spec/',
    bootstrapDir: 'bower_components/bootstrap',

    work: 'target/work/',
    dist: 'target/classes/static',

    port:           9000,
    apiPort:        8080,
    liveReloadPort: 35729
};

gulp.task('clean', function () {
    return gulp.src([config.dist, config.work], {read: false})
        .pipe(clean());
});

gulp.task('copy', function () {
    return gulp.src(config.app + 'i18n/**')
        .pipe(gulp.dest(config.dist + 'i18n/'));
});

gulp.task('copy-fonts', function () {
    return gulp.src(config.bootstrapDir + '/fonts/**/*')
        .pipe(gulp.dest(config.dist + '/fonts'));
});

gulp.task('copy-images', function () {
    return gulp.src(config.app + '/images/**/*')
        .pipe(gulp.dest(config.dist + '/images'));
});

gulp.task('preprocess', function () {
    var injectStyles = gulp.src([
        config.app + '/**/*.css'
    ], {read: false});

    var injectScripts = gulp.src([
        config.app + '/scripts/**/*.js'
    ]).pipe(angularFilesort());

    var injectOptions = {
        relative: true
    };

    return gulp.src([config.app + '**/*.html', '!' + config.app + 'scripts/**/*.html'])
        .pipe(inject(injectScripts, injectOptions))
        .pipe(inject(injectStyles, injectOptions))
        .pipe(wiredep())
        .pipe(gulp.dest(config.work))
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('preprocess-test', function () {
    var wiredepOpts = {
        ignorePath:      '../../../',
        devDependencies: true
    };

    var injectScripts = gulp.src([
        config.app + '/scripts/**/*.js'
    ]).pipe(angularFilesort());

    var injectOptions = {
        relative: true
    };


    return gulp.src('src/test/karma.conf.js')
        .pipe(wiredep(wiredepOpts))
        .pipe(inject(injectScripts, injectOptions))
        .pipe(gulp.dest('target/test/javascript'));
});

gulp.task('styles', function () {
    return gulp.src([config.app + '**/*.css'])
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('templates', function () {
    return gulp.src([config.app + 'scripts/**/*.html'])
        .pipe(angularTemplatecache({
            module: 'settlerApplication',
            root:   'scripts'
        }))
        .pipe(gulp.dest(config.work));
});

gulp.task('html', ['copy', 'copy-fonts', 'copy-images', 'templates', 'preprocess'], function () {
    var assets = useref.assets({
        searchPath: [config.work, config.app]
    });

    var templatesInjectFile = gulp.src(config.work + 'templates.js', {read: true});
    var templatesInjectOptions = {
        starttag: '<!-- inject:templates -->',
        relative: true
    };

    return gulp.src([config.work + '**/*.html'])
        .pipe(inject(templatesInjectFile, templatesInjectOptions))
        .pipe(assets)
        .pipe(gulpif('*.js', ngAnnotate()))
        .pipe(gulpif('*.js', uglify()))
        .pipe(gulpif('*.css', minifyCss({compatibility: 'ie7'})))
        .pipe(rev())
        .pipe(assets.restore())
        .pipe(useref())
        .pipe(revReplace())
        .pipe(gulp.dest(config.dist));
});

gulp.task('build', ['html'], function () {
});

gulp.task('serve', ['preprocess'], function () {

    gulp.watch([config.app + '**/*.html', config.app + '**/*.js', 'bower.json'], ['preprocess']);
    gulp.watch([config.app + '**/*.css'], function (event) {
        if (event.type === 'changed') {
            gulp.start('styles');
        } else {
            gulp.start('preprocess');
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

gulp.task('test', ["preprocess-test"], function (done) {
    return karma.start({
        configFile: __dirname + '/target/test/javascript/karma.conf.js',
        singleRun:  true
    }, done)
});

gulp.task('default', function () {
    return runSequence(
        'clean',
        'build');
});
