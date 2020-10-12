const path = require('path');
const fs = require('fs');
const templateParser = require('./template-parser');
const tamplateLoader = require('./template-loader');
const tasksFolder = path.join(process.cwd(), 'tasks');
const scriptsFolder = path.join(process.cwd(), 'scripts');
const importer = require('./importer');
const exist = (PATH) => {
    return fs.existsSync(PATH);
};
const createTaskJs = (name, data) => {
    const file = path.join(tasksFolder, `${name}.js`);
    createFile(file, data);
};
const createFile = (file, data) => {
    fs.writeFileSync(file, data, 'utf8');
    console.log('archivo ', file, ' creado.');
};

const createTaskSQL = (name, data) => {
    const file = path.join(scriptsFolder, `${name}.sql`);
    createFile(file, data);
};
module.exports.generateTask = (name) => {
    const parse = templateParser.parse(tamplateLoader.loadTemplate('task'), {name: name});
    createTaskJs(name, parse);
    createTaskSQL(name, tamplateLoader.loadTemplate('sql'));
    importer.generateImport(name);
};