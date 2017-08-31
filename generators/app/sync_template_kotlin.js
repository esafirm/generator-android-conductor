'use strict';

const path = require('path');
const rimraf = require('rimraf');
const replace = require('replace');
const ncp = require('ncp').ncp;
const nodegit = require('nodegit');
const clone = nodegit.Clone;

const tempDir = path.join(__dirname, './tmp');

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
    rimraf.sync(path.join(__dirname, '/templates/template-kotlin/*'));
    rimraf.sync(path.join(__dirname, '/templates/template-kotlin/.*'));
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

  rimraf.sync(path.join(__dirname, '/tmp/.git'));

  console.log('Copying files to ./templates/template-kotlin');

  ncp.limit = 1600;
  ncp(tempDir, path.join(__dirname, 'templates/template-kotlin'), err => {
    if (err) {
      return console.error(err);
    }
    console.log('Copying complete!');
    rimraf.sync(tempDir);
  });
}
