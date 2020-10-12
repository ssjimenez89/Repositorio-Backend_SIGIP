const path = require('path');
const insertLine = require('insert-line');
const fs = require('fs');
const tasksFolder = path.join(process.cwd(), 'tasks');
const taskIndex = path.join(tasksFolder, 'index.js');
const templateLoader = require('./template-loader');
const templateParser = require('./template-parser');
const mainJs = path.join(process.cwd(), 'main.js');
const indexTaskImport = (name) => {
    const template = templateParser.parse(templateLoader.loadTemplate('index'), {name: name});
    fs.appendFileSync(taskIndex, `\n${template}`, 'utf8');
};
const mainTaskImport = (name) => {
    const template = templateParser.parse(templateLoader.loadTemplate('main'), {name: name});
    insertLine(mainJs).contentSync(`    ${template}`).at(getLineToInstert());
};
const getLines = (main) => {
    return fs.readFileSync(main, 'utf8').toString().split('\n');
};
const getLineToInstert = () => {
    const lines = getLines(mainJs);
    let encontrado = false;
    let pos = 0;
    let cronStarts = false;
    while (!encontrado) {
        const line = lines[pos];
        if (line.replace(/ /gi, '').startsWith('cron.schedule')) {
            if (!cronStarts) {
                cronStarts = true;
            }
        } else if (cronStarts) {
            encontrado = true;
        }
        pos++;
    }
    return pos;
};

module.exports.generateImport = (name) => {
    indexTaskImport(name);
    mainTaskImport(name);
};
