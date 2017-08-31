'use strict';

const Generator = require('yeoman-generator');
const mkdirp = require('mkdirp');
const yosay = require('yosay');
const chalk = require('chalk');
const ncp = require('ncp').ncp;

module.exports = Generator.extend({
  initializing: function () {
    this.props = {};
  },
  prompting: function () {
    this.log(yosay(
      'Welcome to ' + chalk.red('Kotlin Android Starter') + ' generator!'
    ));

    const prompts = [
      {
        name: 'name',
        message: 'What are you calling your app?',
        store: true,
        default: this.appname // Default to current folder name
      },
      {
        name: 'package',
        message: 'What package will you be publishing the app under?'
      },
      {
        name: 'targetSdk',
        message: 'What Android SDK will you be targeting?',
        store: true,
        default: 26 // Android 8.0 (O(7.1+))
      },
      {
        name: 'minSdk',
        message: 'What is the minimum Android SDK you wish to support?',
        store: true,
        default: 19 // Android 4.0 (Ice Cream Sandwich)
      }];

    return this.prompt(prompts).then(props => {
      this.props.appPackage = props.package;
      this.appName = props.name;
      this.appPackage = props.package;
      this.androidTargetSdkVersion = props.targetSdk;
      this.androidMinSdkVersion = props.minSdk;
    });
  },

  writing: function () {
    const packageDir = this.props.appPackage.replace(/\./g, '/');
    const appFolder = 'conductor';

    mkdirp('app');
    mkdirp('app/src/main/assets');
    mkdirp('app/src/main/kotlin/' + packageDir);
    mkdirp('app/src/androidTest/kotlin/' + packageDir);
    mkdirp('app/src/commonTest/kotlin/' + packageDir);
    mkdirp('app/src/debug');
    mkdirp('app/src/release');
    mkdirp('app/src/test/resources');
    mkdirp('app/src/test/kotlin/' + packageDir);

    const appPath = this.sourceRoot() + '/' + appFolder + '/';
    const copyToSameLocation = filePath => this.fs.copy(appPath + filePath, filePath);

    const copyAllToSameLocation = filePath => {
      ncp.limit = 1600;
      ncp(appPath + filePath, filePath, err => {
        if (err) {
          return console.error(err);
        }
      });
    };

    this.fs.copy(appPath + '.gitignore', '.gitignore');
    this.fs.copy(appPath + 'build.gradle', 'build.gradle');
    this.fs.copy(appPath + 'gradle.properties', 'gradle.properties');
    this.fs.copy(appPath + 'gradlew', 'gradlew');
    this.fs.copy(appPath + 'gradlew.bat', 'gradlew.bat');
    this.fs.copy(appPath + 'settings.gradle', 'settings.gradle');

    copyToSameLocation('app/.gitignore');
    copyToSameLocation('app/proguard-rules.pro');
    copyToSameLocation('app/output.gradle');
    copyToSameLocation('app/versioning.gradle');

    copyAllToSameLocation('config');

    this.fs.copy(appPath + 'gradle', 'gradle');
    this.fs.copy(appPath + 'app/src/main/res', 'app/src/main/res');
    this.fs.copy(appPath + 'app/src/test/resources', 'app/src/test/resources');

    const currentPath = 'nolambda/androidstarter';

    /* Top Level */
    this.fs.copyTpl(appPath + 'app/build.gradle', 'app/build.gradle', this.props);

    /* Main */
    this.fs.copyTpl(appPath + 'app/src/main/AndroidManifest.xml', 'app/src/main/AndroidManifest.xml', this.props);
    this.fs.copyTpl(appPath + `app/src/main/kotlin/${currentPath}`, 'app/src/main/kotlin/' + packageDir, this.props);
    this.fs.copyTpl(appPath + 'app/src/main/res/layout', 'app/src/main/res/layout', this.props);

    /* Test */
    this.fs.copyTpl(appPath + `app/src/test/kotlin/${currentPath}`, 'app/src/test/kotlin/' + packageDir, this.props);
  }
});
