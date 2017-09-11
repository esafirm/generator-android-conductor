'use strict';

const path = require('path');
const rimraf = require('rimraf');
const replace = require('replace');
const ncp = require('ncp').ncp;
const nodegit = require('nodegit');
const clone = nodegit.Clone;
const mv = require('mv');

const {replaceConfig, createReplacement} = require('./config/replace');

const tempDir = path.join(__dirname, './tmp');
const appPath = 'conductor';

console.log('Running… ');

rimraf.sync(tempDir);

clone('https://github.com/esafirm/android-conductor-boilerplate.git', tempDir)
  .then(function () {
    return clearTemplate().then(() => checkOutAndCopy());
  })
  .catch(function (err) {
    console.log('cloning error', err);
  });

function clearTemplate() {
  return new Promise(resolve => {
    rimraf.sync(path.join(__dirname, `/templates/${appPath}/*`));
    rimraf.sync(path.join(__dirname, `/templates/${appPath}/.*`));
    resolve();
  });
}

function checkOutAndCopy() {
  console.log('Setting up code base…');

  replace({
    regex: 'nolambda.androidstarter',
    replacement: '<%= appPackage %>',
    paths: [tempDir],
    recursive: true,
    silent: true
  });

  replaceConfig.forEach(config => {
    console.log('Replaceing ' + config.name);
    replace({
      regex: config.replace,
      replacement: createReplacement(config),
      paths: [tempDir],
      recursive: true,
      silent: true
    });
  });

  maskDotFile(tempDir + '/.gitignore');
  maskDotFile(tempDir + '/app/.gitignore');

  rimraf.sync(path.join(__dirname, '/tmp/.git'));

  console.log(`Copying files to ./templates/${appPath}`);

  ncp.limit = 1600;
  ncp(tempDir, path.join(__dirname, `templates/${appPath}`), err => {
    if (err) {
      return console.error(err);
    }
    console.log('Copying complete!');
    rimraf.sync(tempDir);
  });
}

function maskDotFile(filePath) {
  const masked = filePath.replace('.', '');
  mv(filePath, masked, err => {
    if (err) {
      console.log('Mask dot file error:', err);
    } else {
      console.log('Successfully masked to ' + masked);
    }
  });
}
