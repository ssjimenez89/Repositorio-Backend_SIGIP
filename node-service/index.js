const bootstrapper = require('./boot/initialazer');
bootstrapper.bootstrap();
const app = require('./main');
app.run();