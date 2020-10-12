const cron = require('node-cron');
const tasks = require('./tasks');
module.exports.run = () => {
    //add here tasks
    //cron.schedule('* * * * *', tasks.pruebaTask);
    cron.schedule('* 0 * * *', tasks.setVarEstabStateToPending);
};