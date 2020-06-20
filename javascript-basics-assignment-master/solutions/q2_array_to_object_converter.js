/* Write a Program to convert an array of objects to an object
	based on a given key */


const convert = (arr, keyField) => {
	try {
		return arr.reduce((key, val) => {
			key[val[keyField]] = val;
			return key;
		}, {});
	} catch(e) {
		return null;
	}
};


/* For example,
INPUT - convert([{id: 1, value: 'abc'}, {id: 2, value: 'xyz'}], 'id')
OUTPUT - {
			'1': {id: 1, value: 'abc'},
			'2': {id: 2, value: 'xyz'}
			}


*/

module.exports = convert;
