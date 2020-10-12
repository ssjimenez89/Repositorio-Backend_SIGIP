const {Pool} = require('pg');
const fs = require('fs');
const path = require('path');
const dbConf = config.db;
const pool = new Pool({
    user: dbConf.user,
    host: dbConf.host,
    port: dbConf.port,
    password: dbConf.pass,
    database: dbConf.dbName
});
// const query = fs.readFileSync(path.join('..', 'scripts', 'prueba.sql'), 'utf8');
// console.log(query);


module.exports.execute = (sqlFile, values = []) => {
    const query = fs.readFileSync(path.join(__dirname, '..', 'scripts', sqlFile + '.sql'), 'utf8');
    pool.query(query, values)
        .then(res => {
            console.log(res);
        }).catch(err => {
        console.log(err.stack);
    })

};