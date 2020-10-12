const executer = require('../lib/script-executer');

module.exports.prueba = () => {
    executer.execute('prueba',[42]);
};