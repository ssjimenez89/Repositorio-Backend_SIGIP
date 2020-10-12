const executer = require('../lib/script-executer');

module.exports.pruebaReal = () => {
    const d = new Date();
    executer.execute('prueba2', [d.toISOString()]);
};