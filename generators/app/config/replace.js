const PLACEHOLDER = '|__PLACEHOLDER__|';

const replaceConfig = [
  {
    name: 'App name',
    symbol: '<%= appName %>',
    replace: '<string name="app_name">Android Starter</string>',
    replacement: `<string name="app_name">${PLACEHOLDER}</string>`
  },
  {
    name: 'Target SDK version',
    symbol: '<%= androidTargetSdkVersion %>',
    replace: 'targetSdk : 22',
    replacement: `targetSdk : ${PLACEHOLDER}`
  },
  {
    name: 'Min SDK version',
    symbol: '<%= androidMinSdkVersion %>',
    replace: 'minSdk    : 16',
    replacement: `minSdk    : ${PLACEHOLDER}`
  }
];

const createReplacement = config => {
  return config.replacement.replace(PLACEHOLDER, config.symbol);
};

module.exports = {
  replaceConfig,
  createReplacement
};
