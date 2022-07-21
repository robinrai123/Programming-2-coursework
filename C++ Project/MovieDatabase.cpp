/*
By Robin Rai
V.1.0.0
Created on 05/03/2020
This file features constructors for MovieDatabase, some lambdas used for sorting, some filter functions, overloaded
 operators, and a test function
*/

#include <fstream>
#include <sstream>
#include "MovieDatabase.h"


using namespace std;

MovieDatabase::MovieDatabase() {};

MovieDatabase::MovieDatabase(const string &fileLocation) {

	ifstream file;
	string line;
	file.open(fileLocation);
	if (!file) {
		cout << "Unable to open file";
		exit(1); // terminate with error
	}
	while (getline(file, line)) {
		//for every good line, it makes a movie, and sets it's variables to whatever's on the line with the overload
		Movie temp;
		std::istringstream iss(line);
		iss >> temp;
		add(temp);
	}
	file.close();
}

void MovieDatabase::sortFilms(const int direction) {
	//cheekily uses overloaded < and >, similar to compareTo using .equals in java.
	//since it uses the overload, it will compare by year first, then title

	if (direction == 0 || direction == 1) {
		sort(omdb.begin(), omdb.end());
	} else {
		cout << "bad input" << endl;
	}
	if (direction == 1) {
		//When using 1/reverse order, it will invert the order of titles as well. So B will be before
		//A if their year is the same. I could get rid of title sorting all together, but I kinda like it
		reverse(omdb.begin(), omdb.end());
	}
}


void MovieDatabase::sortDuration() {
	//look! a lambda! I'm so proud. Sorts by duration, then by movie (year then title)

	sort(omdb.begin(), omdb.end(),
		 [](const Movie &mov1, const Movie &mov2) {
			 if (mov1.getDuration() == mov2.getDuration()) {
				 return &mov1 < &mov2;
				 //if the duration's the same for both, compare by movie
			 } else {
				 return (mov1.getDuration() < mov2.getDuration());
			 }
		 });
}


void MovieDatabase::sortTitleLength() {
	//look! another lambda! I'm still so proud. Sorts by title length, then by movie
	sort(omdb.begin(), omdb.end(),
		 [](const Movie &mov1, const Movie &mov2) {
			 if (mov1.getTitle().length() == mov2.getTitle().length()) {
				 return &mov1 < &mov2;
				 //if the lengths are the same, compare by movie
			 } else {
				 return (mov1.getTitle().length() < mov2.getTitle().length());
			 }
		 });
}


vector<Movie> MovieDatabase::filterGenre(string genre) const {
	//returns a vector of only the movies with the genre specified
	vector<Movie> result;
	for (int i = 0; i < this->omdb.size(); i++) {
		if (this->omdb[i].getGenre().find(genre) != string::npos) {
			//find() will either return the position if it finds it, or npos if it doesn't
			//we don't care about the index at where it was found, just if it found it or not (npos)
			result.push_back(this->omdb[i]);
		}
	}
	return result;
}


vector<Movie> MovieDatabase::filterAgeRating(string age) const {
	//returns a vector of only the movies with the ageRating specified
	vector<Movie> result;
	for (int i = 0; i < this->omdb.size(); i++) {
		if ((age.compare(this->omdb[i].getAgeRating())) == 0) {
			//just compares strings instead of the find thing
			result.push_back(this->omdb[i]);
		}
	}
	return result;
}

std::ostream &operator<<(std::ostream &outputStream, const MovieDatabase &omdb) {
	//goes through vector of films and << them. No endl since movie's << does that already.
	//not inline/header file since lotsa function calls going on
	for (int i = 0; i < omdb.size(); i++) {
		outputStream << omdb.getMovie(i);
	}
	return outputStream;
}

void movieDatabaseTest() {
	//MovieDatabase badLocation("i could really do with another ice cream");
	MovieDatabase omdbTest("movieDatabaseTest.txt");

	Movie movieAdd("Ikiru", 1952, "NOT RATED", "Drama", 143, 0);

	Movie vectorAdd("Life Is Beautiful", 1997, "PG-13", "Comedy/Drama/War", 116, 0);
	Movie vectorAdd2("Castle in the Sky", 1986, "PG", "Adventure/Animation/Family", 125, 0); //bloomin' love laputa
	vector<Movie> movieVector;
	movieVector.push_back(vectorAdd);
	movieVector.push_back(vectorAdd2);

	omdbTest.add(movieAdd);
	omdbTest.add(movieVector);

	cout << "Original order:" << endl;
	cout << omdbTest << endl;

	cout << "Sorting by film (year, then title):" << endl;
	omdbTest.sortFilms(0);  //reverse sortFilms is exactly that - the same year will have it's title sorted backwards
	cout << omdbTest << endl;

	cout << "Sorting by duration:" << endl;
	omdbTest.sortDuration();
	cout << omdbTest << endl;

	cout << "Sorting by title length:" << endl;
	omdbTest.sortTitleLength();
	cout << omdbTest << endl;

	cout << "Filtering by genre:" << endl;
	vector<Movie> genreTest = omdbTest.filterGenre("Comedy");
	//filtering by "" will return everything, since everything contains nothing, and filtering a genre that doesn't
	//exist returns nothing
	MovieDatabase genreDB;
	genreDB.add(genreTest);
	cout << genreDB << endl;

	cout << "Filtering by ageRating:" << endl;
	vector<Movie> ageRatingTest = omdbTest.filterAgeRating("PG");
	//filtering by "" will return everything, since everything contains nothing, and filtering a ageRating that doesn't
	//exist returns nothing
	MovieDatabase ageRatingDB;
	ageRatingDB.add(ageRatingTest);
	cout << ageRatingDB << endl;


}