const executer = require('../lib/script-executer');

module.exports.task = () => {
    const d = new Date();
    executer.execute('setVarEstabStateToPending', [d.toISOString()]);
};