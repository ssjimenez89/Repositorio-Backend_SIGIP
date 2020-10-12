const configJson = require('../config.json');

module.exports.initialize = () => {
    global.config = configJson;
};

