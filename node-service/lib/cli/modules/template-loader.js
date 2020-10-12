const path = require('path');
const fs = require('fs');
const templateFolder = path.join(__dirname, 'templates');
const taskTemplate = path.join(templateFolder, 'task.js.txt');
module.exports.loadTemplate = (template) => {
    switch (template) {
        case 'task': {
            return fs.readFileSync(taskTemplate, 'utf8').toString();
        }
        case 'sql': {
            return '';
        }
        case 'index': {
            return 'module.exports.{{name}} = require(\'./{{name}}\').task;';
        }
        case 'main': {
            return 'cron.schedule(\'* * * * *\', tasks.{{name}});';
        }
        default: {
            process.exit(1);
        }
    }


};
