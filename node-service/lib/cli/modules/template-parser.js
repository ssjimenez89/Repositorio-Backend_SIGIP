const getAllVars = (string) => {
    const reg = /{{[a-z]+}}/gi;
    const vars = [...string.matchAll(reg)];
    return vars.map((v) => {
        return v[0];
    });
};
const clearBrackets = (string) => {
    return string.replace('{{', '').replace('}}', '');
};
module.exports.parse = (string, obj) => {
    let str = string;
    const vars = getAllVars(string);
    vars.forEach((v) => {
        const regExp = new RegExp(v, 'gi');
        const varClear = clearBrackets(v);
        str = str.replace(regExp, obj[varClear]);
    });
    return str;
};



