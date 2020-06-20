/* Write a program to build a `Pyramid of stars` of given height */
let i;
let j;
let k;
let str;
let result;

const buildPyramid = (n) => {
    result = '';
	for(i = 0; i < n; i = i + 1) {
        str = '';
        for(j = 0; j < n - i; j = j + 1) {
            str = str + ' ';
        }
        for(k = 0; k <= i; k = k + 1) {
            str = str + '*' + ' ';
        }
        result = result + str + ' ' + '\n';
    }
    return result;
};

/* For example,
INPUT - buildPyramid(6)
OUTPUT -
     *
    * *
   * * *
  * * * *
 * * * * *
* * * * * *

*/

module.exports = buildPyramid;
