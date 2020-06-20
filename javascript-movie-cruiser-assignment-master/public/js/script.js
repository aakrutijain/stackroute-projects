let movieUrl = 'http://localhost:3000/movies';
let favouritesUrl = 'http://localhost:3000/favourites';

let moviesList = [];
let favouritesList = [];

function getMovies() {
	return fetch(movieUrl)
		.then(data => {
			return data.json();	
		})
		.then(movies => {
			moviesList = movies;
			let tbody = '';
			movies.forEach(movie => {
				tbody = tbody + `
				${movie.id}, ${movie.title}, ${movie.posterPath}
				<input type="button" value="fav" onclick="addFavourite(${movie.id})"> <br/>
				`;
			});
			document.getElementById('moviesList').innerHTML = tbody;
			return moviesList;
		})
		.catch(error => {
			throw new Error(error);
		});
}

function getFavourites() {
	return fetch(favouritesUrl)
		.then(data => {
			return data.json();
		})
		.then(favourites => {
			favouritesList = favourites;
			let tbody = '';
			favourites.forEach(favourite => {
				tbody = tbody + `
				${favourite.id}, ${favourite.title}, ${favourite.posterPath} <br/>
				`;
			});
			document.getElementById('favouritesList').innerHTML = tbody;
			return favouritesList;
		})
		.catch(error => {
			throw new Error(error);
		});
}

function addFavourite(id) {
	let reqBody = moviesList.filter(function (movieId) {
		return movieId.id == id;
	});
	favouritesList.filter(function (movieId) {
		if(movieId.id == id) {
			throw new Error("Movie is already added to favourites");
		}
	});
	return fetch(favouritesUrl, {
		method: 'POST',
		body: JSON.stringify(reqBody[0]),
		headers: {'Content-Type': 'application/json'}
	})
	.then(response => {
		let movie = reqBody[0];
		favouritesList.push(movie);
		let tbody = '';
		favouritesList.forEach(favourite => {
			tbody = tbody + `
			${favourite.id}, ${favourite.title}, ${favourite.posterPath} <br/>
			`;
		});
		document.getElementById('favouritesList').innerHTML = tbody;
		return favouritesList;
	})
	.catch(error => {
		throw new Error(error);
	});
}

module.exports = {
	getMovies,
	getFavourites,
	addFavourite
};

// You will get error - Uncaught ReferenceError: module is not defined
// while running this script on browser which you shall ignore
// as this is required for testing purposes and shall not hinder
// it's normal execution


