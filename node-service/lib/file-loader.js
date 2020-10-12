const fs = require('fs');
const PATH = require('path');
module.exports.loadFile = (path) => {
    return fs.readFileSync(PATH.resolve(__dirname, path), 'utf8');
};

