const PLACEHOLDER = '|__PLACEHOLDER__|';

const replaceConfig = [
  {
    name: 'App name',
    symbol: '<%= appName %>',
    replace: '<string name="app_name">Android Starter</string>',
    replacement: `<string name="app_name">${PLACEHOLDER}</string>`
  }
];

const createReplacement = config => {
  return config.replacement.replace(PLACEHOLDER, config.symbol);
};

module.exports = {
  replaceConfig,
  createReplacement
};
