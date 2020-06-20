/* Write a Program to Flatten a given n-dimensional array */

const flatten = (arr) => {
	try {
		return arr.reduce((arrFlat, val) => {
			return arrFlat.concat(Array.isArray(val) ? flatten(val) : [val]);
		}, []);
	} catch (e) {
		return null;
	}
};


/* For example,
INPUT - flatten([1, [2, 3], [[4], [5]])
OUTPUT - [ 1, 2, 3, 4, 5 ]

*/

module.exports = flatten;
