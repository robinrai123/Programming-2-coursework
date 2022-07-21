/*
By Robin Rai
V.1.0.0
Created on 05/03/2020
 This file runs the program with the intended input. It runs the two test functions, then the program as to spec.
*/

#include <iostream>
#include "MovieDatabase.h"
#include "Movie.h"

using namespace std;

int main() {

	cout << "MOVIE TESTING: " << endl;
	movieTest();
	cout << "MOVIE DATABASE TESTING: " << endl;
	movieDatabaseTest();


	cout << "ACTUAL PROGRAM OUTPUT: " << endl;
	//initializing database

	MovieDatabase omdb("films.txt");


	//all movies in chronological order

	omdb.sortFilms(1);
	cout << endl << "All films in chronological order:" << endl;
	cout << omdb;


	//third longest film noir

	vector<Movie> genreFiltered = omdb.filterGenre("Film-Noir");

	MovieDatabase filmNoirDb;

	filmNoirDb.add(genreFiltered);

	filmNoirDb.sortDuration();

	cout << endl << "Third longest Film-Noir:" << endl;
	cout << filmNoirDb.getMovie(filmNoirDb.size() - 3) << endl;


	//eight most recent unrated

	vector<Movie> ageFiltered = omdb.filterAgeRating("UNRATED");

	MovieDatabase unratedDb;

	unratedDb.add(ageFiltered);

	unratedDb.sortFilms(0);

	cout << "Eighth most recent unrated:" << endl;
	cout << unratedDb.getMovie(unratedDb.size() - 8) << endl;


	//longest titled film

	omdb.sortTitleLength();

	cout << "Longest title: " << endl;
	cout << omdb.getMovie(omdb.size() - 1) << endl;


	return 0;
}
