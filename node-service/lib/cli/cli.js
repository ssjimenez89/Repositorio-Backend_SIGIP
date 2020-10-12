const {Input} = require('enquirer');
const tasGenerator = require('./modules/task-generator');
const prompt = new Input({
    name: 'nombre',
    message: 'nombre de la tarea:'
});

prompt.run()
    .then(resp => {
        tasGenerator.generateTask(resp);
    });